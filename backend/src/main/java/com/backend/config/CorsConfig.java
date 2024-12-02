package com.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir origens específicas (exemplo: localhost:8081)
        config.addAllowedOrigin("http://localhost:8081");

        // Permitir cabeçalhos específicos
        config.addAllowedHeader("*");

        // Permitir métodos HTTP específicos
        config.addAllowedMethod("*");

        // Permitir credenciais, se necessário
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
