package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.address.Address;
import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.domain.user.driver.DriverInfo;
import br.com.gilberto.sgv.domain.user.passenger.PassengerInfo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSerializer extends AbstractSimpleAndCompleteSerializer<User> {

	private final AddressSerializer addressSerializer;
	private final PassangerInfoSerializer passengerInfoSerializer;
	private final DriverInfoSerializer driverInfoSerializer;
	
	@Override
	public void simpleSerialize(User value, JsonGenerator gen) throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("name", value.getName());
		gen.writeStringField("phone", value.getPhone());
		gen.writeStringField("cpf", value.getCpf());
		gen.writeStringField("email", value.getEmail());
		gen.writeStringField("role", value.getRole().getRole());
		serializeAddress(value.getAddress(), gen);
		serializePassengerInfo(value.getPassengerInfo(), gen);
		serializeDriverInfo(value.getDriverInfo(), gen);
		gen.writeEndObject();
	}

	@Override
	public void completeSerialize(User value, JsonGenerator gen) throws IOException {
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("name", value.getName());
		gen.writeStringField("phone", value.getPhone());
		gen.writeStringField("cpf", value.getCpf());
		gen.writeStringField("email", value.getEmail());
		gen.writeStringField("role", value.getRole().getRole());
		serializeAddress(value.getAddress(), gen);
		serializePassengerInfo(value.getPassengerInfo(), gen);
		serializeDriverInfo(value.getDriverInfo(), gen);
	}
	
	public void serializeAddress(final Address address, final JsonGenerator gen) throws IOException {
		if (address != null) {
			gen.writeFieldName("address");
			addressSerializer.serialize(address, gen, null);
		}
	}
	
	public void serializePassengerInfo(final PassengerInfo passangerInfo, final JsonGenerator gen) throws IOException {
		if (passangerInfo != null) {
			gen.writeFieldName("passengerInfo");
			passengerInfoSerializer.serialize(passangerInfo, gen, null);
		}
	}
	
	public void serializeDriverInfo(final DriverInfo driverInfo, final JsonGenerator gen) throws IOException {
		if (driverInfo != null) {
			gen.writeFieldName("driverInfo");
			driverInfoSerializer.serialize(driverInfo, gen, null);
		}
	}

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// inherited
	}

}
