package kr.modernworld.modernworldv2.user.application;

import java.util.List;
import kr.modernworld.modernworldv2.admin.application.api.ItemApi;
import kr.modernworld.modernworldv2.admin.application.api.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.application.event.AlarmEvent;
import kr.modernworld.modernworldv2.user.application.event.UpdateLegendCheckAchievementEvent;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.user.domain.port.inventory.InventoryQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.present.PresentQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.present.PresentRepository;
import kr.modernworld.modernworldv2.user.domain.present.Present;
import kr.modernworld.modernworldv2.user.presentation.present.HandlePresentStatus;
import kr.modernworld.modernworldv2.user.presentation.present.dto.res.GetPresentResponseDTO;
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
  private final ItemApi itemApi;
  private final InventoryService inventoryService;
  private final UserService userService;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public GetPresentResponseDTO getOnePresent(Long userNo, Long presentNo) {
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
  public List<GetPresentResponseDTO> getUserPresents(Long userNo, SenderReceiverNoField type) {

    return presentQueryRepository.getPresents(userNo, type);
  }

  @Transactional
  public Present createOnePresent(Long senderNo, Long receiverNo, Long itemNo) {
    userService.isPresent(receiverNo);

    Present present;

    try {
      present = Present.init(itemNo, senderNo, receiverNo);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.PRESENT_CANNOT_PRESENT_TO_YOURSELF,
          " reason: " + e.getMessage());
    }

    ItemNameAndPriceDTO itemNameAndPrice = itemApi.getNameAndPrice(itemNo);
    Long itemPrice = itemNameAndPrice.price();
    String itemName = itemNameAndPrice.name();

    applicationEventPublisher.publishEvent(
        new UpdateLegendCheckAchievementEvent(this, senderNo, LegendField.PRESENT_COUNT));

    // ============================== 추후에 익명 바꿀것. =============================
    String eventMessage = String.format("%s님이 %s을(를) 선물로 보냈습니다.", "익명",
        itemName);
    applicationEventPublisher.publishEvent(
        new AlarmEvent(this, receiverNo, eventMessage, AlarmTitle.PRESENT));

    userService.decreaseCurrentPoint(senderNo, itemPrice);
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
      ItemNameAndPriceDTO nameAndPrice = itemApi.getNameAndPrice(present.getItemNo());
      Long itemPrice = nameAndPrice.price();
      String itemName = nameAndPrice.name();

      Long itemHalfPrice = itemPrice / 2;

      String evenetMessage = String.format(
          "%s은(는) 이미 보유중인 아이템 입니다. 아이템 가격의 50%%, [%s]포인트로 반환되었습니다.",
          itemName, itemHalfPrice);
      applicationEventPublisher.publishEvent(
          new AlarmEvent(this, userNo, evenetMessage, AlarmTitle.PRESENT));

      userService.increaseCurrentAccumulationPoint(userNo, itemHalfPrice);

      return presentRepository.save(present);
    }

    inventoryService.addOneItemInInventory(userNo, present.getItemNo());

    return presentRepository.save(present);
  }
}
