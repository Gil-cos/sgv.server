package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.user.passenger.PassengerInfoRepository;
import br.com.gilberto.sgv.domain.user.passenger.PassengerInfo;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PassengerInfoDeserializer extends AbstractDeserializer<PassengerInfo> {
	
	private final PassengerInfoRepository passengerInfoRepository;
	private final InstitutionDeserializer institutionDeserializer;
	
	@Override
	public PassengerInfo deserializeNode(JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return passengerInfoRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Passenger-info not found"));
	}

	@Override
	public PassengerInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		return deserialize(getJsonNode(jsonParser));
	}
	
	public PassengerInfo deserialize(final JsonNode jsonNode) throws IOException {
		
		final Institution institution = institutionDeserializer.deserialize(jsonNode.get("institution"));
		
		if (getId(jsonNode) == null) {
			return new PassengerInfo(institution);
		}
		
		final PassengerInfo passangerInfo = passengerInfoRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Passenger-info not found"));
		
		return passangerInfo.update(institution);
	}

}
