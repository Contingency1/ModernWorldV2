package kr.modernworld.modernworldv2.user.infrastructure.repository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
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

  private Key accessKey;

  private Key refreshKey;

  @PostConstruct
  public void init() {
    this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.accessSecret()));
    this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.refreshSecret()));
  }

  @Override
  public TokenResultDTO createAccess(Long userNo, boolean isAdmin, Long now) {
    return createToken(userNo, isAdmin, now, properties.accessExpiration(), accessKey);
  }

  @Override
  public TokenResultDTO createRefresh(Long userNo, boolean isAdmin, Long now) {
    return createToken(userNo, isAdmin, now, properties.refreshExpiration(), refreshKey);
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
