package br.com.gilberto.sgv.infra.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
public class ErrorMessage {

	private final String message;
    private final Map<String, String> params;
    
    public ErrorMessage(final String message) {
        this.message = message;
        this.params = new HashMap<>();
    }
    
    public ErrorMessage(final String message, final String param) {
        this.message = message;
        this.params = new HashMap<>();
        this.params.put("error", param);
    }
    
    public ErrorMessage(final String message, final Map<String, String> params) {
        this.message = message;
        this.params = params;
    }
    
    public ErrorMessage addParam(final String key, final String message) {
    	params.put(key, message);
    	return this;
    }
    
    public ErrorMessage addParam(final String key, final Object message) {
    	params.put(key, toJson(message));
    	return this;
    }
    
    public String toJson() {
    	return toJson(this);
    }
    
    public String toJson(final Object object) {
        try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Error to map object to json: {}", e);
		}
        return null;
    }
}


