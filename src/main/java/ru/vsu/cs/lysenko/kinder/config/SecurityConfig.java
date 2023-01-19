package ru.vsu.cs.lysenko.kinder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new UsernamePasswordAuthenticationFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().deleteCookies(CookieAuthenticationFilter.COOKIE_NAME)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-in", "/sign-up").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type",
                "content-type", "Access-Control-Request-Method", "Origin",
                "X-Requested-With", "X-HTTP-Method-Override", "Accept", "Accept-Encoding"));
        configuration.setExposedHeaders(List.of("X-Get-Header"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
