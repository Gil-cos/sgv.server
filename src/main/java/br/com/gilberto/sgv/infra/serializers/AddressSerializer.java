package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.address.Address;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressSerializer extends AbstractSerializer<Address> {

	@Override
	public void serialize(final Address value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("cep", value.getCep());
		gen.writeStringField("street", value.getStreet());
		gen.writeStringField("neighborhood", value.getNeighborhood());
		gen.writeStringField("city", value.getCity());
		gen.writeStringField("number", value.getNumber());
		gen.writeEndObject();
	}

}
