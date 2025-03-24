package br.com.matraca.projetotcc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configurações globais de CORS
        registry.addMapping("/**") // Permite CORS em todos os endpoints
                .allowedOrigins("*") // Permite qualquer origem (mude conforme necessário)
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Permite métodos específicos
                // .allowCredentials(true) // Permite credenciais (como cookies ou headers de autenticação)
                .maxAge(3600); // Define o tempo de vida do cache do CORS (em segundos)
    }
}


