package kr.modernworld.modernworldv2.user.infrastructure.repository.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import kr.modernworld.modernworldv2.user.domain.port.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JwtTokenProvider implements TokenProvider {

  private final JwtProperties properties;

  @Autowired
  public JwtTokenProvider(JwtProperties properties) {
    this.properties = properties;
  }

  private SecretKey accessKey;
  private SecretKey refreshKey;

  private JwtParser accessParser;
  private JwtParser refreshParser;

  @PostConstruct
  public void init() {
    this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.accessSecret()));
    this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.refreshSecret()));

    this.accessParser = Jwts.parser().verifyWith(accessKey).build();
    this.refreshParser = Jwts.parser().verifyWith(refreshKey).build();
  }

  @Override
  public TokenResultDTO createAccess(Long userNo, boolean isAdmin, Long now) {
    return createToken(userNo, isAdmin, now, properties.accessExpiration(), accessKey);
  }

  @Override
  public TokenResultDTO createRefresh(Long userNo, boolean isAdmin, Long now) {
    return createToken(userNo, isAdmin, now, properties.refreshExpiration(), refreshKey);
  }

  @Override
  public TokenUserInfoDTO validateAccess(String token) {
    try {
      Claims payload = accessParser.parseSignedClaims(token).getPayload();

      Long userNo = Long.valueOf(payload.getSubject());
      boolean isAdmin = payload.get("isAdmin", Boolean.class) == Boolean.TRUE;

      return new TokenUserInfoDTO(userNo, isAdmin);
    } catch (ExpiredJwtException e) {
      throw new JwtValidationCustomException("AccessToken Expired", e);
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtValidationCustomException("AccessToken Validation Failed.", e);
    }
  }

  @Override
  public TokenUserInfoDTO validateRefresh(String token) {
    try {
      Claims payload = refreshParser.parseSignedClaims(token).getPayload();

      Long userNo = Long.valueOf(payload.getSubject());
      boolean isAdmin = payload.get("isAdmin", Boolean.class) == Boolean.TRUE;
      return new TokenUserInfoDTO(userNo, isAdmin);
    } catch (ExpiredJwtException e) {
      throw new JwtValidationCustomException("RefreshToken Expired.", e);
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtValidationCustomException("RefreshToken Validation Failed.", e);
    }

  }

  private TokenResultDTO createToken(Long userNo, boolean isAdmin, Long now, long expirationMillis,
      Key key) {
    final long expiredAt = now + expirationMillis;

    String token = Jwts.builder()
        .issuer(properties.issuer())
        .subject(String.valueOf(userNo))
        .issuedAt(new Date(now))
        .expiration(new Date(expiredAt))
        .claim("isAdmin", isAdmin)
        .signWith(key)
        .compact();

    return new TokenResultDTO(token, expiredAt);
  }
}
