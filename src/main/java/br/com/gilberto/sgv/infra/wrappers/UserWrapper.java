package br.com.gilberto.sgv.infra.wrappers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.infra.serializers.UserWrapperSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = UserWrapperSerializer.class)
public class UserWrapper {
	
	private final User user;

}
