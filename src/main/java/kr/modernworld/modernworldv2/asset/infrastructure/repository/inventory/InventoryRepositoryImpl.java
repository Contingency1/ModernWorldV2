package kr.modernworld.modernworldv2.asset.infrastructure.repository.inventory;

import kr.modernworld.modernworldv2.asset.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.asset.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.asset.domain.inventory.port.InventoryRepository;
import kr.modernworld.modernworldv2.asset.infrastructure.mapper.InventoryMapper;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.InventoryJPAEntity;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class InventoryRepositoryImpl implements InventoryRepository {

  private final InventoryJPARepository inventoryJPARepository;
  private final InventoryMapper inventoryMapper;

  @Override
  public Inventory save(Inventory inventory) {
    if (inventory.getNo() == null) {
      InventoryJPAEntity save = inventoryJPARepository.save(inventoryMapper.toJPAEntity(inventory));

      return inventoryMapper.toDomain(save);
    }

    InventoryJPAEntity entity = inventoryJPARepository.findById(inventory.getNo())
        .orElseThrow(() -> new BusinessException(
            BusinessErrorCode.ITEM_NOT_FOUND_IN_INVENTORY));

    inventoryMapper.updateEntityFromDomain(inventory, entity);
    return inventoryMapper.toDomain(entity);
  }

  @Override
  public void update(InventoryCollection items) {
    inventoryJPARepository.saveAll(
        items.getInventory().stream().map(inventoryMapper::toJPAEntity).toList());
  }
}
