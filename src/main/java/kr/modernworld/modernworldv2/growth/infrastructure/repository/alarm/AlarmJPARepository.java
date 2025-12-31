package kr.modernworld.modernworldv2.growth.infrastructure.repository.alarm;

import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.AlarmJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmJPARepository extends JpaRepository<AlarmJPAEntity, Long> {

}
