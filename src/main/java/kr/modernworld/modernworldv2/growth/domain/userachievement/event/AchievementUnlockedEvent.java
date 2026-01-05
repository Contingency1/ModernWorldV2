package kr.modernworld.modernworldv2.growth.domain.userachievement.event;

public record AchievementUnlockedEvent(
    Long userNo,
    String achievementTitle,
    Long achievementRewardPoint
) {

}
