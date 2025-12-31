package kr.modernworld.modernworldv2.asset.presentation.shop.item;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemApiDTO;
import kr.modernworld.modernworldv2.asset.application.shop.ItemShopService;
import kr.modernworld.modernworldv2.asset.presentation.shop.item.dto.req.ItemRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.shop.item.dto.res.ShopItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

  private final ItemShopService itemShopService;

  @GetMapping("/{itemNo}")
  public ResponseEntity<ShopItemResponseDTO> getOne(@PathVariable Long itemNo) {
    ItemApiDTO response = itemShopService.getOne(itemNo);

    return new ResponseEntity<>(ShopItemResponseDTO.from(response), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ShopItemResponseDTO>> getAll(ItemRequestDTO query) {
    List<ItemApiDTO> response = itemShopService.getAll(query);

    return new ResponseEntity<>(
        response.stream()
            .map(ShopItemResponseDTO::from)
            .toList(),
        HttpStatus.OK);
  }

}
