package com.labsit.contacorrente.exception;


import lombok.Getter;

public class ObjetoDuplicadoException extends RuntimeException {

    @Getter
    private final String[] args;

    public ObjetoDuplicadoException(String mensagem) {
        super(mensagem);
        this.args = null;
    }

    public ObjetoDuplicadoException(String message, String[] args) {
        super(message);
        this.args = args;
    }

    public ObjetoDuplicadoException(String message, Throwable cause, String[] args) {
        super(message, cause);
        this.args = args;
    }

}
