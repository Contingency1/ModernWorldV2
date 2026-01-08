package kr.modernworld.modernworldv2.member.application.user;

import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.legend.LegendService;
import kr.modernworld.modernworldv2.member.application.auth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.member.application.auth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserAttendanceDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.application.user.event.UserAttendanceUpdatedEvent;
import kr.modernworld.modernworldv2.member.application.user.socialtoken.SocialTokenService;
import kr.modernworld.modernworldv2.member.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.member.domain.user.User;
import kr.modernworld.modernworldv2.member.domain.user.UserSocialToken;
import kr.modernworld.modernworldv2.member.domain.user.port.UserQueryRepository;
import kr.modernworld.modernworldv2.member.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final LegendService legendService;
  private final SocialTokenService socialTokenService;

  private final ApplicationEventPublisher eventPublisher;

  @Transactional(readOnly = true)
  public UserDTO getOne(Long userNo) {
    return userQueryRepository.findOne(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public PageResponseDTO<UserDTO> getAll(Long page, Long take,
      String animal, OrderByField orderByField, String nickname) {

    return userQueryRepository.findAll(page, take, animal, orderByField, nickname);
  }

  @Transactional
  public User save(SocialUserInfoDTO socialUserInfo, OAuthClient client,
      OAuthTokenDTO socialToken) {
    User user = userQueryRepository.findByUniqueIdentifier(socialUserInfo.uniqueIdentifier())
        .orElseGet(() ->
            User.createFromSocial(
                socialUserInfo.uniqueIdentifier(),
                socialUserInfo.name(),
                socialUserInfo.profileImageUrl(),
                client.getProviderName()
            )
        );

    user.nullifyDeletedAt();
    user.updateToken(socialToken.socialAccessToken(), socialToken.socialRefreshToken());

    boolean isFirst = user.getNo() == null;

    if (isFirst) {
      User savedUser = userRepository.save(user);

      UserSocialToken savedToken = socialTokenService.save(user.getToken(), savedUser.getNo());
      legendService.create(savedUser.getNo());

      savedUser.updateToken(savedToken.getSocialAccessToken(), savedToken.getSocialRefreshToken());
      return savedUser;
    }

    User savedUser = userRepository.save(user);
    UserSocialToken savedToken = socialTokenService.save(user.getToken(), savedUser.getNo());
    savedUser.updateToken(savedToken.getSocialAccessToken(), savedToken.getSocialRefreshToken());

    return savedUser;
  }

  @Transactional
  public void isPresent(Long userNo) {
    if (!userQueryRepository.exists(userNo)) {
      throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND, " userNo: " + userNo);
    }
  }

  @Transactional(readOnly = true)
  public String getUserNickname(Long userNo) {
    return userQueryRepository.findNameByUserNo(userNo).orElseThrow(
        () -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND, " userNo: " + userNo));
  }

  @Transactional
  public void processGameResult(Long userNo, Long pointToAdd) {
    User user = userRepository.findUserByUserNoForUpdate(userNo).orElseThrow(
        () -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND, " userNo: " + userNo));

    try {
      user.decreaseChance();
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.USER_NOT_HAS_ENOUGH_CHANCE);
    }

    user.increaseCurrentAccumulationPoint(pointToAdd);

    userRepository.save(user);
  }

  @Transactional
  public void decreaseCurrentPoint(Long userNo, Long price) {
    User user = userRepository.findUserByUserNoForUpdate(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

    try {
      user.decreaseCurrentPoint(price);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.USER_NOT_HAS_ENOUGH_POINT,
          " " + e.getMessage());
    }

    userRepository.save(user);
  }

  @Transactional
  public void increaseCurrentAccumulationPoint(Long userNo, Long price) {
    User user = userRepository.findUserByUserNoForUpdate(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

    user.increaseCurrentAccumulationPoint(price);

    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public UserAttendanceDTO getUserAttendance(Long userNo) {
    return userQueryRepository.findAttendance(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));
  }

  @Transactional
  public UserAttendanceDTO updateAttendance(Long userNo, Integer attendanceStickerNumber) {
    User user = userRepository.findUserByUserNoForUpdate(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

    try {
      user.updateAttendanceAndIncreasePoint(attendanceStickerNumber);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.USER_ALREADY_ATTENDANCE);
    }

    eventPublisher.publishEvent(new UserAttendanceUpdatedEvent(userNo));
    userRepository.save(user);

    return new UserAttendanceDTO(user.getNickname(), user.getAttendance());
  }
}
