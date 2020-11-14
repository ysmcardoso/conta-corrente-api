package com.labsit.contacorrente.dto;

import com.labsit.contacorrente.model.dominio.DominioTransacao;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TransacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ContaCorrenteDTO conta;
    private BigDecimal valor;
}
