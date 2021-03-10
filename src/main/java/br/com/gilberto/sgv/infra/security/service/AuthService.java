package br.com.gilberto.sgv.infra.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.domain.user.UserApplicationServices;

@Service
public class AuthService implements UserDetailsService {
	
	@Autowired
	private UserApplicationServices userService;

	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		Optional<User> usuario = userService.findByEmail(userName);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos!");
	}

}
