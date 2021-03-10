package br.com.gilberto.sgv.infra.deserializers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.gilberto.sgv.infra.exception.JsonPreConditionFailedException;
import br.com.gilberto.sgv.infra.exception.NotFoundException;
import br.com.gilberto.sgv.infra.util.IOExceptionFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDeserializer<T> extends JsonDeserializer<T> {

	public abstract T deserializeNode(final JsonNode jsonNode, final DeserializationContext deserializationContext) throws IOException, NotFoundException;

	@Override
	public abstract T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException;

	protected Long getId(final JsonNode node) {
		if (node.hasNonNull("id")) {
			return node.get("id").asLong() != 0 ? node.get("id").asLong() : null;
		}
		return null;
	}

	protected Optional<JsonNode> getNodeAsOptional(final JsonNode node) {
		return isNodeNotNull(node) ? Optional.of(node) : Optional.empty();
	}

	protected String getAsString(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).textValue();
		}
		return null;
	}
	
	protected Integer getAsInteger(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).asInt();
		}
		return null;
	}

	protected JsonNode getAsJsonNode(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field);
		}
		return null;
	}

	protected Boolean getAsBoolean(final String field, final JsonNode node) {
		final JsonNode booleanNode = node.get(field);
		return isNodeNotNull(booleanNode) ? booleanNode.asBoolean() : null;
	}

	protected boolean getAsBooleanPrimitive(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).asBoolean();
		}

		return false;
	}

	protected Long getAsLong(final String field, final JsonNode node) {
		if (node.has(field)) {
			return node.get(field).asLong() != 0 ? node.get(field).asLong() : null;
		}
		return null;
	}

	protected Long getAsLong(final JsonNode node) {
		return node.asLong() != 0 ? node.asLong() : null;
	}


	protected BigDecimal getAsBigDecimalZeroIfNull(final String field, final JsonNode node) {
		if (node.hasNonNull(field)) {
			return BigDecimal.valueOf(node.get(field).asDouble());
		}

		return BigDecimal.ZERO;
	}

	protected JsonNode getJsonNode(final JsonParser jsonParser) throws IOException {
		final ObjectCodec oc = jsonParser.getCodec();
		return oc.readTree(jsonParser);
	}

	protected boolean isNodeNotNull(final JsonNode node) {
		return node != null && !node.isNull();
	}

	protected void applyDeserialize(final IOExceptionFunction function) {
		try {
			function.apply();
		} catch (final IOException e) {
			log.warn("Error with deserialization", e);
			throw new JsonPreConditionFailedException("Deserialization Exception: " + e.getMessage());
		}
	}
}
