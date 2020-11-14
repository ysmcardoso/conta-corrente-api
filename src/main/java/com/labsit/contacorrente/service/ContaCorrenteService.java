package com.labsit.contacorrente.service;

import com.labsit.contacorrente.dto.CadastrarClienteDTO;
import com.labsit.contacorrente.dto.ExtratoDTO;
import com.labsit.contacorrente.dto.ItemExtratoDTO;
import com.labsit.contacorrente.dto.RequestExtratoDTO;
import com.labsit.contacorrente.dto.SaldoDTO;
import com.labsit.contacorrente.dto.TransacaoDTO;
import com.labsit.contacorrente.exception.ObjetoNaoEncontradoException;
import com.labsit.contacorrente.exception.ValidacaoException;
import com.labsit.contacorrente.model.ContaCorrente;
import com.labsit.contacorrente.model.Transacao;
import com.labsit.contacorrente.model.dominio.DominioTransacao;
import com.labsit.contacorrente.repository.ContaCorrenteRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaCorrenteService {

    private ContaCorrenteRepository contaCorrenteRepository;
    private TransacaoService transacaoService;
    private ModelMapper modelMapper;

    @Autowired
    public ContaCorrenteService(ContaCorrenteRepository contaCorrenteRepository, TransacaoService transacaoService, ModelMapper modelMapper) {
        this.contaCorrenteRepository = contaCorrenteRepository;
        this.transacaoService = transacaoService;
        this.modelMapper = modelMapper;
        configMapperTrnDtoToEntity();
        configMapperCcEntityToDto();
        configMapperTrnEntityToDto();
    }


    private void configMapperTrnDtoToEntity() {
        modelMapper.addMappings(new PropertyMap<TransacaoDTO, Transacao>() {
                                    @Override
                                    protected void configure() {
                                        try {
                                            map().getContaCorrente().getId().getAgencia().setNrAg(source.getConta().getNumeroAgencia());
                                            map().getContaCorrente().getId().setNrCta(source.getConta().getNumeroConta());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        map().setValor(source.getValor());
                                    }
                                }
        );
    }

    private void configMapperCcEntityToDto() {
        modelMapper.addMappings(new PropertyMap<ContaCorrente, SaldoDTO>() {
                                    @SneakyThrows
                                    @Override
                                    protected void configure() {
                                        map().setNumeroAgencia(source.getId().getAgencia().getNrAg());
                                        map().setNumeroConta(source.getId().getNrCta());
                                        map().setSaldo(source.getSld());
                                    }
                                }
        );
    }

    private void configMapperTrnEntityToDto() {
        modelMapper.addMappings(new PropertyMap<Transacao, ItemExtratoDTO>() {

                                    @SneakyThrows
                                    @Override
                                    protected void configure() {
                                        map().setDataTransacao(source.getData());
                                        map().setTipoTransacao(source.getDominioTransacao());
                                        map().setValorTransacao(source.getValor());
                                    }
                                }
        );
    }

    public void cadastrar(ContaCorrente contaCorrente) {
        contaCorrenteRepository.save(contaCorrente);
    }

    public void debitar(@NotNull @Validated TransacaoDTO transacaoDTO) {
        Transacao transacao = modelMapper.map(transacaoDTO, Transacao.class);
        transacao.setDominioTransacao(DominioTransacao.DEBITO);
        transacao.setData(LocalDateTime.now());

        ContaCorrente contaCorrente = buscarConta(transacao.getContaCorrente().getId().getAgencia().getNrAg(), (transacao.getContaCorrente().getId().getNrCta()));
        verificarSaldo(contaCorrente, transacao.getValor());
        contaCorrente.setSld(contaCorrente.getSld().subtract(transacao.getValor()));

        contaCorrenteRepository.save(contaCorrente);
        transacaoService.salvar(transacao);
    }

    public void creditar(@NotNull @Validated TransacaoDTO transacaoDTO) {
        Transacao transacao = modelMapper.map(transacaoDTO, Transacao.class);
        transacao.setDominioTransacao(DominioTransacao.CREDITO);
        transacao.setData(LocalDateTime.now());

        ContaCorrente contaCorrente = buscarConta(transacao.getContaCorrente().getId().getAgencia().getNrAg(), (transacao.getContaCorrente().getId().getNrCta()));
        contaCorrente.setSld(contaCorrente.getSld().add(transacao.getValor()));

        contaCorrenteRepository.save(contaCorrente);
        transacaoService.salvar(transacao);
    }

    private void verificarSaldo(ContaCorrente contaCorrente, BigDecimal valor) {
        if (contaCorrente.getSld().compareTo(valor) < 0) {
            throw new ValidacaoException("Conta com saldo insuficiente");
        }
    }

    public SaldoDTO consultarSaldo(String nrAgencia, String nrConta) {
        ContaCorrente contaCorrente = buscarConta(nrAgencia, nrConta);
        SaldoDTO saldoDTO = modelMapper.map(contaCorrente, SaldoDTO.class);
        saldoDTO.setDataHoraConsulta(LocalDateTime.now());
        return saldoDTO;
    }

    public ExtratoDTO consultarExtrato(RequestExtratoDTO requestExtratoDTO) {

        List<ItemExtratoDTO> itemExtratoDTOS = new ArrayList<>();
        validarPeriodo(requestExtratoDTO.getDataInicial(), requestExtratoDTO.getDataFinal());
        ContaCorrente contaCorrente = buscarConta(requestExtratoDTO.getAgencia(), requestExtratoDTO.getConta());

        List<Transacao> transacaos = transacaoService.
                buscarTransacoesByPeriodo(contaCorrente, requestExtratoDTO.getDataInicial(),
                        requestExtratoDTO.getDataFinal())
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhum dado retornado para os parâmetros informados"));

        ExtratoDTO extratoDTO = new ExtratoDTO();
        extratoDTO.setAgencia(transacaos.get(0).getContaCorrente().getId().getAgencia().getNrAg());
        extratoDTO.setNumeroContaCorrente(transacaos.get(0).getContaCorrente().getId().getNrCta());
        extratoDTO.setDataHoraConsulta(LocalDateTime.now());

        transacaos.forEach(t -> {
            ItemExtratoDTO itemExtratoDTO = modelMapper.map(t, ItemExtratoDTO.class);
            itemExtratoDTOS.add(itemExtratoDTO);
        });

        extratoDTO.setTransacoes(itemExtratoDTOS);
        return extratoDTO;
    }

    private void validarPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataFinal.isBefore(dataInicial)) {
            throw new ValidacaoException("A data final não pode ser menor que a inicial !");
        } else if (dataInicial.isAfter(dataFinal)) {
            throw new ValidacaoException("A inicial não pode ser maior que a data final !");
        }
    }

    public ContaCorrente buscarConta(String nrAgencia, String nrConta) {
        return contaCorrenteRepository.findContaCorrenteByNrAgAndNrCta(nrAgencia, nrConta).orElseThrow(() -> new ObjetoNaoEncontradoException("Conta corrente não encontrada !"));
    }

    public ContaCorrente buscarContaCorrente(String nrAgencia, String nrConta) {
        return contaCorrenteRepository.findContaCorrenteByNrAgAndNrCta(nrAgencia, nrConta).orElse(null);
    }

    public List<ContaCorrente> findAll() {
        return contaCorrenteRepository.findAll();
    }

    public CadastrarClienteDTO convertEntityToDto(ContaCorrente contaCorrente) {
        CadastrarClienteDTO clienteDTO = new CadastrarClienteDTO();
        clienteDTO.setNumeroCpfCnpj(contaCorrente.getPessoa().getCpfCnpj());
        clienteDTO.setTipoPessoa(contaCorrente.getPessoa().getTpPess());
        clienteDTO.setNome(contaCorrente.getPessoa().getNome());
        clienteDTO.setTelefone(contaCorrente.getPessoa().getNrFone());
        clienteDTO.getContaCorrente().setNumeroAgencia(contaCorrente.getId().getAgencia().getNrAg());
        clienteDTO.getContaCorrente().setNumeroConta(contaCorrente.getId().getNrCta());
        return clienteDTO;
    }
}
