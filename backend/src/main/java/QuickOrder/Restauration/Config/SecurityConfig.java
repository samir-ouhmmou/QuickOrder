package QuickOrder.Restauration.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/category",
                                        "/api/category/**"
                                ).permitAll()

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/plats",
                                        "/api/plats/**"
                                ).permitAll()

                                .anyRequest().authenticated()

                        )
                .formLogin(form->form.disable())
                .httpBasic(basic->basic.disable());

        return http.build();

    }

}
