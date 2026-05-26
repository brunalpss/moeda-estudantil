package com.moedaestudantil.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
// CODE REVIEW: A desativação global do CSRF facilita testes com API REST, mas deveria ser justificada ou condicionada ao tipo de autenticação usado. Em produção, caso a aplicação utilize sessões ou formLogin, manter CSRF desativado pode abrir vulnerabilidades. Sugere-se revisar essa configuração e documentar quando ela é segura.
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
// CODE REVIEW: A liberação do H2 Console é útil em desenvolvimento, mas deveria estar restrita ao perfil dev. Em ambiente de produção, expor `/h2-console/**` pode permitir acesso indevido ao banco de dados. Sugere-se usar `@Profile("dev")` ou uma configuração separada para desenvolvimento.
                        .requestMatchers("/h2-console/**").permitAll()
// CODE REVIEW: A regra `.anyRequest().permitAll()` deixa todos os endpoints públicos, incluindo cadastro, login, transferência de moedas e resgate de recompensas. Para melhorar a segurança e a arquitetura, recomenda-se exigir autenticação e aplicar autorização por perfil: aluno, professor e empresa parceira.
                        .anyRequest().permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
// CODE REVIEW: A configuração usa `formLogin`, mas o frontend realiza login via chamadas REST com Axios. Seria interessante padronizar a estratégia de autenticação, por exemplo usando JWT para APIs REST, evitando mistura entre autenticação baseada em formulário e autenticação via endpoints JSON.
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
