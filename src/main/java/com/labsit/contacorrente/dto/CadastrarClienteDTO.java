package com.labsit.contacorrente.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.labsit.contacorrente.model.dominio.DominioTipoPessoa;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CadastrarClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String numeroCpfCnpj;
    @NotNull
    private DominioTipoPessoa tipoPessoa;
    @NotNull
    private String nome;
    @NotNull
    private String telefone;
    @NotNull
    private ContaCorrenteDTO contaCorrente;

    public CadastrarClienteDTO() {
        contaCorrente = new ContaCorrenteDTO();
    }
}
