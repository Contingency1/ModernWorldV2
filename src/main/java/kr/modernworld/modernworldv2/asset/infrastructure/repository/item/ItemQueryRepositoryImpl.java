package kr.modernworld.modernworldv2.asset.infrastructure.repository.item;

import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QItemJPAEntity.itemJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemApiDTO;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.asset.application.item.port.ItemQueryRepository;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.ItemJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<ItemNameAndPriceDTO> findNameAndPrice(Long itemNo) {
    ItemNameAndPriceDTO nameAndPrice = queryFactory
        .select(Projections.constructor(ItemNameAndPriceDTO.class,
            itemJPAEntity.name, itemJPAEntity.price))
        .from(itemJPAEntity)
        .where(itemJPAEntity.no.eq(itemNo))
        .fetchFirst();

    if (nameAndPrice == null) {
      return Optional.empty();
    }

    return Optional.of(nameAndPrice);
  }

  @Override
  public Boolean exists(Long itemNo) {
    ItemJPAEntity entity = queryFactory
        .select(itemJPAEntity)
        .from(itemJPAEntity)
        .where(itemJPAEntity.no.eq(itemNo))
        .fetchOne();

    return entity != null;
  }

  @Override
  public Optional<ItemApiDTO> findOne(Long itemNo) {
    ItemApiDTO entity = queryFactory
        .select(
            Projections.constructor(
                ItemApiDTO.class,
                itemJPAEntity.no,
                itemJPAEntity.name,
                itemJPAEntity.description,
                itemJPAEntity.image,
                itemJPAEntity.theme,
                itemJPAEntity.type,
                itemJPAEntity.price
            )
        )
        .from(itemJPAEntity)
        .where(itemJPAEntity.no.eq(itemNo))
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(entity);
  }

  @Override
  public List<ItemApiDTO> findAll(String theme, String itemName) {
    return queryFactory
        .select(
            Projections.constructor(
                ItemApiDTO.class,
                itemJPAEntity.no,
                itemJPAEntity.name,
                itemJPAEntity.description,
                itemJPAEntity.image,
                itemJPAEntity.theme,
                itemJPAEntity.type,
                itemJPAEntity.price
            )
        )
        .from(itemJPAEntity)
        .where(themeContains(theme), itemNameContains(itemName))
        .fetch();
  }

  private BooleanExpression themeContains(String theme) {
    return theme == null ? null : itemJPAEntity.theme.contains(theme);
  }

  private BooleanExpression itemNameContains(String itemName) {
    return itemName == null ? null : itemJPAEntity.name.contains(itemName);
  }


}
