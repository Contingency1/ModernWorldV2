package kr.modernworld.modernworldv2.admin.infrastructure.repository.character;

import kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.CharacterJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterJPARepository extends JpaRepository<CharacterJPAEntity, Long> {

}
