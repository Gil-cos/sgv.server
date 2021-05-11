package br.com.gilberto.sgv.domain.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RouteFilter {

	private final List<Long> driver;
	
}
