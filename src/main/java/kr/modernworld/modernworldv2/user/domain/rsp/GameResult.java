package kr.modernworld.modernworldv2.user.domain.rsp;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameResult {
  WIN("win"), LOSE("lose"), DRAW("draw");

  final String result;

  GameResult(String result) {
    this.result = result;
  }

  public String toString() {
    return this.result;
  }

  public static GameResult stringToGameResult(String result) {
    for (GameResult gameResult : GameResult.values()) {
      if (gameResult.toString().equals(result)) {
        return gameResult;
      }
    }

    throw new IllegalArgumentException("Invalid gameResult: " + result);
  }

  @JsonValue
  public String getResult() {
    return this.result;
  }
}
