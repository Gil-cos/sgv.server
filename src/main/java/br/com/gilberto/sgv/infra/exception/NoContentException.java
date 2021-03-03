/*
 * @(#)NoContentException.java 1.0 08/05/2020
 *
 *  Copyright (c) 2020, Embraer. All rights reserved. Embraer S/A
 *  proprietary/confidential. Use is subject to license terms.
 */

package br.com.gilberto.sgv.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A classe <code>NoContentException</code> indica a situacao quando um determinado recurso solicitado pelo usuario
 * existe, mas nao possui nenhum conteudo.
 *
 * @author Roberto Perillo
 * @author Thiago Cherubini
 * @version 1.0 08/05/2020
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {
}