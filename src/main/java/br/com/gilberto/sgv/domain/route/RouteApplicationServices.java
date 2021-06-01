package br.com.gilberto.sgv.domain.route;

import java.util.Set;

import javax.transaction.Transactional;

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

	@Transactional
	public Route save(final Route route) {
		return routeRepository.save(route);
	}

	public Route findById(final Long id) {
		return routeRepository.findById(id).orElseThrow(() -> new NotFoundException("Route not found'"));
	}
	
	public Set<Route> findAll(final Long userId) {
		final User user = userApplicationServices.findById(userId);
		return user.isDriver() ? user.getOwnerRoutes() : user.getRoutes();
	}

	@Transactional
	public Route addPassenger(final Long routeId, final Long passengerId) {
		final Route route = findById(routeId);
		final User passenger = userApplicationServices.findById(passengerId);
		route.addPassenger(passenger);
		return route;
	}
	
	@Transactional
	public Route removePassenger(final Long routeId, final Long passengerId) {
		final Route route = findById(routeId);
		route.removePassenger(userApplicationServices.findById(passengerId));
		return route;
	}
	
	@Transactional
	public Route changeStatus(final Long id, final RouteStatus status) {
		final Route route = findById(id);
		route.updateStatus(status);
		return route;
	}
}
