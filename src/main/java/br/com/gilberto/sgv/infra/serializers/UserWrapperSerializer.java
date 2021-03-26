package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.infra.wrappers.UserWrapper;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserWrapperSerializer extends AbstractSimpleAndCompleteSerializer<UserWrapper> {

	private final UserSerializer userSerializer;
	
	@Override
	public void simpleSerialize(UserWrapper value, JsonGenerator gen) throws IOException {
		userSerializer.simpleSerialize(value.getUser(), gen);
	}

	@Override
	public void completeSerialize(UserWrapper value, JsonGenerator gen) throws IOException {
		userSerializer.simpleSerialize(value.getUser(), gen);
	}

	@Override
	public void serialize(UserWrapper value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		userSerializer.simpleSerialize(value.getUser(), gen);
	}

}
