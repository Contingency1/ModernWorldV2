package kr.modernworld.modernworldv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class ModernWorldV2Application {

  public static void main(String[] args) {
    SpringApplication.run(ModernWorldV2Application.class, args);
  }

}
