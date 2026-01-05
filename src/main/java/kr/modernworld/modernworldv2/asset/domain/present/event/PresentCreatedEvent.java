package kr.modernworld.modernworldv2.asset.domain.present.event;

public record PresentCreatedEvent(
    Long receiverNo,
    String senderName,
    String itemName
) {

}
