package kr.modernworld.modernworldv2.growth.infrastructure.mapper;

import kr.modernworld.modernworldv2.growth.domain.userachievement.UserAchievement;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserAchievementJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserAchievementMapper {

  @Mapping(target = "userNo", source = "user.no")
  @Mapping(target = "achievementNo", source = "achievement.no")
  UserAchievement toDomain(UserAchievementJPAEntity userAchievement);

  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "achievement.no", source = "achievementNo")
  UserAchievementJPAEntity toEntity(UserAchievement userAchievement);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "achievement.no", source = "achievementNo")
  void updateEntityFromDomain(UserAchievement userAchievement,
      @MappingTarget UserAchievementJPAEntity achievementJPAEntity);

}
