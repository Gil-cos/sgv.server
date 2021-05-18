package br.com.gilberto.sgv.domain.route;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.domain.user.UserApplicationServices;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteApplicationServices {

	private final RouteRepository routeRepository;
	private final UserApplicationServices userApplicationServices;
	private final RouteSpecification routeSpecification;

	@Transactional
	public Route save(final Route route) {
		return routeRepository.save(route);
	}

	public Route findById(final Long id) {
		return routeRepository.findById(id).orElseThrow(() -> new NotFoundException("Route not found'"));
	}
	
	public List<Route> findAll(final RouteFilter filters) {
		final Specification<Route> specification = routeSpecification.toSpecification(filters);
		return routeRepository.findAll(specification);
	}

	public Set<User> getPassengers(final Long id) {
		final Route route = findById(id);
		return route.getPassengers();
	}

	@Transactional
	public Route addPassenger(final Long routeId, final Long passengerId) {
		final Route route = findById(routeId);
		route.addPassenger(userApplicationServices.findById(passengerId));
		return route;
	}
	
	@Transactional
	public Route removePassenger(final Long routeId, final Long passengerId) {
		final Route route = findById(routeId);
		route.removePassenger(userApplicationServices.findById(passengerId));
		return route;
	}
}
