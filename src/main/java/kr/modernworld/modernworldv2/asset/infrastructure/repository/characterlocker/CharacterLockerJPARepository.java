package kr.modernworld.modernworldv2.asset.infrastructure.repository.characterlocker;

import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterLockerJPARepository extends
    JpaRepository<CharacterLockerJPAEntity, Long> {

}
