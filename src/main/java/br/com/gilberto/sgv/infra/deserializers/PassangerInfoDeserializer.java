package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.user.passanger.PassangerInfo;
import br.com.gilberto.sgv.domain.user.passanger.PassangerInfoRepository;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PassangerInfoDeserializer extends AbstractDeserializer<PassangerInfo> {
	
	private final PassangerInfoRepository passangerInfoRepository;
	private final InstitutionDeserializer institutionDeserializer;
	
	@Override
	public PassangerInfo deserializeNode(JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return passangerInfoRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Passanger-info not found"));
	}

	@Override
	public PassangerInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		return deserialize(getJsonNode(jsonParser));
	}
	
	public PassangerInfo deserialize(final JsonNode jsonNode) throws IOException {
		
		final Institution institution = institutionDeserializer.deserialize(jsonNode.get("institution"));
		
		if (getId(jsonNode) == null) {
			return new PassangerInfo(institution);
		}
		
		final PassangerInfo passangerInfo = passangerInfoRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Passanger-info not found"));
		
		return passangerInfo.update(institution);
	}

}
