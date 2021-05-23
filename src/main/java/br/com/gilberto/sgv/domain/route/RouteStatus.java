package br.com.gilberto.sgv.domain.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouteStatus {
	
	STAND_BY(""),
	PREPARING("Preparando"),
	GOING("Indo até o destino"),
	RETURNING("Voltando para casa");
	
	private final String prettyName;
}
