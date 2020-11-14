package com.labsit.contacorrente.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AtualizarClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String nome;
    @NotNull
    private String telefone;
}
