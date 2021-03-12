package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.user.passanger.PassangerInfo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PassangerInfoSerializer extends AbstractSerializer<PassangerInfo> {

	private final InstitutionSerializer institutionSerializer;

	@Override
	public void serialize(final PassangerInfo value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		serializeInstitution(value.getInstitution(), gen);
		gen.writeEndObject();
	}

	public void serializeInstitution(final Institution institution, final JsonGenerator gen) throws IOException {
		if (institution != null) {
			gen.writeFieldName("institution");
			institutionSerializer.serialize(institution, gen, null);
		}
	}

}
