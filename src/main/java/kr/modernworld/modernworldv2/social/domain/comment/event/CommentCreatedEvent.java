package kr.modernworld.modernworldv2.social.domain.comment.event;

public record CommentCreatedEvent(
    Long senderNo,
    Long receiverNo,
    String senderName

) {

}
