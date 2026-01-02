package kr.modernworld.modernworldv2.asset.infrastructure.repository.characterlocker;

import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterJPAEntity.characterJPAEntity;
import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QCharacterLockerJPAEntity.characterLockerJPAEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.CharacterInfoDTO;
import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.GetCharacterLockerDTO;
import kr.modernworld.modernworldv2.asset.application.characterlocker.port.CharacterLockerQueryRepository;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLockerCollection;
import kr.modernworld.modernworldv2.asset.infrastructure.mapper.CharacterLockerMapper;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CharacterLockerQueryRepositoryImpl implements CharacterLockerQueryRepository {

  private final CharacterLockerMapper characterLockerMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public List<GetCharacterLockerDTO> getUserCharactersByCondition(Long userNo,
      Boolean status, CharacterSpecies species) {
    return queryFactory
        .select(getProjections())
        .from(characterLockerJPAEntity)
        .where(
            characterLockerJPAEntity.user.no.eq(userNo),
            speciesEq(species),
            statusEq(status)
        )
        .join(characterLockerJPAEntity.character, characterJPAEntity)
        .fetch();
  }

  private ConstructorExpression<GetCharacterLockerDTO> getProjections() {
    return Projections.constructor(
        GetCharacterLockerDTO.class,
        characterLockerJPAEntity.no,
        characterLockerJPAEntity.character.no,
        characterLockerJPAEntity.user.no,
        characterLockerJPAEntity.status,
        Projections.constructor(CharacterInfoDTO.class,
            characterJPAEntity.name,
            characterJPAEntity.description,
            characterJPAEntity.image,
            characterJPAEntity.species,
            characterJPAEntity.price
        )
    );
  }

  private BooleanExpression statusEq(Boolean status) {
    if (status == null) {
      return null;
    }

    return characterLockerJPAEntity.status.eq(status);
  }

  private BooleanExpression speciesEq(CharacterSpecies species) {
    if (species == null) {
      return null;
    }

    return characterLockerJPAEntity.character.species.eq(species);
  }

  @Override
  public CharacterLockerCollection getUserAllCharacters(Long userNo) {
    List<CharacterLockerJPAEntity> data = queryFactory
        .select(characterLockerJPAEntity)
        .from(characterLockerJPAEntity)
        .where(characterLockerJPAEntity.user.no.eq(userNo))
        .fetch();

    return new CharacterLockerCollection(
        data.stream().map(characterLockerMapper::toDomain).toList());
  }

  @Override
  public Boolean exists(Long userNo, Long characterNo) {
    Integer exist = queryFactory
        .selectOne()
        .from(characterLockerJPAEntity)
        .where(
            characterLockerJPAEntity.user.no.eq(userNo),
            characterLockerJPAEntity.character.no.eq(characterNo))
        .fetchFirst();

    return exist != null;
  }
}
