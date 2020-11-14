package com.labsit.contacorrente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labsit.contacorrente.model.dominio.DominioTransacao;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemExtratoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dataTransacao;
    private BigDecimal valorTransacao;
    private DominioTransacao tipoTransacao;
}
