package br.com.gilberto.sgv.infra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gilberto.sgv.infra.security.token.TokenService;
import br.com.gilberto.sgv.infra.security.wrapper.LoginWrapper;
import br.com.gilberto.sgv.infra.security.wrapper.TokenDto;

@RestController
public class AuthRestController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;


	@PostMapping(value = "/api/auth")
	public ResponseEntity<TokenDto> authenticate(@RequestBody final LoginWrapper form) {
		UsernamePasswordAuthenticationToken authenticationToken = form.converter();
		
		try {
			Authentication authentication = authManager.authenticate(authenticationToken);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
