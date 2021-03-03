package br.com.gilberto.sgv.infra.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.gilberto.sgv.infra.util.ApplicationContextUtil;

public class JsonToDomainHttpMessageResolver extends AbstractMessageConverterMethodArgumentResolver {

	public JsonToDomainHttpMessageResolver() {
		super(Collections.singletonList(new MappingJackson2HttpMessageConverter(new ObjectMapper())));
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(JsonDeserialize.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
			throws Exception {
		return readWithMessageConverters(webRequest, parameter, parameter.getNestedGenericParameterType());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object readWithMessageConverters(final HttpInputMessage inputMessage, final MethodParameter parameter, final Type targetType)
			throws IOException {
		final JsonDeserialize jsonDeserialize = parameter.getParameterAnnotation(JsonDeserialize.class);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		JsonDeserializer deserializer = ApplicationContextUtil.getApplicationContext().getBean(
				jsonDeserialize.using());
		module.addDeserializer(parameter.getParameterType(),
				deserializer);
		mapper.registerModule(module);
		return mapper.readValue(inputMessage.getBody(),
				parameter.getParameterType());
	}
}