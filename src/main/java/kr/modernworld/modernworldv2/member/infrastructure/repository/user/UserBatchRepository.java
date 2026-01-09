package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface UserBatchRepository {

  Long deleteExpiredUsers(Instant threshold);

  Long resetAllUserChance(Long count);

  Long resetAllUserAttendance(Map<String, List<Integer>> data);
}
