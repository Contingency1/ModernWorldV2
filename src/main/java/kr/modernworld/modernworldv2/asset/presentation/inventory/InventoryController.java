package kr.modernworld.modernworldv2.asset.presentation.inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import kr.modernworld.modernworldv2.asset.application.inventory.InventoryService;
import kr.modernworld.modernworldv2.asset.application.shop.ItemShopService;
import kr.modernworld.modernworldv2.asset.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.asset.presentation.inventory.dto.request.BuyOneItemRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.inventory.dto.request.GetInventoryRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.inventory.dto.request.UpdateInventoryRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.inventory.dto.response.GetInventoryResponseDTO;
import kr.modernworld.modernworldv2.asset.presentation.inventory.dto.response.InventoryResponseDTO;
import kr.modernworld.modernworldv2.user.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class InventoryController {

  private final InventoryService inventoryService;
  private final ItemShopService itemShopService;

  @GetMapping("/users/{userNo}/items")
  public ResponseEntity<List<GetInventoryResponseDTO>> getInventory(
      @PathVariable("userNo") @Min(1) Long userNo,
      GetInventoryRequestDTO query
  ) {
    List<GetInventoryResponseDTO> response = inventoryService.getAllItems(userNo, query);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/users/my/items")
  public ResponseEntity<InventoryResponseDTO> createOneItemInInventory(
      @AuthenticationPrincipal TokenUserInfoDTO user, @Valid BuyOneItemRequestDTO item
  ) {
    Inventory inventory = itemShopService.buyOneItem(user.userNo(), item.itemNo());

    InventoryResponseDTO response = new InventoryResponseDTO(inventory.getNo(),
        inventory.getUserNo(), inventory.getItemNo(), inventory.getCreatedAt(),
        inventory.getStatus());

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PatchMapping("/users/my/items/{itemNo}")
  public ResponseEntity<InventoryResponseDTO> updateOneItemInInventory(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable("itemNo") @Min(1) Long itemNo,
      @Valid UpdateInventoryRequestDTO item
  ) {
    Inventory inventory = inventoryService.updateItemEquipStatus(user.userNo(), itemNo,
        item.status());

    InventoryResponseDTO response = new InventoryResponseDTO(inventory.getNo(),
        inventory.getUserNo(), inventory.getItemNo(), inventory.getCreatedAt(),
        inventory.getStatus());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
