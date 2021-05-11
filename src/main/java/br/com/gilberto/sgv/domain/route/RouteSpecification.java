package br.com.gilberto.sgv.domain.route;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.gilberto.sgv.infra.specification.BaseSpecification;

@Component
public class RouteSpecification extends BaseSpecification<Route, RouteFilter> {

	@Override
	public Specification<Route> toSpecification(RouteFilter filter) {
		return (root, query, cb) -> {
			query.distinct(true);
			return where(where(attributeContainsWithJoin("driver", filter.getDriver(), "id")))
					.toPredicate(root, query, cb);
		};
	}
}
