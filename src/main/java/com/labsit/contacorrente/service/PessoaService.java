package com.labsit.contacorrente.service;

import com.labsit.contacorrente.dto.AtualizarClienteDTO;
import com.labsit.contacorrente.dto.CadastrarClienteDTO;
import com.labsit.contacorrente.dto.ContaCorrenteDTO;
import com.labsit.contacorrente.exception.ObjetoDuplicadoException;
import com.labsit.contacorrente.exception.ObjetoNaoEncontradoException;
import com.labsit.contacorrente.model.ContaCorrente;
import com.labsit.contacorrente.model.Pessoa;
import com.labsit.contacorrente.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;
    private ContaCorrenteService contaCorrenteService;
    private ModelMapper modelMapper;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, ContaCorrenteService contaCorrenteService, ModelMapper modelMapper) {
        this.pessoaRepository = pessoaRepository;
        this.contaCorrenteService = contaCorrenteService;
        this.modelMapper = modelMapper;
        configMapperDtoToEntity();
        configMapperAtuDtoToEntity();
        configMapperEntityToDTO();
        configMapperCCDtoToEntity();
    }

    private void configMapperDtoToEntity() {
        modelMapper.addMappings(new PropertyMap<CadastrarClienteDTO, Pessoa>() {

                                    @Override
                                    protected void configure() {
                                        map().setCpfCnpj(source.getNumeroCpfCnpj());
                                        map().setTpPess(source.getTipoPessoa());
                                        map().setNrFone(source.getTelefone());
                                    }
                                }
        );
    }

    private void configMapperAtuDtoToEntity() {
        modelMapper.addMappings(new PropertyMap<AtualizarClienteDTO, Pessoa>() {

                                    @Override
                                    protected void configure() {
                                        map().setNrFone(source.getTelefone());
                                        map().setNome(source.getNome());
                                    }
                                }
        );
    }


    private void configMapperEntityToDTO() {
        modelMapper.addMappings(new PropertyMap<Pessoa, CadastrarClienteDTO>() {

                                    @Override
                                    protected void configure() {
                                        map().setNumeroCpfCnpj(source.getCpfCnpj());
                                        map().setTipoPessoa(source.getTpPess());
                                        map().setNome(source.getNome());
                                        map().setTelefone(source.getNrFone());
                                    }
                                }
        );
    }

    private void configMapperCCDtoToEntity() {
        modelMapper.addMappings(new PropertyMap<ContaCorrenteDTO, ContaCorrente>() {
                                    @Override
                                    protected void configure() {
                                        map().getId().getAgencia().setNrAg(source.getNumeroAgencia());
                                        map().getId().setNrCta(source.getNumeroConta());
                                        map().setSld(BigDecimal.ZERO);
                                    }
                                }
        );
    }

    public void cadastrarCliente(@NotNull @Validated CadastrarClienteDTO cadastrarClienteDTO) {
        validarDadosConta(cadastrarClienteDTO);
        Pessoa pessoa = modelMapper.map(cadastrarClienteDTO, Pessoa.class);
        ContaCorrente contaCorrente = modelMapper.map(cadastrarClienteDTO.getContaCorrente(), ContaCorrente.class);
        contaCorrente.setPessoa(pessoa);
        contaCorrenteService.cadastrar(contaCorrente);
    }

    public void alterarCliente(@NotNull @NotEmpty String cpfCnpj, @NotNull @Validated AtualizarClienteDTO clienteDTO) {
        validarCliente(cpfCnpj);
        Pessoa pessoa = pessoaRepository.findById(cpfCnpj).get();
        pessoa.setNome(clienteDTO.getNome());
        pessoa.setNrFone(clienteDTO.getTelefone());
        pessoaRepository.save(pessoa);
    }

    public List<CadastrarClienteDTO> listarClientes() {
        List<ContaCorrente> contaCorrentes = contaCorrenteService.findAll();
        List<CadastrarClienteDTO> cadastrarClienteDTOS = new ArrayList<>();

        contaCorrentes.forEach(c -> {
            CadastrarClienteDTO cadastrarClienteDTO = contaCorrenteService.convertEntityToDto(c);
            cadastrarClienteDTOS.add(cadastrarClienteDTO);
        });
        return cadastrarClienteDTOS;
    }

    public void validarCliente(String numeroCpfCnpj) {
        pessoaRepository.findById(numeroCpfCnpj).orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente informado não existe !"));
    }

    @ExceptionHandler
    private void validarDadosConta(CadastrarClienteDTO cadastrarClienteDTO) {
        if (pessoaRepository.findById(cadastrarClienteDTO.getNumeroCpfCnpj()).isPresent()) {
            throw new ObjetoDuplicadoException("Cliente já cadastrado !");
        }

        if (null != contaCorrenteService.buscarContaCorrente(cadastrarClienteDTO.getContaCorrente().getNumeroAgencia(),
                cadastrarClienteDTO.getContaCorrente().getNumeroConta())) {
            throw new ObjetoDuplicadoException("Conta já cadastrada !");
        }
    }
}
