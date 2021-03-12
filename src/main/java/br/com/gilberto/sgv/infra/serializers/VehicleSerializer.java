package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.vehicle.Vehicle;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VehicleSerializer extends AbstractSerializer<Vehicle> {

	@Override
	public void serialize(final Vehicle value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("brand", value.getBrand());
		gen.writeStringField("model", value.getModel());
		gen.writeStringField("licensePlate", value.getLicensePlate());
		gen.writeNumberField("numberOfSeats", value.getNumberOfSeats());
		gen.writeEndObject();
	}

}
