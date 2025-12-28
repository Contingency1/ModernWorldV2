package kr.modernworld.modernworldv2.user.presentation.shop.character;

import java.util.List;
import kr.modernworld.modernworldv2.user.application.shop.CharacterShopService;
import kr.modernworld.modernworldv2.user.presentation.shop.character.dto.req.ShopCharacterRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.shop.character.dto.res.ShopCharacterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

  private final CharacterShopService characterShopService;

  @GetMapping("/{characterNo}")
  public ResponseEntity<ShopCharacterResponseDTO> getOne(@PathVariable Long characterNo) {
    ShopCharacterResponseDTO response = characterShopService.getCharacter(characterNo);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ShopCharacterResponseDTO>> getAll(ShopCharacterRequestDTO query) {
    List<ShopCharacterResponseDTO> response = characterShopService.getCharacters(query);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
