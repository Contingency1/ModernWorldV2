package kr.modernworld.modernworldv2.admin.infrastructure.repository.item;

import static kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.QItemJPAEntity.itemJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.admin.application.api.ItemPriceAndTypeDTO;
import kr.modernworld.modernworldv2.admin.domain.port.item.ItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<ItemPriceAndTypeDTO> getPriceAndType(Long itemNo) {
    ItemPriceAndTypeDTO itemInfo = queryFactory
        .select(
            Projections.constructor(ItemPriceAndTypeDTO.class,
                itemJPAEntity.price,
                itemJPAEntity.type))
        .from(itemJPAEntity)
        .where(itemJPAEntity.no.eq(itemNo))
        .fetchFirst();

    if (itemInfo == null) {
      return Optional.empty();
    }

    return Optional.of(itemInfo);

  }
}
