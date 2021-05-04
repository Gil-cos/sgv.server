package br.com.gilberto.sgv.domain.route;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteApplicationServices {

	private final RouteRepository routeRepository;

	@Transactional
	public Route save(final Route route) {
		return routeRepository.save(route);
	}
}
