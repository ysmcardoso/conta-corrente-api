package com.labsit.contacorrente.exception;

import com.labsit.contacorrente.dto.ErrorRespondeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ContaCorrenteExceptionHandler {

    @ExceptionHandler(value = ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErrorRespondeDTO> handleGenericNotFoundException(ObjetoNaoEncontradoException e) {
        ErrorRespondeDTO error = new ErrorRespondeDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ObjetoDuplicadoException.class)
    public ResponseEntity<ErrorRespondeDTO> handleGenericDuplicatedException(ObjetoDuplicadoException e) {
        ErrorRespondeDTO error = new ErrorRespondeDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidacaoException.class)
    public ResponseEntity<ErrorRespondeDTO> handleGenericValidationException(ValidacaoException e) {
        ErrorRespondeDTO error = new ErrorRespondeDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
