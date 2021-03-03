package br.com.gilberto.sgv.infra.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import br.com.gilberto.sgv.infra.util.SerializerFunctions;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JsonResponseWrapperSerializerConfig {


    @SuppressWarnings("rawtypes")
    @Bean
    public Map<Class, SerializerFunctions> commonWrapperSerializerMap() {
        final Builder<Class, SerializerFunctions> builder = ImmutableMap.builder();
        return builder
                .build();
    }
}
