package kr.modernworld.modernworldv2.user.infrastructure.repository.inventory;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.InventoryJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryJPARepository extends JpaRepository<InventoryJPAEntity, Long> {

}
