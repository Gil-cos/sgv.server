package br.com.gilberto.sgv.endpoints;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.gilberto.sgv.infra.exception.ErrorData;
import br.com.gilberto.sgv.infra.exception.ForbiddenException;

@ControllerAdvice
public class ExceptionHandlerController {

	private final Log logger = LogFactory.getLog(getClass());

	@ExceptionHandler(Throwable.class)
	protected ResponseEntity<ErrorData> handleException(final Throwable throwable) {
		final Class<? extends Throwable> clazz = throwable.getClass();
		if (clazz.equals(ConstraintViolationException.class)) {
			return handleException(throwable.getCause());
		} else if (clazz.isAnnotationPresent(ResponseStatus.class)) {
	        return createErrorData(throwable, clazz);
		} else if (throwable.getCause() != null) {
			return handleException(throwable.getCause());
		} else if (clazz.equals(AccessDeniedException.class)) {
			ForbiddenException ex = new ForbiddenException(throwable.getMessage());
			return createErrorDataForbidden(ex, ex.getClass());
		}

		logException(HttpStatus.INTERNAL_SERVER_ERROR, throwable);
		return new ResponseEntity<>(new ErrorData(HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	private ResponseEntity<ErrorData> createErrorData(final Throwable throwable,
			final Class<? extends Throwable> clazz) {
		final HttpStatus status = getHttpStatus(clazz);
		final ErrorData data = createErrorData(throwable, status);
		logException(status, throwable);
		return new ResponseEntity<>(data, status);
	}

	private ResponseEntity<ErrorData> createErrorDataForbidden(final Throwable throwable,
			final Class<? extends Throwable> clazz) {
		final HttpStatus status = getHttpStatus(clazz);
		final ErrorData data = createErrorData(throwable, status);
		logger.error("Error code " + status.toString());
		logger.error("Cause: " + throwable.getMessage());
		return new ResponseEntity<>(data, status);
	}

	private ErrorData createErrorData(final Throwable throwable, final HttpStatus status) {
		return new ErrorData(status, throwable);
	}

	private HttpStatus getHttpStatus(final Class<? extends Throwable> clazz) {
		final ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);
		return HttpStatus.INTERNAL_SERVER_ERROR == responseStatus.value() ? responseStatus.code()
				: responseStatus.value();
	}

	private void logException(final HttpStatus status, final Throwable throwable) {
		logger.error("Error code " + status.toString());
		logger.error("Cause: " + throwable.getMessage(), throwable);
	}
}
