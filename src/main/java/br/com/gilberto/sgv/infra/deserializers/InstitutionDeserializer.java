package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.address.Address;
import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.institution.InstitutionRepository;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InstitutionDeserializer extends AbstractDeserializer<Institution> {
	
	private final InstitutionRepository institutionRepository;
	private final AddressDeserializer addressDeserializer;

	@Override
	public Institution deserializeNode(JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return institutionRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Institution not found"));
	}

	@Override
	public Institution deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		return deserialize(getJsonNode(jsonParser));
	}
	
	public Institution deserialize(final JsonNode jsonNode) throws IOException {
		
		final String name = getAsString("name", jsonNode);
		final Address address = addressDeserializer.deserialize(jsonNode.get("address"));
		
		if (getId(jsonNode) == null) {
			return new Institution(name, address);
		}
		
		final Institution institution = institutionRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Institution not found"));
		
		return institution.update(name, address);
	}
}
