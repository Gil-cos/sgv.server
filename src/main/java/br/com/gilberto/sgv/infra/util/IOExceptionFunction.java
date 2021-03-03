package br.com.gilberto.sgv.infra.util;

import java.io.IOException;

@FunctionalInterface
public interface IOExceptionFunction {
	
	void apply() throws IOException; 

}
