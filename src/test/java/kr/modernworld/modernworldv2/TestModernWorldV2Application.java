package kr.modernworld.modernworldv2;

import org.springframework.boot.SpringApplication;

public class TestModernWorldV2Application {

  public static void main(String[] args) {
    SpringApplication.from(ModernWorldV2Application::main).with(TestcontainersConfiguration.class)
        .run(args);
  }

}
