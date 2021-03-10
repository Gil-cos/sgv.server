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
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDeserializer extends AbstractDeserializer<User> {

	private final UserRepository userRepository;
	private final AddressDeserializer addressDeserializer;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public User deserializeNode(final JsonNode jsonNode, final DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return userRepository.findById(getId(jsonNode))
				.orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public User deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {

		JsonNode node = getJsonNode(jsonParser);
		
		final String name = getAsString("name", node);
		final String phone = getAsString("phone", node);
		final String cpf = getAsString("cpf", node);
		final String email = getAsString("email", node);
		final String password = encodePassword(getAsString("password", node));
		final Address address = addressDeserializer.deserialize(node.get("address"));
		final Role role = Role.valueOf(getAsString("role", node));
		
		if (getId(node) == null) {
			return new User(name, phone, cpf, email, password, address, role);
		}
		
		final User user = userRepository.findById(getId(node))
				.orElseThrow(() -> new NotFoundException("User not found"));
		
		return user.update(name, phone, cpf, address);
	}
	
	private String encodePassword(final String password) {
		return passwordEncoder.encode(password);
	}

}
