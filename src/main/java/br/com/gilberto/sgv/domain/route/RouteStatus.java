package br.com.gilberto.sgv.domain.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouteStatus {
	
	STAND_BY(""),
	PREPARING("Em Preparação"),
	TRAVELING("Em Viagem");
	
	private final String prettyName;
}
