package br.com.gilberto.sgv.infra.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import br.com.gilberto.sgv.domain.user.User;
import br.com.gilberto.sgv.infra.serializers.UserSerializer;
import br.com.gilberto.sgv.infra.util.SerializerFunctions;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JsonResponseWrapperSerializerConfig {

	private final UserSerializer userSerializer;
	
    @SuppressWarnings("rawtypes")
    @Bean
    public Map<Class, SerializerFunctions> commonWrapperSerializerMap() {
        final Builder<Class, SerializerFunctions> builder = ImmutableMap.builder();
        return builder
        		.put(User.class, new SerializerFunctions<>(userSerializer::completeSerialize, userSerializer::simpleSerialize))
                .build();
    }
}
