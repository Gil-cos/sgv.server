package br.com.gilberto.sgv.domain.route;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteRepository extends PagingAndSortingRepository<Route, Long>, JpaSpecificationExecutor<Route> {

}
