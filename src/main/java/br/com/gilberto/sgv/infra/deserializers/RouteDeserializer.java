package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.domain.institution.Institution;
import br.com.gilberto.sgv.domain.route.Period;
import br.com.gilberto.sgv.domain.route.Route;
import br.com.gilberto.sgv.domain.route.RouteRepository;
import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RouteDeserializer extends AbstractDeserializer<Route> {

	private final RouteRepository routeRepository;
	private final UserDeserializer userDeserializer;
	private final InstitutionDeserializer institutionDeserializer;

	@Override
	public Route deserializeNode(final JsonNode jsonNode, final DeserializationContext deserializationContext)
			throws IOException, NotFoundException {
		return routeRepository.findById(getId(jsonNode)).orElseThrow(() -> new NotFoundException("Route not found"));
	}

	@Override
	public Route deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {

		JsonNode node = getJsonNode(jsonParser);

		final String description = getAsString("description", node);
		final Period period = Period.valueOf(getAsString("period", node));
		final User driver = userDeserializer.deserializeNode(node.get("driver"), deserializationContext);
		final Institution institution = institutionDeserializer.deserialize(node.get("institution"));
//		final Set<User> passengers = deserializePassengers(node.get("passengers"), deserializationContext);

		if (getId(node) == null) {
			return new Route(description, period, driver, institution);
		}

		final Route route = routeRepository.findById(getId(node))
				.orElseThrow(() -> new NotFoundException("Route not found"));

		return route.update(description, period, driver, institution);
	}

//	private Set<User> deserializePassengers(final JsonNode users, final DeserializationContext deserializationContext)
//			throws NotFoundException, IOException {
//		final Set<User> set = new HashSet<>();
//
//		for (JsonNode user : users) {
//			set.add(userDeserializer.deserializeNode(user, deserializationContext));
//		}
//		return set;
//	}

}
