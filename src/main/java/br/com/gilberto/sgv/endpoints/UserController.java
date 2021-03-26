package br.com.gilberto.sgv.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.domain.user.UserApplicationServices;
import br.com.gilberto.sgv.infra.deserializers.UserDeserializer;
import br.com.gilberto.sgv.infra.wrappers.JsonResponseWrapper;
import br.com.gilberto.sgv.infra.wrappers.UserWrapper;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	private final UserApplicationServices services;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public JsonResponseWrapper create(@JsonDeserialize(using = UserDeserializer.class) final User user) {
		return new JsonResponseWrapper(services.save(user));
	}
	
	@PutMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	public JsonResponseWrapper update(@JsonDeserialize(using = UserDeserializer.class) final User user) {
		return new JsonResponseWrapper(services.update(user));
	}
	
    @GetMapping("/me")
    public UserWrapper get(final Authentication authentication) {
        return new UserWrapper(services.findByEmail(authentication.getName()).get());
    }
}
