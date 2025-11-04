package kr.modernworld.modernworldv2.user.infrastructure.repository;

import kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.CharacterJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterLockerJPARepository extends JpaRepository<CharacterJPAEntity, Long> {

}
