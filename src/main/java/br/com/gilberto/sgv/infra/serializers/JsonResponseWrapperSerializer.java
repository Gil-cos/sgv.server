package br.com.gilberto.sgv.infra.serializers;

import java.util.Map;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.infra.util.SerializerFunctions;
import br.com.gilberto.sgv.infra.wrappers.JsonResponseWrapper;
import lombok.RequiredArgsConstructor;

@JsonComponent
@RequiredArgsConstructor
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JsonResponseWrapperSerializer extends AbstractSerializer<JsonResponseWrapper> {

	private final Map<Class, SerializerFunctions> commonWrapperSerializerMap;

	@Override
	public void serialize(final JsonResponseWrapper commonWrapper, final JsonGenerator gen, final SerializerProvider serializers) {
		final SerializerFunctions serializerFunctions = commonWrapperSerializerMap.get(commonWrapper.getType());
		commonWrapper.getPageableValue().ifPresent(pageable -> applySerialize(() -> {
			gen.writeStartObject();
			gen.writeFieldName("content");
			gen.writeStartArray();
			pageable.getContent().forEach(
					page -> applySerialize(() -> serializerFunctions.getIterableSerializer().apply(page, gen)));
			gen.writeEndArray();
			paging(gen,
					pageable);
			gen.writeEndObject();
		}));
		commonWrapper.getValueOptional().ifPresent(valueToSerialize -> applySerialize(() -> {
			gen.writeStartObject();
			if(commonWrapper.getType().isInstance(valueToSerialize)) {
				serializerFunctions.getValueSerializer().apply(commonWrapper.getType().cast(valueToSerialize),
						gen);
			}
			gen.writeEndObject();
		}));
		commonWrapper.getCollection().ifPresent(valueToSerialize -> applySerialize(() -> {
			gen.writeStartArray();
			valueToSerialize.forEach(valueInsideCollection -> applySerialize(() -> {
				if (commonWrapper.isUseIterableSerializerToCollection()) {
					serializerFunctions.getIterableSerializer().apply(valueInsideCollection,
							gen);
				} else {
					gen.writeStartObject();
					if(commonWrapper.getType().isInstance(valueInsideCollection)) {
						serializerFunctions.getValueSerializer().apply(commonWrapper.getType().cast(valueInsideCollection),
								gen);
					}
					gen.writeEndObject();
				}
			}));
			gen.writeEndArray();
		}));
	}

}
