package kr.modernworld.modernworldv2.asset.application.present;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.inventory.InventoryService;
import kr.modernworld.modernworldv2.asset.application.inventory.port.InventoryQueryRepository;
import kr.modernworld.modernworldv2.asset.application.item.ItemService;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.asset.application.present.dto.GetPresentDTO;
import kr.modernworld.modernworldv2.asset.application.present.port.PresentQueryRepository;
import kr.modernworld.modernworldv2.asset.domain.external.member.MemberExternalPort;
import kr.modernworld.modernworldv2.asset.domain.present.Present;
import kr.modernworld.modernworldv2.asset.domain.present.event.PresentCreatedEvent;
import kr.modernworld.modernworldv2.asset.domain.present.event.PresentItemRefundedEvent;
import kr.modernworld.modernworldv2.asset.domain.present.port.PresentRepository;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PresentService {

  private final PresentQueryRepository presentQueryRepository;
  private final PresentRepository presentRepository;
  private final InventoryQueryRepository inventoryQueryRepository;
  private final ItemService itemService;
  private final InventoryService inventoryService;
  private final ApplicationEventPublisher eventPublisher;
  private final MemberExternalPort memberExternalPort;

  @Transactional
  public GetPresentDTO getOnePresent(Long userNo, Long presentNo) {
    Present present = presentRepository.findByNoForUpdate(userNo, presentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRESENT_NOT_FOUND));

    boolean isStatusChanged;

    try {
      isStatusChanged = present.read(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.PRESENT_ACCESS_DENIED,
          " reason: " + e.getMessage());
    }

    if (isStatusChanged) {
      presentRepository.save(present);
    }

    return presentQueryRepository.getOnePresent(presentNo);
  }

  @Transactional(readOnly = true)
  public List<GetPresentDTO> getUserPresents(Long userNo, SenderReceiverNoField type) {

    return presentQueryRepository.getPresents(userNo, type);
  }

  @Transactional
  public Present createOnePresent(Long senderNo, Long receiverNo, Long itemNo) {
    memberExternalPort.validateUser(receiverNo);

    Present present;

    try {
      present = Present.init(itemNo, senderNo, receiverNo);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.PRESENT_CANNOT_PRESENT_TO_YOURSELF,
          " reason: " + e.getMessage());
    }

    ItemNameAndPriceDTO itemNameAndPrice = itemService.getNameAndPrice(itemNo);
    Long itemPrice = itemNameAndPrice.price();
    String itemName = itemNameAndPrice.name();

    eventPublisher.publishEvent(new PresentCreatedEvent(senderNo, receiverNo, itemName));

    memberExternalPort.decreaseCurrentPoint(senderNo, itemPrice);
    return presentRepository.save(present);
  }

  @Transactional
  public void deleteOnePresent(Long userNo, Long presentNo) {
    Present present = presentRepository.findByNoForUpdate(userNo, presentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRESENT_NOT_FOUND));

    try {
      present.delete(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.PRESENT_INVALID_STATE,
          " reason: " + e.getMessage());
    }

    presentRepository.save(present);
  }

  @Transactional
  public Present acceptOrReject(Long userNo, Long presentNo, HandlePresentStatus status) {
    Present present = presentRepository.findByNoForUpdate(userNo, presentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRESENT_NOT_FOUND,
            " presentNo: " + presentNo));

    if (status.equals(HandlePresentStatus.REJECT)) {
      try {
        present.reject(userNo);
      } catch (IllegalStateException e) {
        throw new BusinessException(BusinessErrorCode.PRESENT_INVALID_STATE,
            " reason: " + e.getMessage());
      }

      return presentRepository.save(present);
    }

    try {
      present.accept(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.PRESENT_INVALID_STATE,
          " reason: " + e.getMessage());
    }

    Boolean itemExists = inventoryQueryRepository.exists(userNo, present.getItemNo());

    if (itemExists) {
      ItemNameAndPriceDTO nameAndPrice = itemService.getNameAndPrice(present.getItemNo());
      Long itemPrice = nameAndPrice.price();
      String itemName = nameAndPrice.name();

      Long refundedPrice = itemPrice / 2;

      eventPublisher.publishEvent(
          new PresentItemRefundedEvent(userNo, itemName, refundedPrice));

      memberExternalPort.increaseCurrentAccumulationPoint(userNo, refundedPrice);

      return presentRepository.save(present);
    }

    inventoryService.addOneItemInInventory(userNo, present.getItemNo());

    return presentRepository.save(present);
  }
}
