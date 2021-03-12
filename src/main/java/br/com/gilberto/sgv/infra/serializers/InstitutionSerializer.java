package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.address.Address;
import br.com.gilberto.sgv.domain.institution.Institution;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InstitutionSerializer extends AbstractSerializer<Institution> {

	private final AddressSerializer addressSerializer;

	@Override
	public void serialize(final Institution value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("name", value.getName());
		serializeAddress(value.getAddress(), gen);
		gen.writeEndObject();
	}

	public void serializeAddress(final Address address, final JsonGenerator gen) throws IOException {
		if (address != null) {
			gen.writeFieldName("address");
			addressSerializer.serialize(address, gen, null);
		}
	}

}
