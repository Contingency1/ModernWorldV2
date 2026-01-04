package kr.modernworld.modernworldv2.asset.domain.present.event;

public record PresentItemRefundedEvent(
    Long userNo,
    String itemName,
    Long refundedPoint
) {

}
