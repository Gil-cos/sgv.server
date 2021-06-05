package br.com.gilberto.sgv.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.gilberto.sgv.infra.wrappers.SingleValueWrapper;
import br.com.gilberto.sgv.domain.route.Route;
import br.com.gilberto.sgv.domain.route.RouteApplicationServices;
import br.com.gilberto.sgv.domain.route.RouteStatus;
import br.com.gilberto.sgv.infra.deserializers.RouteDeserializer;
import br.com.gilberto.sgv.infra.wrappers.JsonResponseWrapper;
import br.com.gilberto.sgv.infra.wrappers.PassengersWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@Api(value = "Rotas")
@RequestMapping("/routes")
@AllArgsConstructor
public class RouteController {

	private final RouteApplicationServices services;
	
	@PostMapping("/create")
	@ApiOperation(value = "Cria uma nova Rota")
	@ResponseStatus(HttpStatus.CREATED)
	public JsonResponseWrapper create(@JsonDeserialize(using = RouteDeserializer.class) final Route route) {
		return new JsonResponseWrapper(services.save(route));
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Busca uma Rota pelo ID")
	public JsonResponseWrapper getById(@PathVariable("id") final Long id) {
		return new JsonResponseWrapper(services.findById(id));
	}
	
	@GetMapping("/{userId}/user")
	@ApiOperation(value = "Lista as Rotas de acordo com a Role")
	public JsonResponseWrapper listAll(@PathVariable("userId") final Long userId) {
		return new JsonResponseWrapper(services.findAll(userId), Route.class);
	}
	
	@GetMapping("/passengers/{id}")
	@ApiOperation(value = "Lista os Passageiros de uma Rota")
	public PassengersWrapper listPassengers(@PathVariable("id") final Long id) {
		return new PassengersWrapper(id);
	}
	
	@PutMapping("{routeId}/passengers/add/{passengerId}")
	@ApiOperation(value = "Adiciona um Passageiro a uma Rota")
	public JsonResponseWrapper addPassenger(@PathVariable("routeId") final Long routeId, @PathVariable("passengerId") final Long passengerId) {
		return new JsonResponseWrapper(services.addPassenger(routeId, passengerId), Route.class);
	}
	
	@PutMapping("{routeId}/link")
	@ApiOperation(value = "Adiciona um Passageiro a uma Rota")
	public void saveSharedLocationLink(@PathVariable("routeId") final Long routeId, @RequestBody final SingleValueWrapper wrapper) {
		services.saveSharedLocationLink(routeId, wrapper.getValue().toString());
	}
	
	@PutMapping("{routeId}/passengers/remove/{passengerId}")
	@ApiOperation(value = "Remove um Passageiro de uma Rota")
	public JsonResponseWrapper removePassenger(@PathVariable("routeId") final Long routeId, @PathVariable("passengerId") final Long passengerId) {
		return new JsonResponseWrapper(services.removePassenger(routeId, passengerId), Route.class);
	}
	
	@PutMapping("{id}/status/{status}")
	@ApiOperation(value = "Muda o status da Rota")
	public JsonResponseWrapper changeStatus(@PathVariable("id") final Long routeId, @PathVariable("status") final RouteStatus status) {
		return new JsonResponseWrapper(services.changeStatus(routeId, status), Route.class);
	}
}
