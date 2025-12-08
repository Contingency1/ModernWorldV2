package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.UserAchievement;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserAchievementJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAchievementMapper {

  @Mapping(target = "userNo", source = "user.no")
  @Mapping(target = "achievementNo", source = "achievement.no")
  UserAchievement toDomain(UserAchievementJPAEntity userAchievement);

  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "achievement.no", source = "achievementNo")
  UserAchievementJPAEntity toEntity(UserAchievement userAchievement);

}
