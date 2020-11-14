package com.labsit.contacorrente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExtratoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String agencia;
    @NotNull
    private String numeroContaCorrente;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dataHoraConsulta;
    @NotNull
    private List<ItemExtratoDTO> transacoes;
}
