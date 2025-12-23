package kr.modernworld.modernworldv2.global.common;

import lombok.Getter;

@Getter
public enum RewardPoint {
  WIN_GAME(500L);

  final Long point;

  RewardPoint(Long point) {
    this.point = point;
  }
}
