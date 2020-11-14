package com.labsit.contacorrente.controller;


import com.labsit.contacorrente.contants.URLConstants;
import com.labsit.contacorrente.dto.RequestExtratoDTO;
import com.labsit.contacorrente.dto.TransacaoDTO;
import com.labsit.contacorrente.service.ContaCorrenteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Api(value = "Api para manutenção de clientes")
@RestController
@RequestMapping("conta-corrente")
public class ContaCorrenteController {

    private ContaCorrenteService contaCorrenteService;

    @Autowired
    public ContaCorrenteController(ContaCorrenteService contaCorrenteService) {
        this.contaCorrenteService = contaCorrenteService;
    }

    @ApiOperation(value = "Sacar valor da conta corrente")
    @PostMapping(value = URLConstants.CONTA_DEBITO, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity debitar(@RequestBody TransacaoDTO transacaoDTO) {
        contaCorrenteService.debitar(transacaoDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Depositar valor na conta corrente")
    @PostMapping(value = URLConstants.CONTA_CREDITO, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity creditar(@RequestBody TransacaoDTO transacaoDTO) {
        contaCorrenteService.creditar(transacaoDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Consultar saldo da conta corrente")
    @GetMapping(value = URLConstants.CONTA_SALDO, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity consultarSaldo(@PathVariable("agencia") String agencia, @PathVariable("conta") String conta) {
        return ResponseEntity.ok(contaCorrenteService.consultarSaldo(agencia, conta));
    }

    @ApiOperation(value = "Consultar extrato da conta corrente")
    @PostMapping(value = URLConstants.CONTA_EXTRATO, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity consultarExtrato(@RequestBody RequestExtratoDTO requestExtratoDTO) {
        return ResponseEntity.ok(contaCorrenteService.consultarExtrato(requestExtratoDTO));
    }

}
