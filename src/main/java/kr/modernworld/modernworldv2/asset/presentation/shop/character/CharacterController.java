package kr.modernworld.modernworldv2.asset.presentation.shop.character;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.character.dto.CharacterApiDTO;
import kr.modernworld.modernworldv2.asset.application.shop.CharacterShopService;
import kr.modernworld.modernworldv2.asset.presentation.shop.character.dto.req.ShopCharacterRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.shop.character.dto.res.ShopCharacterResponseDTO;
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
    CharacterApiDTO response = characterShopService.getCharacter(characterNo);

    return new ResponseEntity<>(ShopCharacterResponseDTO.from(response), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ShopCharacterResponseDTO>> getAll(ShopCharacterRequestDTO query) {
    List<CharacterApiDTO> response = characterShopService.getCharacters(query);

    return new ResponseEntity<>(
        response.stream().map(ShopCharacterResponseDTO::from).toList(),
        HttpStatus.OK);
  }

}
