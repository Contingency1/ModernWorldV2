package kr.modernworld.modernworldv2.user.presentation.shop.item;

import java.util.List;
import kr.modernworld.modernworldv2.user.application.shop.ItemShopService;
import kr.modernworld.modernworldv2.user.presentation.shop.item.dto.req.ItemRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.shop.item.dto.res.ShopItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

  private final ItemShopService itemShopService;

  @GetMapping("/items/{itemNo}")
  public ResponseEntity<ShopItemResponseDTO> getOne(@PathVariable Long itemNo) {
    ShopItemResponseDTO response = itemShopService.getOne(itemNo);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/items")
  public ResponseEntity<List<ShopItemResponseDTO>> getAll(ItemRequestDTO query) {
    List<ShopItemResponseDTO> response = itemShopService.getAll(query);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
