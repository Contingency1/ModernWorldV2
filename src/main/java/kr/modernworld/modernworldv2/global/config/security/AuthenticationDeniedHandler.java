package kr.modernworld.modernworldv2.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.modernworld.modernworldv2.global.error.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthenticationDeniedHandler implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    
    ErrorResponseDTO error = new ErrorResponseDTO(
        "Authentication Failed. Please send with JWT Token.",
        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
        HttpStatus.UNAUTHORIZED.value());

    response.getWriter().write(objectMapper.writeValueAsString(error));
  }
}
