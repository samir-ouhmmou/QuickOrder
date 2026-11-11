package QuickOrder.Restauration.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final JwtAuthentificationFilter jwtAuthentificationFilter;
    private final ObjectProvider<GoogleLoginSuccessHandler> googleLoginSuccessHandler;

    public SecurityConfig(
            JwtAuthentificationFilter jwtAuthentificationFilter,
            ObjectProvider<GoogleLoginSuccessHandler> googleLoginSuccessHandler
    ) {
        this.jwtAuthentificationFilter = jwtAuthentificationFilter;
        this.googleLoginSuccessHandler = googleLoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource,
            @Value("${google.oauth.enabled:false}") boolean googleOauthEnabled,
            @Value("${frontend.base-url:http://localhost:4200}") String frontendBaseUrl
    ) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/auth/google/enabled").permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/category", "/api/category/**",
                                        "/api/plats", "/api/plats/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form->form.disable())
                .httpBasic(basic->basic.disable())
                .addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

        if (googleOauthEnabled) {
            http.oauth2Login(oauth -> oauth
                    .successHandler(googleLoginSuccessHandler.getObject())
                    .failureHandler((request, response, exception) ->
                            response.sendRedirect(frontendBaseUrl + "/login?googleError=failed"))
            );
        }

        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Autoriser tous les ports sur localhost et 127.0.0.1
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "http://127.0.0.1:*"
        ));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Autoriser tous les headers
        configuration.setAllowedHeaders(List.of("*"));

        // Autoriser l'en-tête Authorization
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
