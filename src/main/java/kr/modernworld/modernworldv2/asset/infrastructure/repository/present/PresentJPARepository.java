package kr.modernworld.modernworldv2.asset.infrastructure.repository.present;

import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.PresentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentJPARepository extends JpaRepository<PresentJPAEntity, Long> {

}
