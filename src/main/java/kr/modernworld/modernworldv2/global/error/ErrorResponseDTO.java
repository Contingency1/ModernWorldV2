package kr.modernworld.modernworldv2.global.error;

public record ErrorResponseDTO(
    String message,
    String error,
    Integer statusCode
) {

}
