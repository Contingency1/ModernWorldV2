package kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto;

import java.util.Optional;

public record GoogleErrorDetailsDTO(
    Optional<Integer> code,
    Optional<String> message,
    Optional<String> status
) {

}
