package br.com.gilberto.sgv.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorData {

    private final int errorCode;
    private final String[] message;

    public ErrorData(final int errorCode, final String... message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorData(final HttpStatus httpStatus, final Throwable exception) {
        this.errorCode = httpStatus.value();
        this.message = new String[]{exception.getMessage()};
    }
}
