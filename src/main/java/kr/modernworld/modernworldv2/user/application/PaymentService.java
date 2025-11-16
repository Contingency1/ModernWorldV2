package kr.modernworld.modernworldv2.user.application;

import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.port.user.UserQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.user.UserRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final UserQueryRepository userQueryRepository;
  private final UserRepository userRepository;

  @Transactional
  public void pay(Long userNo, Long price) {
    User user = userQueryRepository.findUserCurrentPointByNo(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

    try {
      user.decreaseCurrentPoint(price);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.USER_NOT_HAS_ENOUGH_POINT,
          " " + e.getMessage());
    }

    userRepository.updateCurrentPoint(user.getNo(), user.getCurrentPoint());
  }

}
