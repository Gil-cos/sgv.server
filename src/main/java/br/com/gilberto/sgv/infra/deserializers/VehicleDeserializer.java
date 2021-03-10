package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.vehicle.Vehicle;
import br.com.gilberto.sgv.domain.vehicle.VehicleRepository;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VehicleDeserializer extends AbstractDeserializer<Vehicle> {
	
	private final VehicleRepository vehicleRepository;

	@Override
	public Vehicle deserializeNode(JsonNode jsonNode, DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return vehicleRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Vehicle not found"));
	}

	@Override
	public Vehicle deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		return deserialize(getJsonNode(jsonParser));
	}
	
	public Vehicle deserialize(final JsonNode jsonNode) throws IOException {
		
		final String brand = getAsString("brand", jsonNode);
		final String model = getAsString("model", jsonNode);
		final String licensePlate = getAsString("licensePlate", jsonNode);
		final Integer numberOfSeats = getAsInteger("numberOfSeats", jsonNode);
		
		if (getId(jsonNode) == null) {
			return new Vehicle(brand, model, licensePlate, numberOfSeats);
		}
		
		final Vehicle vehicle = vehicleRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("Vehicle not found"));
		
		return vehicle.update(brand, model, licensePlate, numberOfSeats);
	}

}
