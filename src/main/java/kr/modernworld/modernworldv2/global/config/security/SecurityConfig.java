package kr.modernworld.modernworldv2.global.config.security;

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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ExceptionHandlingFilter exceptionHandlingFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AccessDeniedHandler accessDeniedHandler;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
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
            .anyRequest()
            .authenticated()
        );

    return http.build();
  }
}
