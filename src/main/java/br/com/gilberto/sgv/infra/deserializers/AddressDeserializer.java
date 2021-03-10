package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.address.Address;
import br.com.gilberto.sgv.domain.address.AddressRepository;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AddressDeserializer extends AbstractDeserializer<Address> {
	
	private final AddressRepository addressRepository;
	
	@Override
	public Address deserializeNode(final JsonNode jsonNode, final DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return addressRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Address not found"));
	}

	@Override
	public Address deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {
		return deserialize(getJsonNode(jsonParser));
	}
	
	public Address deserialize(final JsonNode jsonNode) throws IOException {
		
		final String cep = getAsString("cep", jsonNode);
		final String street = getAsString("street", jsonNode);
		final String neighborhood = getAsString("neighborhood", jsonNode);
		final String city = getAsString("city", jsonNode);
		final String number = getAsString("number", jsonNode);
		
		if (getId(jsonNode) == null) {
			return new Address(cep, street, neighborhood, city, number);
		}
		
		final Address address = addressRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Address not found"));
		
		return address.update(cep, street, neighborhood, city, number);
	}

}
