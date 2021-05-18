package br.com.gilberto.sgv.domain.user;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.gilberto.sgv.infra.specification.BaseSpecification;

@Component
public class UserSpecification  extends BaseSpecification<User, UserFilter> {

	@Override
	public Specification<User> toSpecification(UserFilter filter) {
		return (root, query, cb) -> {
			query.distinct(true);
			return where(attributeContains("name", filter.getName()))
					.and(attributeEqual("role", Arrays.asList(Role.PASSENGER)))
					.toPredicate(root, query, cb);
		};
	}

}
