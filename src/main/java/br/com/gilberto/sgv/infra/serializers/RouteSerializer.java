package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.route.Route;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RouteSerializer extends AbstractSimpleAndCompleteSerializer<Route> {

	private final InstitutionSerializer institutionSerializer;
	private final UserSerializer userSerializer;
	
	@Override
	public void simpleSerialize(final Route value, final JsonGenerator gen) throws IOException {
		gen.writeStartObject();
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("description", value.getDescription());
		userSerializer.simpleSerialize("driver", value.getDriver(), gen);
		gen.writeStringField("period", value.getPeriod().name());
		gen.writeStringField("sharedLocationLink", value.getSharedLocationLink());
		serializeInstitution(value.getInstitution(), gen);
		gen.writeEndObject();
	}

	@Override
	public void completeSerialize(final Route value, final JsonGenerator gen) throws IOException {
		if (value.getId() != null) {
			gen.writeNumberField("id", value.getId());
		}
		gen.writeStringField("description", value.getDescription());
		userSerializer.simpleSerialize("driver", value.getDriver(), gen);
		gen.writeStringField("period", value.getPeriod().name());
		gen.writeStringField("status", value.getStatus().name());
		gen.writeStringField("sharedLocationLink", value.getSharedLocationLink());
		serializeInstitution(value.getInstitution(), gen);
	}
	
	public void serializeInstitution(final Institution address, final JsonGenerator gen) throws IOException {
		if (address != null) {
			gen.writeFieldName("institution");
			institutionSerializer.serialize(address, gen, null);
		}
	}

	@Override
	public void serialize(final Route value, final JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// inherited
	}

}
