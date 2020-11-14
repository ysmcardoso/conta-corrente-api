package com.labsit.contacorrente.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class RequestExtratoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String agencia;
    private String conta;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
}
