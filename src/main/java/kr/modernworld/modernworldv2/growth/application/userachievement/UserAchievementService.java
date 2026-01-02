package kr.modernworld.modernworldv2.growth.application.userachievement;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.userachievement.dto.GetUserAchievementDTO;
import kr.modernworld.modernworldv2.growth.application.userachievement.port.UserAchievementQueryRepository;
import kr.modernworld.modernworldv2.growth.domain.userachievement.UserAchievement;
import kr.modernworld.modernworldv2.growth.domain.userachievement.port.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAchievementService {

  private final UserAchievementQueryRepository userAchievementQueryRepository;
  private final UserAchievementRepository userAchievementRepository;

  @Transactional(readOnly = true)
  public List<GetUserAchievementDTO> getUserAchievements(Long userNo,
      String title, String category) {

    return userAchievementQueryRepository.getUserAchievements(
        userNo, title, category);
  }

  @Transactional
  public void createUserAchievement(Long userNo, Long achievementNo) {
    UserAchievement achievement = UserAchievement.init(userNo, achievementNo);

    userAchievementRepository.save(achievement);
  }

  @Transactional
  public UserAchievement updateUserAchievementStatus
      (Long userNo, Long achievementNo, Boolean status) {
    UserAchievement target = userAchievementRepository.
        findOneForUpdate(userNo, achievementNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_ACHIEVEMENT_NOT_FOUND));

    try {
      target.validationUserNo(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.USER_ACHIEVEMENT_NOT_FOUND);
    }

    if (!status) {
      if (!target.getStatus()) {
        return target;
      }

      target.unequip();
      return userAchievementRepository.save(target);
    }

    Optional<UserAchievement> alreadyEquippedAchievement = userAchievementRepository
        .findByUserNoAndStatusForUpdate(userNo, true);

    if (alreadyEquippedAchievement.isPresent()) {
      UserAchievement userAchievement = alreadyEquippedAchievement.get();

      if (!target.getNo().equals(userAchievement.getNo())) {
        userAchievement.unequip();
        userAchievementRepository.save(userAchievement);
      }
    }

    if (target.getStatus()) {
      return target;
    }

    target.equip();
    return userAchievementRepository.save(target);
  }

}
