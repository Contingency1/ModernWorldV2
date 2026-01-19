package kr.modernworld.modernworldv2.member.application.auth.event;

public record LoginSuccessEvent(String sessionKey, Long userNo) {

}
