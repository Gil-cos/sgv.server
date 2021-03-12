package br.com.gilberto.sgv.domain.user;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {

	ADMIN("ADMIN"),
	DRIVER("DRIVER"),
	PASSANGER("PASSANGER"),
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