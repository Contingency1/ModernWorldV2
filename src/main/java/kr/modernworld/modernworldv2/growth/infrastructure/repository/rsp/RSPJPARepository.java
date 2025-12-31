package kr.modernworld.modernworldv2.growth.infrastructure.repository.rsp;

import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.RspGameRecordJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSPJPARepository extends JpaRepository<RspGameRecordJPAEntity, Long> {

}
