package br.com.gilberto.sgv.infra.wrappers;

import lombok.Getter;

@Getter
public class PassengersWrapper {
	
	private final Long id;
	
	public PassengersWrapper(final Long id) {
		this.id = id;
	}

}
