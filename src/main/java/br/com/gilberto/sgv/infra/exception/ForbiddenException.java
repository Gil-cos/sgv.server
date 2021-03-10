package br.com.gilberto.sgv.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends AccessDeniedException {

	private static final long serialVersionUID = 105695328880495557L;

	public ForbiddenException(final String message) {
		super(message);
	}
}