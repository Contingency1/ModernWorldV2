package kr.modernworld.modernworldv2.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.modernworld.modernworldv2.global.error.ErrorResponseDTO;
import kr.modernworld.modernworldv2.user.infrastructure.auth.jwt.JwtValidationCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlingFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (JwtValidationCustomException e) {
      log.warn("JWT validation failed: {}", e.getMessage(), e);
      makeResponse(response, e);
    }

  }

  private void makeResponse(HttpServletResponse response, JwtValidationCustomException e)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
        e.getMessage(),
        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
        HttpStatus.UNAUTHORIZED.value()
    );

    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
