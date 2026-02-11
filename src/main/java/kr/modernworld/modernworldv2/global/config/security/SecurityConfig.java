package kr.modernworld.modernworldv2.global.config.security;

import jakarta.servlet.DispatcherType;
import java.util.List;
import kr.modernworld.modernworldv2.global.config.CorsConfigProperty;
import kr.modernworld.modernworldv2.global.filter.ExceptionHandlingFilter;
import kr.modernworld.modernworldv2.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ExceptionHandlingFilter exceptionHandlingFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AccessDeniedHandler accessDeniedHandler;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final CorsConfigProperty corsConfigProperty;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .addFilterBefore(exceptionHandlingFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter, ExceptionHandlingFilter.class)

        .exceptionHandling(handler ->
            handler
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
        )

        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/error").permitAll() // 아무런 에러 핸들링이 안됐을 경우 최후의 보루
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/api/admin/**").hasAuthority(UserRole.ROLE_ADMIN.name())
            .dispatcherTypeMatchers(DispatcherType.ASYNC, DispatcherType.ERROR).permitAll()
            .anyRequest()
            .authenticated()
        );

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(corsConfigProperty.allowedOrigins());

    config.setAllowCredentials(true);
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
