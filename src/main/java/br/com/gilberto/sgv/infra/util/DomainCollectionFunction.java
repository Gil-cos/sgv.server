package br.com.gilberto.sgv.infra.util;

@SuppressWarnings("rawtypes")
@FunctionalInterface
public interface DomainCollectionFunction<T extends Iterable> {

	T apply();
}
