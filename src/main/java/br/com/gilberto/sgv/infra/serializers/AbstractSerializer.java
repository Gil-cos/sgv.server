package br.com.gilberto.sgv.infra.serializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

import br.com.gilberto.sgv.infra.exception.JsonPreConditionFailedException;
import br.com.gilberto.sgv.infra.util.IOExceptionFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractSerializer<T> extends JsonSerializer<T> {

	protected void paging(final JsonGenerator gen, final Page<?> value) throws IOException {
		gen.writeFieldName("pageable");
		gen.writeStartObject();
		gen.writeFieldName("sort");
		gen.writeStartObject();
		gen.writeBooleanField("sorted", value.getPageable().getSort().isSorted());
		gen.writeBooleanField("unsorted", value.getPageable().getSort().isUnsorted());
		gen.writeEndObject();
		gen.writeNumberField("pageNumber", value.getPageable().getPageNumber());
		gen.writeNumberField("pageSize", value.getPageable().getPageSize());
		gen.writeNumberField("offset", value.getPageable().getOffset());
		gen.writeBooleanField("paged", value.getPageable().isPaged());
		gen.writeBooleanField("unpaged", value.getPageable().isUnpaged());
		gen.writeEndObject();
		gen.writeBooleanField("last", value.isLast());
		gen.writeNumberField("totalElements", value.getTotalElements());
		gen.writeNumberField("totalPages", value.getTotalPages());
		gen.writeBooleanField("first", value.isFirst());
		gen.writeFieldName("sort");
		gen.writeStartObject();
		gen.writeBooleanField("sorted", value.getSort().isSorted());
		gen.writeBooleanField("unsorted", value.getSort().isUnsorted());
		gen.writeEndObject();
		gen.writeNumberField("numberOfElements", value.getNumberOfElements());
		gen.writeNumberField("size", value.getSize());
		gen.writeNumberField("number", value.getNumber());
	}

	protected void writeDateField(final String fieldName, final LocalDateTime localDateTime, final JsonGenerator gen) throws IOException {
		gen.writeFieldName(fieldName);
		if (localDateTime != null) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			final ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
			gen.writeString(zdt.format(formatter));
		}else {
			gen.writeNull();
		}
	}

	protected void writeDateField(final String fieldName, final LocalDate localDate, final JsonGenerator gen) throws IOException {
		gen.writeFieldName(fieldName);
		if (localDate != null) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			gen.writeString(localDate.format(formatter));
		}else {
			gen.writeNull();
		}
	}
	
	protected void writeTimeField(final String fieldName, final LocalTime localTime, final JsonGenerator gen) throws IOException {
		gen.writeFieldName(fieldName);
		if (localTime != null) {
			gen.writeString(localTime.toString());
		}else {
			gen.writeNull();
		}
	}

	protected void applySerialize(final IOExceptionFunction function){
		try {
			function.apply();
		} catch (final IOException e) {
			log.warn("Error with serialization", e);
			throw new JsonPreConditionFailedException("Serialization Exception: " + e.getMessage());
		}
	}

}
