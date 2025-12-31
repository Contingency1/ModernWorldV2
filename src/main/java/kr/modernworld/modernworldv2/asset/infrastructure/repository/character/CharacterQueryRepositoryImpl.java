package kr.modernworld.modernworldv2.asset.infrastructure.repository.character;


import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterJPAEntity.characterJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.character.dto.CharacterApiDTO;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.character.port.CharacterQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CharacterQueryRepositoryImpl implements CharacterQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Long> getPrice(Long no) {
    Long price = queryFactory
        .select(characterJPAEntity.price)
        .from(characterJPAEntity)
        .where(characterJPAEntity.no.eq(no))
        .fetchOne();

    if (price == null) {
      return Optional.empty();
    }

    return Optional.of(price);
  }

  @Override
  public Optional<CharacterApiDTO> findOne(Long no) {
    CharacterApiDTO record = queryFactory
        .select(Projections.constructor(
            CharacterApiDTO.class,
            characterJPAEntity.no,
            characterJPAEntity.name,
            characterJPAEntity.description,
            characterJPAEntity.image,
            characterJPAEntity.species,
            characterJPAEntity.price
        ))
        .from(characterJPAEntity)
        .where(characterJPAEntity.no.eq(no))
        .fetchOne();

    if (record == null) {
      return Optional.empty();
    }

    return Optional.of(record);
  }

  @Override
  public List<CharacterApiDTO> findAll(CharacterSpecies species, String name) {
    return queryFactory
        .select(Projections.constructor(
            CharacterApiDTO.class,
            characterJPAEntity.no,
            characterJPAEntity.name,
            characterJPAEntity.description,
            characterJPAEntity.image,
            characterJPAEntity.species,
            characterJPAEntity.price
        ))
        .from(characterJPAEntity)
        .where(speciesEq(species), nameContains(name))
        .fetch();
  }

  private BooleanExpression speciesEq(CharacterSpecies species) {
    return species == null ? null : characterJPAEntity.species.eq(species);

  }

  private BooleanExpression nameContains(String name) {
    return name == null ? null : characterJPAEntity.name.contains(name);
  }
}
