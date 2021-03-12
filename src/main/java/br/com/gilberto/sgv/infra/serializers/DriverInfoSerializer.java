package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.user.driver.DriverInfo;
import br.com.gilberto.sgv.domain.vehicle.Vehicle;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriverInfoSerializer extends AbstractSerializer<DriverInfo> {

	private final VehicleSerializer vehicleSerializer;

	@Override
	public void serialize(final DriverInfo value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		serializeVehicle(value.getVehicle(), gen);
		gen.writeEndObject();
	}

	public void serializeVehicle(final Vehicle vehicle, final JsonGenerator gen) throws IOException {
		if (vehicle != null) {
			gen.writeFieldName("vehicle");
			vehicleSerializer.serialize(vehicle, gen, null);
		}
	}
}
