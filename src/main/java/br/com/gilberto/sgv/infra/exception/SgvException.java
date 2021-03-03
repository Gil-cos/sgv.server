package br.com.gilberto.sgv.infra.exception;

import lombok.Getter;

@Getter
public abstract class SgvException extends RuntimeException {

    private static final long serialVersionUID = -5893378710711143802L;
    
    public SgvException() {}
    
    public SgvException(final ErrorMessage message) {
        super(message.toJson());
    }
    
    public SgvException(final ErrorMessage message, final Exception ex) {
        super(message.toJson(), ex);
    }

    public SgvException(final String message) {
        super(message);
    }
    
    public SgvException(final String message, final Throwable t) {
        super(message, t);
    }

	public SgvException(final Throwable t) {
		super(t);
	}
}
