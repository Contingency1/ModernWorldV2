package kr.modernworld.modernworldv2.global.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SenderReceiverNoField {
  SENDER_NO("senderNo"), RECEIVER_NO("receiverNo");

  final String str;

  SenderReceiverNoField(String str) {
    this.str = str;
  }

  @JsonCreator
  public static SenderReceiverNoField stringToSenderReceiverNoField(String str) {
    for (SenderReceiverNoField role : SenderReceiverNoField.values()) {
      if (role.str.equals(str)) {
        return role;
      }
    }

    throw new IllegalArgumentException("Invalid SenderReceiverNoField: " + str);
  }
}
