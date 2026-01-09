package kr.modernworld.modernworldv2.member.infrastructure.scheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kr.modernworld.modernworldv2.member.infrastructure.repository.user.UserBatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserBatchScheduler {

  private final UserBatchRepository userBatchRepository;

  /**
   * 1. 탈퇴 30일 경과 유저 영구 삭제
   * <p>
   * 실행 시각: 한국 시각 매일 새벽 03:00
   */
  @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
  @Transactional
  public void deleteExpiredUsers() {
    log.info("[Scheduler] Start deleting expired users.");

    Instant threshold = LocalDateTime.now()
        .minusDays(30)
        .atZone(ZoneId.of("Asia/Seoul"))
        .toInstant();
    Long count = userBatchRepository.deleteExpiredUsers(threshold);

    log.info("[Scheduler] Finished deleting expired users. deleted {} users", count);
  }

  /**
   * 2. 유저 Chance 10으로 초기화
   * <p>
   * 실행 시각: 한국 시각 매일 자정 (00:00)
   */
  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
  @Transactional
  public void resetDailyChance() {
    log.info("[Scheduler] Start resetting daily chances.");
    Long count = userBatchRepository.resetAllUserChance(10L);
    log.info("[Scheduler] Finished resetting daily chances. changed {} users", count);
  }

  /**
   * 3. 유저 Attendance 초기화
   * <p>
   * 실행 시각: 한국 시각 매주 월요일 00:00:00
   * <p>
   * Spring Cron: 초 분 시 일 월 요일(MON)
   */
  @Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Seoul")
  @Transactional
  public void resetWeeklyAttendance() {
    Map<String, List<Integer>> data = new LinkedHashMap<>();

    data.put("0", new ArrayList<>(List.of(0, 100)));
    data.put("1", new ArrayList<>(List.of(0, 200)));
    data.put("2", new ArrayList<>(List.of(0, 300)));
    data.put("3", new ArrayList<>(List.of(0, 200)));
    data.put("4", new ArrayList<>(List.of(0, 400)));
    data.put("5", new ArrayList<>(List.of(0, 300)));
    data.put("6", new ArrayList<>(List.of(0, 300)));

    log.info("[Scheduler] Start resetting weekly attendance.");
    Long count = userBatchRepository.resetAllUserAttendance(data);
    log.info("[Scheduler] Finished resetting weekly attendance. changed {} users", count);
  }
}
