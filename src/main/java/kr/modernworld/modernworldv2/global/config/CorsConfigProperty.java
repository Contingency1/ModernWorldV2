package kr.modernworld.modernworldv2.global.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cors")
public record CorsConfigProperty(List<String> allowedOrigins) {

}
