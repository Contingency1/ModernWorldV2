package kr.modernworld.modernworldv2.admin.infrastructure.repository.character;


import static kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.QCharacterJPAEntity.characterJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.admin.domain.character.port.CharacterQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CharacterQueryRepositoryImpl implements CharacterQueryRepository {

  private final JPAQueryFactory jpaQuery;

  @Override
  public Optional<Long> getPrice(Long no) {
    Long price = jpaQuery
        .select(characterJPAEntity.price)
        .from(characterJPAEntity)
        .where(characterJPAEntity.no.eq(no))
        .fetchOne();

    if (price == null) {
      return Optional.empty();
    }

    return Optional.of(price);
  }
}
