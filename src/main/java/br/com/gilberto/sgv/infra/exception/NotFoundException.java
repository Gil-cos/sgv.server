package br.com.gilberto.sgv.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends SgvException {
    private static final long serialVersionUID = 1030047614131045967L;

    public NotFoundException(final String message) {
        super(message);
    }
}