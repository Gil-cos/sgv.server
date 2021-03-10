package br.com.gilberto.sgv.domain.user;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {

	ADMIN("WEB-CTE-0002"),
	DRIVER("WEB-CTE"),
	PASSANGER("WEB-CTE-0001"),
	SYSTEM("SYSTEM");

	private final String role;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAuthority() {
		return this.getRole();
	}

}