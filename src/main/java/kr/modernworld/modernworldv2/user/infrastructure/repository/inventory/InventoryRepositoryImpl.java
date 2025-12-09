package kr.modernworld.modernworldv2.user.infrastructure.repository.inventory;

import kr.modernworld.modernworldv2.user.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.user.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.user.domain.inventory.port.InventoryRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.InventoryMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.InventoryJPAEntity;
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
    InventoryJPAEntity save = inventoryJPARepository.save(inventoryMapper.toJPAEntity(inventory));

    return inventoryMapper.toDomain(save);
  }

  @Override
  public void update(InventoryCollection items) {
    inventoryJPARepository.saveAll(
        items.getInventory().stream().map(inventoryMapper::toJPAEntity).toList());
  }
}
