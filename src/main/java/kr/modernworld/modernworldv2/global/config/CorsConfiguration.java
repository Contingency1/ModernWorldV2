package kr.modernworld.modernworldv2.global.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfiguration {

  @Bean
  public CorsFilter corsFilter() {

    return new CorsFilter();
  }
}
