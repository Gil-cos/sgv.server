package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.user.driver.DriverInfo;
import br.com.gilberto.sgv.domain.user.passanger.DriverInfoRepository;
import br.com.gilberto.sgv.domain.vehicle.Vehicle;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DriverInfoDeserializer extends AbstractDeserializer<DriverInfo> {
	
	private final DriverInfoRepository driverInfoRepository;
	private final VehicleDeserializer vehicleDeserializer;

	@Override
	public DriverInfo deserializeNode(JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return driverInfoRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("DriverInfo not found"));
	}

	@Override
	public DriverInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		return deserialize(getJsonNode(jsonParser));
	}
	
	public DriverInfo deserialize(final JsonNode jsonNode) throws IOException {
		
		final Vehicle vehicle = vehicleDeserializer.deserialize(jsonNode.get("address"));
		
		if (getId(jsonNode) == null) {
			return new DriverInfo(vehicle);
		}
		
		final DriverInfo driverInfo = driverInfoRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("DriverInfo not found"));
		
		return driverInfo.update(vehicle);
	}

}
