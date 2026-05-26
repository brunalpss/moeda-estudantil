package com.moedaestudantil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
// CODE REVIEW: A origem permitida do CORS está fixa em `http://localhost:5173`, o que funciona apenas em ambiente local. Para melhorar a portabilidade da aplicação, recomenda-se externalizar essa URL para uma variável de ambiente, como `FRONTEND_URL`, permitindo configurar origens diferentes para desenvolvimento, homologação e produção sem alterar o código-fonte.
                        .allowedOrigins("http://localhost:5173")
// CODE REVIEW: Os métodos permitidos estão definidos manualmente, mas não incluem `OPTIONS`, que costuma ser usado pelo navegador em requisições preflight de CORS. Sugere-se incluir `OPTIONS` e revisar se todos os métodos expostos são realmente necessários para reduzir a superfície de acesso da API.
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
