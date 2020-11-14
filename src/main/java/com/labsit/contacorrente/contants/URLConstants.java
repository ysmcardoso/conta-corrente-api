package com.labsit.contacorrente.contants;

public interface URLConstants {

    String CLIENTE_CADASTRAR = "/cadastrar";
    String CLIENTE_ATUALIZAR = "/atualizar/{cpfCnpj}";
    String CLIENTES_LISTAR = "/listar";
    String CONTA_DEBITO = "/debito";
    String CONTA_CREDITO = "/credito";
    String CONTA_SALDO = "/saldo/{agencia}/{conta}";
    String CONTA_EXTRATO = "/extrato";
}
