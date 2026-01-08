package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterJPAEntity.characterJPAEntity;
import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterLockerJPAEntity.characterLockerJPAEntity;
import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity.achievementJPAEntity;
import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QLegendJPAEntity.legendJPAEntity;
import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserAchievementJPAEntity.userAchievementJPAEntity;
import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterJPAEntity;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterLockerJPAEntity;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QLegendJPAEntity;
import kr.modernworld.modernworldv2.member.application.user.OrderByField;
import kr.modernworld.modernworldv2.member.application.user.dto.UserAttendanceDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserAchievementDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserAchievementDetailDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserCharacterDTO;
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
  public PageResponseDTO<UserDTO> findAll(Long page, Long take, String animal,
      OrderByField orderBy,
      String nickname) {
    List<UserDTO> baseUsers = queryFactory
        .select(Projections.constructor(UserDTO.class,
            user.no,
            user.socialName,
            user.nickname,
            user.description,
            user.currentPoint,
            user.accumulationPoint,
            user.image,
            Projections.constructor(UserLegendDTO.class,
                legendJPAEntity.likeCount),
            user.chance
        ))
        .from(user)
        .leftJoin(user.legend, legendJPAEntity)
        .where(
            eqNickname(nickname),
            eqAnimal(animal),
            user.deletedAt.isNull()
        )
        .orderBy(createOrderSpecifier(orderBy))
        .offset((page - 1) * take)
        .limit(take)
        .fetch();

    if (baseUsers.isEmpty()) {
      return new PageResponseDTO<>(Collections.emptyList(), new PageMetaDTO(page, take, 0L, 0L));
    }

    List<Long> userIds = baseUsers.stream().map(UserDTO::no).toList();

    Map<Long, List<UserCharacterLockerDTO>> lockerMap = queryFactory
        .from(characterLocker)
        .join(characterLocker.character, characterJPAEntity)
        .where(
            characterLocker.user.no.in(userIds),
            characterLocker.status.isTrue()
        )
        .transform(
            GroupBy.groupBy(characterLocker.user.no)
                .as(GroupBy.list(
                        Projections.constructor(UserCharacterLockerDTO.class,
                            Projections.constructor(UserCharacterDTO.class,
                                characterJPAEntity.no,
                                characterJPAEntity.image
                            )
                        )
                    )
                )
        );

    Map<Long, List<UserAchievementDTO>> achievementMap = queryFactory
        .from(userAchievement)
        .join(userAchievement.achievement, achievementJPAEntity)
        .where(
            userAchievement.user.no.in(userIds),
            userAchievement.status.isTrue())
        .transform(
            GroupBy.groupBy(userAchievement.user.no)
                .as(GroupBy.list(
                    Projections.constructor(UserAchievementDTO.class,
                        Projections.constructor(UserAchievementDetailDTO.class,
                            achievementJPAEntity.title,
                            achievementJPAEntity.level.stringValue()
                        )
                    )
                )));

    List<UserDTO> finalContent = baseUsers.stream()
        .map(u -> new UserDTO(
            u.no(), u.socialName(), u.nickname(), u.description(),
            u.currentPoint(), u.accumulationPoint(), u.image(), u.legend(),
            lockerMap.getOrDefault(u.no(), Collections.emptyList()),
            achievementMap.getOrDefault(u.no(), Collections.emptyList()),
            u.chance()
        ))
        .toList();

    Long total = queryFactory
        .select(userJPAEntity.count())
        .from(userJPAEntity)
        .where(
            eqNickname(nickname),
            userJPAEntity.deletedAt.isNull(),
            eqAnimal(animal)
        )
        .fetchOne();

    long totalPage = (long) Math.ceil((double) total / take);

    return new PageResponseDTO<>(finalContent, new PageMetaDTO(page, take, total, totalPage));
  }

  @Override
  public Optional<UserAttendanceDTO> findAttendance(Long userNo) {
    UserAttendanceDTO data = queryFactory
        .select(
            Projections.constructor(UserAttendanceDTO.class,
                user.nickname,
                user.attendance))
        .from(user)
        .where(user.no.eq(userNo))
        .fetchOne();

    if (data == null) {
      return Optional.empty();
    }

    return Optional.of(data);
  }

  private OrderSpecifier<?>[] createOrderSpecifier(OrderByField orderBy) {
    OrderSpecifier<?> first;
    OrderSpecifier<?> second = new OrderSpecifier<>(Order.DESC, userJPAEntity.no);

    if (orderBy != null && "like".equals(orderBy.getField())) {
      first = new OrderSpecifier<>(Order.DESC, legendJPAEntity.likeCount);
    } else if (orderBy != null && "accumulationPoint".equals(orderBy.getField())) {
      first = new OrderSpecifier<>(Order.DESC, userJPAEntity.accumulationPoint);
    } else {
      first = new OrderSpecifier<>(Order.DESC, userJPAEntity.createdAt);
    }

    return new OrderSpecifier[]{first, second};
  }

  private BooleanExpression eqNickname(String nickname) {
    return nickname != null ? userJPAEntity.nickname.contains(nickname) : null;
  }

  private BooleanExpression eqAnimal(String animal) {
    if (animal == null) {
      return null;
    }

    return JPAExpressions.selectOne()
        .from(characterLocker)
        .join(characterLocker.character, characterJPAEntity)
        .where(
            characterLocker.user.eq(userJPAEntity),
            characterLocker.status.isTrue(),
            characterJPAEntity.species.stringValue().eq(animal)
        )
        .exists();
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
