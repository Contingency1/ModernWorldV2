package kr.modernworld.modernworldv2.admin.infrastructure.repository.item;

import static kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.QItemJPAEntity.itemJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.admin.domain.port.item.ItemQueryRepository;
import kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity.ItemJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Long> getPrice(Long itemNo) {
    Long price = queryFactory
        .select(itemJPAEntity.price)
        .from(itemJPAEntity)
        .where(itemJPAEntity.no.eq(itemNo))
        .fetchFirst();

    if (price == null) {
      return Optional.empty();
    }

    return Optional.of(price);
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
