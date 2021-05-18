package br.com.gilberto.sgv.infra.specification;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class BaseSpecification<T, U> {
	protected static final String WILDCARD = "%";
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TO_CHAR = "TO_CHAR";

	public abstract Specification<T> toSpecification(U request);

	protected <E> Specification<T> attributeContainsWithJoin(final String attributeJoin, final Object values, final String param) {
		return this.attributeContainsWithJoin(attributeJoin, Collections.singletonList(values), param);
	}

	protected <E> Specification<T> attributeContainsWithJoin(final String attributeJoin, final Set<?> values, final String param) {
		return this.attributeContainsWithJoin(attributeJoin, new ArrayList<>(values), param);
	}

	protected <E> Specification<T> attributeContainsWithJoin(final String attributeJoin, final List<?> values, final String param) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}
			final Path<?> join = this.joinPath(attributeJoin, root);
			final List<Predicate> predicates = new ArrayList<>();

			final Object firstValue = values.get(0);
			if (firstValue instanceof String) {
				for (final Object value : values) {
					predicates.add(cb.like(cb.lower(join.get(param)), this.containsLowerCase((String) value)));
				}
			} else {
				for (final Object value : values) {
					predicates.add(cb.equal(join.get(param), value));
				}
			}
			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}
	
	protected <E> Specification<T> attributeNotContainsWithJoin(final String attributeJoin, final List<?> values, final String param) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}
			final Path<?> join = this.joinPath(attributeJoin, root);
			final List<Predicate> predicates = new ArrayList<>();

			final Object firstValue = values.get(0);
			if (firstValue instanceof String) {
				for (final Object value : values) {
					predicates.add(cb.like(cb.lower(join.get(param)), this.containsLowerCase((String) value)));
				}
			} else {
				for (final Object value : values) {
					predicates.add(cb.equal(join.get(param), value));
				}
			}
			return cb.or(predicates.toArray(new Predicate[values.size()])).not();
		};
	}

	protected <E> Specification<T> attributeContainsWithGet(final String attributeJoin, final List<?> values, final String param) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}
			final Path<?> join = this.getPath(attributeJoin, root);
			final List<Predicate> predicates = new ArrayList<>();

			final Object firstValue = values.get(0);
			if (firstValue instanceof String) {
				for (final Object value : values) {
					predicates.add(cb.like(cb.lower(join.get(param)), this.containsLowerCase((String) value)));
				}
			} else {
				for (final Object value : values) {
					predicates.add(cb.equal(join.get(param), value));
				}
			}
			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected <E> Specification<T> attributeContainsWithJoin(final String attributeJoin, final Boolean value, final String param) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			final Path<?> join = getPath(attributeJoin, root);
			return cb.or(cb.equal(join.get(param), value));
		};
	}

	protected Specification<T> attributeContainsUserWithJoin(final String attributeJoin, final List<String> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final Path<?> join = this.joinPath(attributeJoin, root);
			final List<Predicate> predicates = new ArrayList<>();
			for (final String value : values) {
				predicates.add(cb.like(cb.lower(join.get("name")), this.containsLowerCase(value)));
			}
			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeContains(final String attribute, final List<?> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Object value : values) {
				predicates.add(cb.like(cb.lower(root.get(attribute)), this.containsLowerCase(String.valueOf(value))));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeContainsNumber(final String attribute, final List<?> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Object value : values) {
				predicates.add(cb.like(root.get(attribute).as(String.class), this.containsLowerCase(String.valueOf(value))));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeEqualLong(final String attribute, final List<Long> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Long value : values) {
				predicates.add(cb.equal(root.get(attribute), value));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}
	
	protected Specification<T> attributeNotEqualLong(final String attribute, final List<Long> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Long value : values) {
				predicates.add(cb.notEqual(root.get(attribute), value));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeEqual(final String attribute, final List<?> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Object value : values) {
				predicates.add(cb.equal(cb.upper(root.get(attribute)), value));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}
	
	protected Specification<T> attributeEqualTo(final String attribute, final List<?> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Object value : values) {
				predicates.add(cb.equal(root.get(attribute), value));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeEqual(final String attribute, final Object value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.or(cb.equal(cb.upper(root.get(attribute)), value));
		};
	}

	protected Specification<T> attributeEqualEmbeddable(final String attribute, final Object value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.or(cb.equal(root.get(attribute), value));
		};
	}

	protected Specification<T> attributeEqualEmbeddable(final String attribute, final List<?> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final Object value : values) {
				predicates.add(cb.or(cb.equal(root.get(attribute), value)));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeEqual(final String attribute, final Boolean value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}

	protected Specification<T> attributeContainsBigDecimal(final String attribute, final List<BigDecimal> values) {
		return (root, query, cb) -> {
			if (CollectionUtils.isEmpty(values)) {
				return null;
			}

			final List<Predicate> predicates = new ArrayList<>();
			for (final BigDecimal value : values) {
				predicates.add(cb.equal(root.get(attribute), value));
			}

			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	protected Specification<T> attributeContainsDate(final String attribute, final LocalDate from, final LocalDate to) {
		return (root, query, cb) -> {
			if (from == null && to == null) {
				return null;
			}

			final Expression<String> date = cb.function(TO_CHAR,
					String.class,
					root.get(attribute),
					cb.literal(DATE_FORMAT));
			final String formattedFromDate = this.getFormattedDate(from, DATE_FORMAT);
			final String formattedToDate = this.getFormattedDate(to, DATE_FORMAT);

			if (from != null && to != null) {
				return cb.between(date, formattedFromDate, formattedToDate);
			} else if (from != null) {
				return cb.greaterThanOrEqualTo(date, formattedFromDate);
			}
			return cb.lessThanOrEqualTo(date, formattedToDate);
		};
	}

	protected Specification<T> attributeBetweenLongInterval(final String attribute, final Long from, final Long to) {
		return (root, query, cb) -> {
			if (from == null && to == null) {
				return null;
			}
			final Expression<Long> value = getPathWithJoin(attribute, root).as(Long.class);

			if (from != null && to != null) {
				return cb.between(value, from, to);
			} else if (from != null) {
				return cb.greaterThanOrEqualTo(value, from);
			}
			return cb.lessThanOrEqualTo(value, to);
		};
	}

	protected Specification<T> attributeContainsDateWithJoin(final String attributeJoin, final LocalDate from, final LocalDate to, final String attribute) {
		return (root, query, cb) -> {
			if (from == null && to == null) {
				return null;
			}

			final String formattedFromDate = this.getFormattedDate(from, DATE_FORMAT);
			final String formattedToDate = this.getFormattedDate(to, DATE_FORMAT);
			final Path<?> join = this.joinPath(attributeJoin, root);
			final Expression<String> dateExpression = cb.function(TO_CHAR,
					String.class,
					join.get(attribute),
					cb.literal(DATE_FORMAT));
			if (from != null && to != null) {
				return cb.between(dateExpression, formattedFromDate, formattedToDate);
			} else if (from != null) {
				return cb.greaterThanOrEqualTo(dateExpression, formattedFromDate);
			}
			return cb.lessThanOrEqualTo(dateExpression, formattedToDate);
		};
	}

	protected String containsLowerCase(final String searchField) {
		return BaseSpecification.WILDCARD + searchField.trim().toLowerCase() + BaseSpecification.WILDCARD;
	}

	protected String getFormattedDate(final LocalDate date, final String pattern) {
		if (date == null) {
			return null;
		}
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	protected Specification<T> name(final List<String> value) {
		return this.attributeContains("name", value);
	}

	protected Specification<T> isActive(final Boolean active) {
		if (active != null) {
			return (root, query, cb) -> cb.or(cb.equal(root.get("active"), active));
		}
		return null;
	}

	protected Path<?> getPath(final String path, final Root<?> root) {
		final String[] p = this.splitPath(path);
		Path<?> pathObj = root.join(p[0]);
		for (int i = 1; i < p.length; i++) {
			pathObj = pathObj.get(p[i]);
		}
		return pathObj;
	}

	protected Path<?> getPathWithJoin(final String path, final Root<?> root) {
		final String[] p = this.splitPath(path);
		if (p.length == 1) {
			return root.get(path);
		}
		Join<Object, Object> pathObj = root.join(p[0]);
		for (int i = 1; i < p.length - 1; i++) {
			pathObj = pathObj.join(p[i]);
		}
		return pathObj.get(p[p.length - 1]);
	}

	protected Path<?> joinPath(final String path, final Root<?> root) {
		final String[] p = this.splitPath(path);
		Join<Object, Object> pathObj = root.join(p[0]);
		for (int i = 1; i < p.length; i++) {
			pathObj = pathObj.join(p[i]);
		}
		return pathObj;
	}

	protected String[] splitPath(final String path) {
		String[] p = {path};
		if (path.contains(".")) {
			p = path.split("\\.");
		}
		return p;
	}

	public Specification<T> attributeNestedContains(final String attribute, final String path, final List<String> values) {
		return (root, query, cb) -> {
			if (values.isEmpty()) {
				return null;
			}
			final Join<T, ?> join = getNestedPath(path, root);

			final List<Predicate> predicates = new ArrayList<>();
			for (final String value : values) {
				predicates.add(cb.like(cb.lower(join.get(attribute)), this.containsLowerCase(value)));
			}
			return cb.or(predicates.toArray(new Predicate[values.size()]));
		};
	}

	public Specification<T> attributeNestedEqual(final String attribute, final String path, JoinType joinType, final Object... values) {
		return (root, query, cb) -> {
			if (values == null || values.length == 0) {
				return cb.or();
			}

			final Join<T, ?> join = getNestedPath(path, root, joinType);
			final List<Predicate> predicates = new ArrayList<>(values.length);

			for (final Object value : values) {
				predicates.add(cb.equal(join.get(attribute), value));
			}
			return cb.or(predicates.toArray(new Predicate[values.length]));
		};
	}

	public Specification<T> attributeNestedEqual(final String attribute, final String path, JoinType joinType, final List<?> values) {
		return attributeNestedEqual(attribute, path, joinType, values.toArray());
	}

	public Specification<T> attributeNestedEqual(final String attribute, final String path, final Object... values) {
		return this.attributeNestedEqual(attribute, path, JoinType.INNER, values);
	}

	public Specification<T> attributeNestedEqual(final String attribute, final Join<T, ?> join, final Object... values) {
		return (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			for (final Object value : values) {
				predicates.add(cb.equal(join.get(attribute), value));
			}
			return cb.or(predicates.toArray(new Predicate[values.length]));
		};
	}

	public Specification<T> attributeNestedEqual(final String attribute, final String path, final Long value) {
		return (root, query, cb) -> {
			if (value == null) {
				return cb.and(new Predicate[0]);
			}

			final Join<T, ?> join = getNestedPath(path, root);
			return cb.and(cb.equal(join.get(attribute), value));
		};
	}

	protected Specification<T> attributeContainsWithJoin(String path, String attribute, List<?> values, JoinType joinType) {
		return (root, query, cb) -> {
			if (values == null || values.isEmpty()) {
				return null;
			}
			final Join<T, ?> nestedPath = getNestedPath(path, root, joinType);
			final Predicate predicate = nestedPath.get(attribute).in(values);
			return cb.or(predicate);
		};
	}

	protected Specification<T> dateIsOnPeriod(final String initialDate, final String endDate, final LocalDate from, final LocalDate to) {
		return (root, query, cb) -> {
			if (from == null && to == null) {
				return null;
			}

			final Expression<String> initialDateExpression = cb.function(TO_CHAR, String.class, getPathWithJoin(initialDate, root),
					cb.literal(DATE_FORMAT));
			final Expression<String> endDateExpression = cb.function(TO_CHAR, String.class, getPathWithJoin(endDate, root),
					cb.literal(DATE_FORMAT));
			final String formattedFromDate = this.getFormattedDate(from, DATE_FORMAT);
			final String formattedToDate = this.getFormattedDate(to, DATE_FORMAT);


			if (from != null && to != null) {
				return cb.or(cb.between(initialDateExpression, formattedFromDate, formattedToDate), cb.between(endDateExpression, formattedFromDate, formattedToDate));

			}
			if (from != null) {
				return cb.greaterThanOrEqualTo(initialDateExpression, formattedFromDate);
			}
			return cb.and(cb.lessThanOrEqualTo(initialDateExpression, formattedToDate), cb.lessThanOrEqualTo(endDateExpression, formattedToDate));
		};
	}

	protected Specification<T> dateIsOnPeriod(final String initialDate, final LocalDate from, final LocalDate to) {
		return dateIsOnPeriod(initialDate, initialDate, from, to);
	}

	protected Join<T, ?> getNestedPath(final String path, final Root<T> root, final JoinType joinType) {
		final String[] splitPath = splitPath(path);
		Join<T, ?> join = root.join(splitPath[0], joinType);
		for (int i = 1; i < splitPath.length; i++) {
			join = join.join(splitPath[i], joinType);
		}
		return join;
	}

	protected Join<T, ?> concatJoin(final String path, final Join<T, ?> root, final JoinType joinType) {
		final String[] splitPath = splitPath(path);
		Join<T, ?> join = root.join(splitPath[0], joinType);
		for (int i = 1; i < splitPath.length; i++) {
			join = join.join(splitPath[i], joinType);
		}
		return join;
	}

	protected Join<T, ?> getNestedPath(final String path, final Root<T> root) {
		return getNestedPath(path, root, JoinType.INNER);
	}

	protected Join<T, ?> getPathLeftJoin(final String path, final Root<T> root) {
		return getNestedPath(path, root, JoinType.LEFT);
	}

	protected Specification<T> attributeIsOnPeriodOfValidity(final LocalDate from, final LocalDate to, final String initialDateField) {
		return (root, query, cb) -> {
			if (from == null && to == null) {
				return null;
			}
			final List<Predicate> predicates = new ArrayList<>();
			final Expression<String> initialDate = cb.function(TO_CHAR, String.class, getPathWithJoin(initialDateField, root),
					cb.literal(DATE_FORMAT));

			if (from != null) {
				final String formattedFromDate = this.getFormattedDate(from, DATE_FORMAT);
				predicates.add(cb.greaterThanOrEqualTo(initialDate, formattedFromDate));
			}

			if (to != null) {
				final String formattedToDate = this.getFormattedDate(to, DATE_FORMAT);
				predicates.add(cb.lessThanOrEqualTo(initialDate, formattedToDate));
			}

			return predicates.stream().reduce((x, y) -> cb.and(x)).get();
		};
	}
}
