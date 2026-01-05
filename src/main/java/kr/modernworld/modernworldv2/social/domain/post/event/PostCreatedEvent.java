package kr.modernworld.modernworldv2.social.domain.post.event;

public record PostCreatedEvent(
    Long receiverNo,
    Long senderNo
) {

}
