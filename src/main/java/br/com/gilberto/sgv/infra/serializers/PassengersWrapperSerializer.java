package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.gilberto.sgv.domain.route.Route;
import br.com.gilberto.sgv.domain.route.RouteRepository;
import br.com.gilberto.sgv.domain.user.PresenceConfirmation;
import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import br.com.gilberto.sgv.infra.wrappers.PassengersWrapper;
import lombok.RequiredArgsConstructor;

@JsonComponent
@RequiredArgsConstructor
public class PassengersWrapperSerializer extends AbstractSerializer<PassengersWrapper> {

	private final RouteRepository routeRepository;
	private final UserSerializer userSerializer;

	@Override
	public void serialize(final PassengersWrapper value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {

		final Route route = routeRepository.findById(value.getId())
				.orElseThrow(() -> new NotFoundException("Route not found'"));

		serializePassengers(route, gen);
	}

	private void serializePassengers(final Route route, final JsonGenerator gen) throws IOException {
		gen.writeStartArray();
		for (final User passenger : route.getPassengers()) {
			gen.writeStartObject();
			userSerializer.completeSerialize(passenger, gen);
			getPassengerConfirmation(route, passenger, gen);
			gen.writeEndObject();
		}
		gen.writeEndArray();
	}

	private void getPassengerConfirmation(final Route route, final User passenger, final JsonGenerator gen) throws IOException {
		final Optional<PresenceConfirmation> optional = passenger.getPresenceConfirmations().stream()
				.filter(p -> p.getRoute().equals(route)).findAny();
		if (optional.isPresent()) {
			gen.writeBooleanField("isConfirmed", optional.get().isCorfimed());		
		} else {
			gen.writeNullField("isConfirmed");
		}
	}
}
