package kr.modernworld.modernworldv2.social.domain.neighbor.event;

public record NeighborConnectedEvent(
    Long senderNo,
    Long receiverNo,
    String senderName,
    String receiverName
) {

}
