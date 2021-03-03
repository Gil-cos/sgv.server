package br.com.gilberto.sgv.infra.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public abstract class AbstractSimpleAndCompleteSerializer<T> extends AbstractSerializer<T> {
	
	protected abstract void simpleSerialize(final T value, final JsonGenerator gen) throws IOException;

	protected abstract void completeSerialize(final T value, final JsonGenerator gen) throws IOException;
	
	protected void simpleSerialize(final String fieldName, final T value, final JsonGenerator gen) throws IOException{
		gen.writeFieldName(fieldName);
		simpleSerialize(value, gen);
	}

	protected void completeSerialize(final String fieldName, final T value, final JsonGenerator gen) throws IOException{
		gen.writeFieldName(fieldName);
		completeSerialize(value, gen);
	}

	@Override
	public abstract void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException;
	

}
