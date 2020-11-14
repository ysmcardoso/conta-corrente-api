package com.labsit.contacorrente.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ContaCorrenteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String numeroAgencia;
    @NotNull
    private String numeroConta;

}
