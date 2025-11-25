package kr.modernworld.modernworldv2.user.application;

import java.util.List;
import kr.modernworld.modernworldv2.admin.application.api.ItemApi;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.port.inventory.InventoryQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.present.PresentQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.present.PresentRepository;
import kr.modernworld.modernworldv2.user.domain.port.user.UserQueryRepository;
import kr.modernworld.modernworldv2.user.domain.present.Present;
import kr.modernworld.modernworldv2.user.presentation.present.HandlePresentStatus;
import kr.modernworld.modernworldv2.user.presentation.present.dto.req.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.present.dto.res.GetPresentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PresentService {

  private final PresentQueryRepository presentQueryRepository;
  private final PresentRepository presentRepository;
  private final InventoryQueryRepository inventoryQueryRepository;
  private final ItemApi itemApi;
  private final UserQueryRepository userQueryRepository;
  private final InventoryService inventoryService;
  private final UserPointService userPointService;

  @Transactional
  public GetPresentResponseDTO getOnePresent(Long userNo, Long presentNo) {
    Present present = presentRepository.findByNoForUpdate(presentNo)
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
    if (!userQueryRepository.exists(receiverNo)) {
      throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND, " receiverNo: " + receiverNo);
    }

    Present present;

    try {
      present = Present.create(itemNo, senderNo, receiverNo);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.PRESENT_INVALID_STATE,
          " reason: " + e.getMessage());
    }

    Long itemPrice = itemApi.getPrice(itemNo);

    //======================= 할 것.======================

    // legend, userAchievement 갱신
    // 알람 table, sse 발행

    //======================= 할 것.======================

    userPointService.decreaseCurrentPoint(senderNo, itemPrice);
    return presentRepository.save(present);
  }

  @Transactional
  public void deleteOnePresent(Long userNo, Long presentNo) {
    Present present = presentRepository.findByNoForUpdate(presentNo)
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
    Present present = presentRepository.findByNoForUpdate(presentNo)
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
      Long itemHalfPrice = itemApi.getPrice(present.getItemNo()) / 2;

      //======================= 이벤트 처리 할 것.======================

      // 알람 table, sse 발행

      //======================= 이벤트 처리 할 것.======================

      userPointService.increaseCurrentAccumulationPoint(userNo, itemHalfPrice);

      return presentRepository.save(present);
    }

    //======================= 앞으로 할 것.======================

    // legend 기록, userAchievement 기록

    //======================= 앞으로 할 것.======================

    inventoryService.addOneItemInInventory(userNo, present.getItemNo());

    return presentRepository.save(present);
  }
}
