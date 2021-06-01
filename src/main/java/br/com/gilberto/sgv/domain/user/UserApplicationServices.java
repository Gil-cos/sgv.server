package br.com.gilberto.sgv.domain.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.gilberto.sgv.domain.route.Route;
import br.com.gilberto.sgv.domain.route.RouteRepository;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationServices {

	private final UserRepository userRepository;
	private final UserSpecification userSpecification;
	private final RouteRepository routeRepository;

	@Transactional
	public User save(final User user) {
		return userRepository.save(user);
	}

	public User findById(final Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found'"));
	}

	public Optional<User> findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public User update(final User user) {
		final User userSaved = userRepository.save(user);
		return userSaved;
	}

	public List<User> findAll(final UserFilter filters) {
		final Specification<User> specification = userSpecification.toSpecification(filters);
		return userRepository.findAll(specification);
	}

	@Transactional
	public User comfirmPresence(final Long routeId, final Long passengerId) {
		final User passenger = findById(passengerId);
		final Route route = routeRepository.findById(routeId).orElseThrow(() -> new NotFoundException("Route not found'"));
		passenger.confirmPresence(route);
		return passenger;
	}
	
	@Transactional
	public User declinePresence(final Long routeId, final Long passengerId) {
		final User passenger = findById(passengerId);
		final Route route = routeRepository.findById(routeId).orElseThrow(() -> new NotFoundException("Route not found'"));
		passenger.declinePresence(route);
		return passenger;
	}

	@Transactional
	public User clearConfirmation(final Long routeId, final Long passengerId) {
		final User passenger = findById(passengerId);
		final Route route = routeRepository.findById(routeId).orElseThrow(() -> new NotFoundException("Route not found'"));
		passenger.clearPresenceConfirmation(route);
		return passenger;
	}

}
