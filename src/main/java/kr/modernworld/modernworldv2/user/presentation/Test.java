package kr.modernworld.modernworldv2.user.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

  @GetMapping("/test")
  public String test() {
    return "user";
  }

  @GetMapping("/api/admin")
  public String admin() {
    return "admin";
  }
}
