package fr.utt.animehub.lo10projetanimehub.config;

import fr.utt.animehub.lo10projetanimehub.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/swagger-ui-recipes.html", "/swagger-ui/**", "/api-docs", "/api-docs.yaml").permitAll()
                        .requestMatchers(HttpMethod.GET, "/recipes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/recipes/**").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT, "/recipes/**").hasAuthority("USER")
                        .requestMatchers(HttpMethod.DELETE, "/recipes/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.defaultSuccessUrl("http://25.18.117.74:3000/", true));

        return http.build();
    }
}
