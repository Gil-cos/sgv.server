package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.address.Address;
import br.com.gilberto.sgv.domain.user.Role;
import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.domain.user.UserRepository;
import br.com.gilberto.sgv.domain.user.driver.DriverInfo;
import br.com.gilberto.sgv.domain.user.passenger.PassengerInfo;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDeserializer extends AbstractDeserializer<User> {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AddressDeserializer addressDeserializer;
	private final PassengerInfoDeserializer passangerInfoDeserializer;
	private final DriverInfoDeserializer driverInfoDeserializer;

	@Override
	public User deserializeNode(final JsonNode jsonNode, final DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return userRepository.findById(getId(jsonNode)).orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public User deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {

		JsonNode node = getJsonNode(jsonParser);

		final String name = getAsString("name", node);
		final String phone = getAsString("phone", node);
		final String cpf = getAsString("cpf", node);
		final String email = getAsString("email", node);
		final String password = encodePassword(getAsString("password", node));
		final String notificationToken = getAsString("notificationToken", node);
		final Role role = Role.valueOf(getAsString("role", node));
		final Address address = deserializeAddress(node);
		final PassengerInfo passangerInfo = deserializePassangerInfo(node);
		final DriverInfo driverInfo = deserializeDriverInfo(node);

		if (getId(node) == null) {
			return new User(name, phone, cpf, email, password, notificationToken, role);
		}

		final User user = userRepository.findById(getId(node))
				.orElseThrow(() -> new NotFoundException("User not found"));

		return user.update(name, phone, cpf, address, passangerInfo, driverInfo);
	}

	private Address deserializeAddress(final JsonNode node) throws IOException {
		if (isNodeNotNull(node.get("address"))) {
			return addressDeserializer.deserialize(node.get("address"));
		}
		return null;
	}

	private PassengerInfo deserializePassangerInfo(final JsonNode node) throws IOException {
		if (isNodeNotNull(node.get("passangerInfo"))) {
			return passangerInfoDeserializer.deserialize(node.get("passangerInfo"));
		}
		return null;
	}

	private DriverInfo deserializeDriverInfo(final JsonNode node) throws IOException {
		if (isNodeNotNull(node.get("driverInfo"))) {
			return driverInfoDeserializer.deserialize(node.get("driverInfo"));
		}
		return null;
	}

	private String encodePassword(final String password) {
		return password != null ? passwordEncoder.encode(password) : password;
	}

}
