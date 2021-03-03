package br.com.gilberto.sgv.infra.wrappers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.gilberto.sgv.infra.util.DomainCollectionFunction;
import lombok.Getter;

@Getter
@SuppressWarnings("rawtypes")
public class JsonResponseWrapper {
	
	private final Optional<?> valueOptional;
	private final Optional<Page<?>> pageableValue;
	private final Optional<Collection<?>> collection;
	private final boolean useIterableSerializerToCollection;
	private final Class type;

	public JsonResponseWrapper(final Page<?> pageableValue, final Class type) {
		this.pageableValue = Optional.of(pageableValue);
		this.valueOptional = Optional.empty();
		this.collection = Optional.empty();
		this.type = type;
		this.useIterableSerializerToCollection = false;
	}

	public JsonResponseWrapper(final Object value) {
		this.pageableValue = Optional.empty();
		this.collection = Optional.empty();
		this.valueOptional = Optional.of(value);
		this.type = value.getClass();
		this.useIterableSerializerToCollection = false;
	}
	
	public JsonResponseWrapper(final Object value, final Class type) {
		this.pageableValue = Optional.empty();
		this.collection = Optional.empty();
		this.valueOptional = Optional.of(value);
		this.type = type;
		this.useIterableSerializerToCollection = false;
	}
	
	
	public JsonResponseWrapper(final DomainCollectionFunction value, final Class type) {
		final Iterable iterable = value.apply();
		if(iterable instanceof Page) {
			this.pageableValue = Optional.of((Page) iterable);
			this.collection = Optional.empty();
			this.useIterableSerializerToCollection = false;

		} else {
			this.pageableValue = Optional.empty();
			this.collection = Optional.of((Collection) iterable);
			this.useIterableSerializerToCollection = true;
		}
		this.valueOptional = Optional.empty();
		this.type = type;
	}

	public JsonResponseWrapper(final Collection<?> collection, final Class type) {
		this(collection, type, false);
	}
	
	public JsonResponseWrapper(final Collection<?> collection, final Class type, boolean useIterableSerializerToCollection) {
		this.pageableValue = Optional.empty();
		this.valueOptional = Optional.empty();
		this.collection = Optional.of(collection);
		this.type = type;
		this.useIterableSerializerToCollection = useIterableSerializerToCollection;
	}

	@SuppressWarnings("unused")
	private JsonResponseWrapper() {
		this.pageableValue = null;
		this.valueOptional = null;
		this.collection = null;
		this.type = null;
		this.useIterableSerializerToCollection = false;
	}
}
