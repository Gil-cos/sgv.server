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
import br.com.gilberto.sgv.domain.user.UserFilter;
import br.com.gilberto.sgv.infra.deserializers.UserDeserializer;
import br.com.gilberto.sgv.infra.wrappers.JsonResponseWrapper;
import br.com.gilberto.sgv.infra.wrappers.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@Api(value = "Usuários")
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	private final UserApplicationServices services;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cria um novo Usuário")
	public JsonResponseWrapper create(@JsonDeserialize(using = UserDeserializer.class) final User user) {
		return new JsonResponseWrapper(services.save(user));
	}
	
	@PutMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Atualiza um Usuário")
	public JsonResponseWrapper update(@JsonDeserialize(using = UserDeserializer.class) final User user) {
		return new JsonResponseWrapper(services.update(user));
	}
	
    @GetMapping("/me")
    @ApiOperation(value = "Busca as informações do Usuário")
    public UserWrapper get(final Authentication authentication) {
        return new UserWrapper(services.findByEmail(authentication.getName()).get());
    }
    
	@GetMapping("/pages")
	@ApiOperation(value = "Lista todos os Usuários")
	public JsonResponseWrapper listAllPageable(final UserFilter filters) {
		return new JsonResponseWrapper(services.findAll(filters), User.class);
	}
}
