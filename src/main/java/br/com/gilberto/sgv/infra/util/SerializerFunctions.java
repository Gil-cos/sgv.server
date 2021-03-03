package br.com.gilberto.sgv.infra.util;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SerializerFunctions<T> {
	
	private final TwoArgumentsVoidFunction<T, JsonGenerator> valueSerializer;
	private final TwoArgumentsVoidFunction<T, JsonGenerator> iterableSerializer;
	
	public SerializerFunctions(final TwoArgumentsVoidFunction<T, JsonGenerator> valueSerializer) {
		this.iterableSerializer = (t , jsonGenerator) -> {
			throw new UnsupportedOperationException("Pageable serializer function not implemented");
		};
		this.valueSerializer = valueSerializer;
	}

}
