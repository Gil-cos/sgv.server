package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;

public abstract class AbstractCollectionSerializer<T> extends AbstractSimpleAndCompleteSerializer<T> {

	protected void completeSerializeAsListWithStart(final String fieldName, final Collection<T> values, final JsonGenerator gen) throws IOException {
		gen.writeFieldName(fieldName);
		gen.writeStartArray();
        for (final T value : values) {
        	gen.writeStartObject();
            completeSerialize(value, gen);
            gen.writeEndObject();
        }
        gen.writeEndArray();
	}
	
    protected void completeSerialize(final List<T> values, final JsonGenerator gen) throws IOException {
        gen.writeStartArray();
        for (final T value : values) {
            gen.writeStartObject();
            completeSerialize(value, gen);
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
	
}
