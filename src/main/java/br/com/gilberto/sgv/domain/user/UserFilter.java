package br.com.gilberto.sgv.domain.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFilter {

	private final List<String> name;
	private final List<Long> id;
}
