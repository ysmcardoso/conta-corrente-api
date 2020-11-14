package com.labsit.contacorrente.controller;

import com.labsit.contacorrente.contants.URLConstants;
import com.labsit.contacorrente.dto.AtualizarClienteDTO;
import com.labsit.contacorrente.dto.CadastrarClienteDTO;
import com.labsit.contacorrente.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Api(value = "Api para manutenção de clientes")
@RestController
@RequestMapping("cliente")
public class ClienteController {

    private PessoaService pessoaService;

    public ClienteController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @ApiOperation(value = "Cadastrar Cliente")
    @PostMapping(value = URLConstants.CLIENTE_CADASTRAR, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity cadastrarCliente(@RequestBody CadastrarClienteDTO cadastrarClienteDTO) {
        pessoaService.cadastrarCliente(cadastrarClienteDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Atualizar Cliente")
    @PutMapping(value = URLConstants.CLIENTE_ATUALIZAR, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity atualizarCliente(@PathVariable("cpfCnpj") String cpfCnpj, @RequestBody AtualizarClienteDTO atualizarClienteDTO) {
        pessoaService.alterarCliente(cpfCnpj, atualizarClienteDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Lista os clientes cadastrados")
    @GetMapping(value = URLConstants.CLIENTES_LISTAR, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity listarClientes() {
        return ResponseEntity.ok(pessoaService.listarClientes());
    }
}
