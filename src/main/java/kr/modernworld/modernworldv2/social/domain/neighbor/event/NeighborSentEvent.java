package kr.modernworld.modernworldv2.social.domain.neighbor.event;

public record NeighborSentEvent(
    Long receiverNo,
    String senderName
) {

}
