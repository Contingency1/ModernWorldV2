package kr.modernworld.modernworldv2.asset.domain.present;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Present {

  private final Long no;

  private final Long itemNo;

  private final Long senderNo;

  private final Long receiverNo;

  private final Instant createdAt;

  private PresentStatus status;

  private Boolean senderDelete;

  private Boolean receiverDelete;

  @Builder
  private Present(Long no, Long itemNo, Long senderNo, Long receiverNo, Instant createdAt,
      PresentStatus status, Boolean senderDelete, Boolean receiverDelete) {
    this.no = no;
    this.itemNo = itemNo;
    this.senderNo = senderNo;
    this.receiverNo = receiverNo;
    this.createdAt = createdAt;
    this.status = status;
    this.senderDelete = senderDelete;
    this.receiverDelete = receiverDelete;
  }

  public static Present init(Long itemNo, Long senderNo, Long receiverNo) {
    if (senderNo.equals(receiverNo)) {
      throw new IllegalArgumentException("User can't send port to port self");
    }

    return Present.builder()
        .itemNo(itemNo)
        .senderNo(senderNo)
        .receiverNo(receiverNo)
        .createdAt(Instant.now())
        .status(PresentStatus.UNREAD)
        .senderDelete(false)
        .receiverDelete(false)
        .build();
  }

  public void accept(Long userNo) {
    validateReceiverPresent(userNo);
    validateStatusRead();

    this.status = PresentStatus.ACCEPT;
  }

  public void reject(Long userNo) {
    validateReceiverPresent(userNo);
    validateStatusRead();

    this.status = PresentStatus.REJECT;
  }

  public Boolean read(Long userNo) {
    if (userNo.equals(this.receiverNo)) {
      if (status.equals(PresentStatus.UNREAD)) {
        this.status = PresentStatus.READ;
        return true;
      }
      return false;
    }

    if (userNo.equals(this.senderNo)) {
      return false;
    }

    throw new IllegalStateException("Present is not related with port.");
  }

  public void delete(Long userNo) {
    if (userNo.equals(senderNo)) {
      validateNotDeleted(senderDelete, "sender");
      this.senderDelete = true;
      return;
    }

    if (userNo.equals(receiverNo)) {
      validateNotDeleted(receiverDelete, "receiver");
      this.receiverDelete = true;
      return;
    }

    throw new IllegalStateException("Present is not related with port.");
  }

  private void validateNotDeleted(Boolean isDeleted, String role) {
    if (isDeleted) {
      throw new IllegalStateException("Already deleted by " + role);
    }
  }

  private void validateStatusRead() {
    if (this.status != PresentStatus.READ) {
      throw new IllegalStateException("Status must be READ");
    }
  }

  private void validateReceiverPresent(Long inputUserNo) {
    if (!inputUserNo.equals(receiverNo)) {
      throw new IllegalStateException("User is not a receiver.");
    }

  }
}