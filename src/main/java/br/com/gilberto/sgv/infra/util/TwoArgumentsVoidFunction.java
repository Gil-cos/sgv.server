package br.com.gilberto.sgv.infra.util;

import java.io.IOException;

@FunctionalInterface
public interface TwoArgumentsVoidFunction<T, S> {

	void apply(T t, S s) throws IOException;

}
