package kr.modernworld.modernworldv2.growth.application.achievement.dto;

public record AchievementDTO(
    Long no,
    String title,
    String description,
    String level,
    Integer point
) {

}
