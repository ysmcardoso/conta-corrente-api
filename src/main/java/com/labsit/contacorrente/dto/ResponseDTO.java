package com.labsit.contacorrente.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object dados;

}