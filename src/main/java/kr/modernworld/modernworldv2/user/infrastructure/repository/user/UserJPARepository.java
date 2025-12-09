package kr.modernworld.modernworldv2.user.infrastructure.repository.user;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, Long> {

  Optional<UserJPAEntity> findByUniqueIdentifier(String uniqueIdentifier);

}
