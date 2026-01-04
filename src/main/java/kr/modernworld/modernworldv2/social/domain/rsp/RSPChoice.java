package kr.modernworld.modernworldv2.social.domain.rsp;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RSPChoice {

  SCISSORS(0, "Scissors"), PAPER(1, "Paper"), ROCK(2, "Rock"),
  NULL(null, "-");

  final Integer number;
  final String name;

  RSPChoice(Integer number, String name) {
    this.number = number;
    this.name = name;
  }

  public static RSPChoice integerToRSPChoice(Integer number) {
    if (number == null) {
      return NULL;
    }

    for (RSPChoice choice : RSPChoice.values()) {
      if (number.equals(choice.number)) {
        return choice;
      }
    }

    throw new IllegalArgumentException("No such choice: " + number);
  }

  public static RSPChoice stringToRSPChoice(String str) {
    for (RSPChoice choice : RSPChoice.values()) {
      if (str.equals(choice.name)) {
        return choice;
      }
    }

    throw new IllegalArgumentException("No such choice: " + str);
  }

  public GameResult startGame(RSPChoice opponent) {
    if (this == NULL || opponent == NULL) {
      throw new IllegalStateException("Cannot play with NULL choice");
    }

    if (this == opponent) {
      return GameResult.DRAW;
    }

    if ((this == ROCK && opponent == SCISSORS) ||
        (this == PAPER && opponent == ROCK) ||
        (this == SCISSORS && opponent == PAPER)) {
      return GameResult.WIN;
    }

    return GameResult.LOSE;
  }

  @JsonValue
  public String getName() {
    return this.name;
  }
}
