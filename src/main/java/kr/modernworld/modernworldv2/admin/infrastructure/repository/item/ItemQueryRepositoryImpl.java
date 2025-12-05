package kr.modernworld.modernworldv2.admin.infrastructure.repository.item;

import static kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.QItemJPAEntity.itemJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.admin.application.api.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.admin.domain.port.item.ItemQueryRepository;
import kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.ItemJPAEntity;
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


}
