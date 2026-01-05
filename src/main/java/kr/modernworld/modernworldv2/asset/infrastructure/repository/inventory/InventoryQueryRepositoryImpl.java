package kr.modernworld.modernworldv2.asset.infrastructure.repository.inventory;

import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QInventoryJPAEntity.inventoryJPAEntity;
import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QItemJPAEntity.itemJPAEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.inventory.dto.GetInventoryDTO;
import kr.modernworld.modernworldv2.asset.application.inventory.dto.InventoryItemDTO;
import kr.modernworld.modernworldv2.asset.application.inventory.port.InventoryQueryRepository;
import kr.modernworld.modernworldv2.asset.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.asset.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.asset.domain.item.ItemType;
import kr.modernworld.modernworldv2.asset.infrastructure.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@Slf4j
public class InventoryQueryRepositoryImpl implements InventoryQueryRepository {

  private final JPAQueryFactory queryFactory;
  private final InventoryMapper inventoryMapper;

  @Override
  public List<GetInventoryDTO> getInventory(Long userNo, String theme, Boolean status,
      String itemName) {
    return queryFactory
        .select(inventorySelect())
        .from(inventoryJPAEntity)
        .join(inventoryJPAEntity.item, itemJPAEntity)
        .where(
            inventoryJPAEntity.user.no.eq(userNo),
            itemThemeContains(theme),
            statusEq(status),
            itemNameContains(itemName)
        )
        .fetch();
  }

  private static ConstructorExpression<GetInventoryDTO> inventorySelect() {
    return Projections.constructor(GetInventoryDTO.class,
        inventoryJPAEntity.no,
        inventoryJPAEntity.user.no,
        inventoryJPAEntity.item.no,
        inventoryJPAEntity.createdAt,
        inventoryJPAEntity.status,
        Projections.constructor(InventoryItemDTO.class,
            itemJPAEntity.no,
            itemJPAEntity.name,
            itemJPAEntity.description,
            itemJPAEntity.image,
            itemJPAEntity.theme,
            itemJPAEntity.type,
            itemJPAEntity.price
        )
    );
  }

  private BooleanExpression itemThemeContains(String theme) {
    if (theme == null || theme.isEmpty()) {
      return null;
    }

    return inventoryJPAEntity.item.theme.contains(theme);
  }

  private BooleanExpression itemNameContains(String itemName) {
    if (itemName == null || itemName.isEmpty()) {
      return null;
    }

    return inventoryJPAEntity.item.name.contains(itemName);
  }

  private BooleanExpression statusEq(Boolean status) {
    if (status == null) {
      return null;
    }

    return inventoryJPAEntity.status.eq(status);
  }

  @Override
  public Optional<ItemType> findInventoryItemType(Long userNo, Long itemNo) {

    ItemType s = queryFactory
        .select(inventoryJPAEntity.item.type)
        .from(inventoryJPAEntity)
        .where(inventoryJPAEntity.user.no.eq(userNo), inventoryJPAEntity.item.no.eq(itemNo))
        .fetchOne();

    if (s == null) {
      return Optional.empty();
    }

    return Optional.of(s);
  }

  @Override
  public InventoryCollection findInventoryByUserNoAndTypeNo(Long userId, ItemType type) {

    List<Inventory> userItems = queryFactory
        .select(inventoryJPAEntity)
        .from(inventoryJPAEntity)
        .join(inventoryJPAEntity.item, itemJPAEntity)
        .fetchJoin()
        .where(inventoryJPAEntity.user.no.eq(userId),
            inventoryJPAEntity.item.type.eq(type))
        .fetch()
        .stream()
        .map(inventoryMapper::toDomain)
        .toList();

    return new InventoryCollection(userItems);
  }

  @Override
  public Boolean exists(Long userNo, Long itemNo) {
    Integer exist = queryFactory
        .selectOne()
        .from(inventoryJPAEntity)
        .where(inventoryJPAEntity.user.no.eq(userNo), inventoryJPAEntity.item.no.eq(itemNo))
        .fetchFirst();

    return exist != null;
  }
}
