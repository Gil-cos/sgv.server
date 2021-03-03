/*
 * @(#)SerializationException.java 1.0 13/02/2019
 *
 * Copyright (c) 2019, Embraer. All rights reserved. Embraer S/A
 * proprietary/confidential. Use is subject to license terms.
 */

package br.com.gilberto.sgv.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author vnbened
 * @version 1.0 13/02/2019
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class JsonPreConditionFailedException extends SgvException {
	private static final long serialVersionUID = 4994700380320594500L;

	public JsonPreConditionFailedException(final String message) {
        super(message);
    }
	
	public JsonPreConditionFailedException(final String message, final Throwable t) {
        super(message, t);
    }
}
