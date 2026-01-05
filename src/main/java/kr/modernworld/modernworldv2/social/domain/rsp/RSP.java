package kr.modernworld.modernworldv2.social.domain.rsp;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RSP {

  private final Long no;

  private final Long userNo;

  private final RSPChoice userChoice;

  private final RSPChoice computerChoice;

  private final GameResult result;

  private final Instant createdAt;

  @Builder
  private RSP(Long no, Long userNo, RSPChoice userChoice, RSPChoice computerChoice,
      GameResult result,
      Instant createdAt) {
    this.no = no;
    this.userNo = userNo;
    this.userChoice = userChoice;
    this.computerChoice = computerChoice;
    this.result = result;
    this.createdAt = createdAt;
  }

  public static RSP init(Long userNo, RSPChoice userChoice) {
    GameResult gameResult = GameResult.LOSE;

    RSPChoice computerChoice = RSPChoice.integerToRSPChoice(
        ThreadLocalRandom.current().nextInt(0, 3));

    if (userChoice == null || userChoice.equals(RSPChoice.NULL)) {
      return RSP.builder()
          .userNo(userNo)
          .userChoice(userChoice == null ? RSPChoice.NULL : userChoice)
          .computerChoice(computerChoice)
          .result(gameResult)
          .createdAt(Instant.now())
          .build();
    }

    gameResult = userChoice.startGame(computerChoice);

    return RSP.builder()
        .userNo(userNo)
        .userChoice(userChoice)
        .computerChoice(computerChoice)
        .result(gameResult)
        .createdAt(Instant.now())
        .build();
  }

}
