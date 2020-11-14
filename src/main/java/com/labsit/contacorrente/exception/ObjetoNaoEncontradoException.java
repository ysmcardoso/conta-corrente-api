package com.labsit.contacorrente.exception;

import lombok.Getter;

public class ObjetoNaoEncontradoException extends RuntimeException {

    @Getter
    private final String[] args;

    public ObjetoNaoEncontradoException(String mensagem) {
        super(mensagem);
        this.args = null;
    }

    public ObjetoNaoEncontradoException(String message, String[] args) {
        super(message);
        this.args = args;
    }

    public ObjetoNaoEncontradoException(String message, Throwable cause, String[] args) {
        super(message, cause);
        this.args = args;
    }
}
