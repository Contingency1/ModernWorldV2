package kr.modernworld.modernworldv2.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import kr.modernworld.modernworldv2.global.config.security.UserRole;
import kr.modernworld.modernworldv2.user.infrastructure.auth.jwt.JwtTokenProvider;
import kr.modernworld.modernworldv2.user.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = getAccessToken(request);

    if (token != null) {
      TokenUserInfoDTO userInfo = jwtTokenProvider.validateAccess(token);

      List<GrantedAuthority> authorities = List.of(
          new SimpleGrantedAuthority(
              userInfo.isAdmin() ? UserRole.ROLE_ADMIN.name() : UserRole.ROLE_USER.name())
      );

      Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, null,
          authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private static String getAccessToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
      return null;
    }

    return bearerToken.split(" ")[1];
  }
}
