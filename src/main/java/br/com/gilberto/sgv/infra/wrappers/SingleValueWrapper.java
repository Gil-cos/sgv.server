package br.com.gilberto.sgv.infra.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class SingleValueWrapper {
	private final Object value;

	@SuppressWarnings("unused")
	private SingleValueWrapper() {
		value = null;
	}
	
	@JsonIgnore
	public String getValueAsString() {
		return Objects.requireNonNull(value).toString();
	}

	@JsonIgnore
	public BigDecimal getValueAsBigDecimal() {
		return new BigDecimal(Objects.requireNonNull(value).toString());
	}
	
	@JsonIgnore
	public Long getValueAsLong() {
		return new Long(Objects.requireNonNull(value).toString());
	}
}
