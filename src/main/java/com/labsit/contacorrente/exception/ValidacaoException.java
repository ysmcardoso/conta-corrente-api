package com.labsit.contacorrente.exception;

import lombok.Getter;

public class ValidacaoException extends RuntimeException {

    @Getter
    private final String[] args;

    public ValidacaoException(String mensagem) {
        super(mensagem);
        this.args = null;
    }

    public ValidacaoException(String message, String[] args) {
        super(message);
        this.args = args;
    }

    public ValidacaoException(String message, Throwable cause, String[] args) {
        super(message, cause);
        this.args = args;
    }
}
