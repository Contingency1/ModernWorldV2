package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserBatchRepository {

  Long deleteExpiredUsers(LocalDateTime threshold);

  Long resetAllUserChance(Long count);

  Long resetAllUserAttendance(Map<String, List<Integer>> data);
}
