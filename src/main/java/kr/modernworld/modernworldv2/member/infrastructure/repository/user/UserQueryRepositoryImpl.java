package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterJPAEntity.characterJPAEntity;
import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterLockerJPAEntity.characterLockerJPAEntity;
import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity.achievementJPAEntity;
import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QLegendJPAEntity.legendJPAEntity;
import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserAchievementJPAEntity.userAchievementJPAEntity;
import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterJPAEntity;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterLockerJPAEntity;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QLegendJPAEntity;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserAchievementDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserAchievementDetailDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserCharacterLockerDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserLegendDTO;
import kr.modernworld.modernworldv2.member.domain.user.User;
import kr.modernworld.modernworldv2.member.domain.user.port.UserQueryRepository;
import kr.modernworld.modernworldv2.member.infrastructure.mapper.UserMapper;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserAchievementJPAEntity;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final UserJPARepository userJPARepository;
  private final UserMapper userMapper;
  private final JPAQueryFactory queryFactory;

  private static final QUserJPAEntity user = userJPAEntity;
  private static final QLegendJPAEntity legend = legendJPAEntity;
  private static final QCharacterLockerJPAEntity characterLocker = characterLockerJPAEntity;
  private static final QCharacterJPAEntity character = characterJPAEntity;
  private static final QUserAchievementJPAEntity userAchievement = userAchievementJPAEntity;
  private static final QAchievementJPAEntity achievement = achievementJPAEntity;

  @Override
  public Optional<User> findByUniqueIdentifier(String uniqueIdentifier) {
    return userJPARepository.findByUniqueIdentifier(uniqueIdentifier).map(userMapper::toDomain);
  }

  @Override
  public Boolean exists(Long userNo) {
    Integer one = queryFactory
        .selectOne()
        .from(userJPAEntity)
        .where(userJPAEntity.no.eq(userNo))
        .fetchFirst();

    return one != null;
  }

  @Override
  public Optional<UserDTO> findOne(Long userNo) {
    Tuple userTuple = queryFactory
        .select(
            user.no,
            user.socialName,
            user.nickname,
            user.description,
            user.currentPoint,
            user.accumulationPoint,
            user.image,
            user.chance
        )
        .from(user)
        .where(user.no.eq(userNo), user.deletedAt.isNull())
        .fetchOne();

    if (userTuple == null) {
      return Optional.empty();
    }

    UserLegendDTO legendDTO = queryFactory.select(
            Projections.constructor(UserLegendDTO.class,
                legend.likeCount
            ))
        .from(legend)
        .where(legend.user.no.eq(userNo))
        .fetchOne();

    List<UserCharacterLockerDTO> lockerList = queryFactory
        .select(Projections.constructor(UserDTO.UserCharacterLockerDTO.class,
            Projections.constructor(UserDTO.UserCharacterDTO.class,
                character.no,
                character.image
            )
        ))
        .from(characterLocker)
        .join(characterLocker.character, character)
        .where(
            characterLocker.user.no.eq(userNo),
            characterLocker.status.isTrue()
        )
        .fetch();

    List<UserAchievementDTO> achievementList = queryFactory
        .select(Projections.constructor(UserAchievementDTO.class,
            Projections.constructor(UserAchievementDetailDTO.class,
                achievement.title,
                achievement.level.stringValue()
            )
        ))
        .from(userAchievement)
        .join(userAchievement.achievement, achievement)
        .where(
            userAchievement.user.no.eq(userNo),
            userAchievement.status.isTrue()
        )
        .fetch();

    return Optional.of(
        new UserDTO(
            userTuple.get(user.no),
            userTuple.get(user.socialName),
            userTuple.get(user.nickname),
            userTuple.get(user.description),
            userTuple.get(user.currentPoint),
            userTuple.get(user.accumulationPoint),
            userTuple.get(user.image),
            legendDTO,
            lockerList,
            achievementList,
            userTuple.get(user.chance)
        ));
  }

  @Override
  public Optional<String> findNameByUserNo(Long userNo) {
    String name = queryFactory
        .select(userJPAEntity.nickname)
        .from(userJPAEntity)
        .where(userJPAEntity.no.eq(userNo))
        .fetchFirst();

    if (name == null) {
      return Optional.empty();
    }

    return Optional.of(name);
  }
}
