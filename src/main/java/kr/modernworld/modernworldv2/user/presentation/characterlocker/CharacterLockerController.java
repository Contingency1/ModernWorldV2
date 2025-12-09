package kr.modernworld.modernworldv2.user.presentation.characterlocker;

import java.util.List;
import kr.modernworld.modernworldv2.user.application.characterlocker.CharacterLockerService;
import kr.modernworld.modernworldv2.user.application.shop.CharacterShopService;
import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.user.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.req.CharacterNoRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.req.GetCharacterRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.res.CharacterLockerResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.res.GetCharacterLockerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CharacterLockerController {

  private final CharacterLockerService characterLockerService;
  private final CharacterShopService characterShopService;

  @GetMapping("/{userNo}/characters")
  public ResponseEntity<List<GetCharacterLockerResponseDTO>> getUserCharacters(
      @PathVariable Long userNo,
      GetCharacterRequestDTO characterInfo) {
    List<GetCharacterLockerResponseDTO> response = characterLockerService.getUserCharacters(
        userNo, characterInfo.status(), characterInfo.species());

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @PostMapping("/my/characters")
  public ResponseEntity<CharacterLockerResponseDTO> buyOneUserCharacter(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      CharacterNoRequestDTO characterInfo) {
    CharacterLocker characterLocker = characterShopService.buyOneCharacter(user.userNo(),
        characterInfo.characterNo());

    CharacterLockerResponseDTO response = new CharacterLockerResponseDTO(characterLocker.getNo(),
        characterLocker.getCharacterNo(), characterLocker.getUserNo(), characterLocker.getStatus());

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PatchMapping("/my/characters/{characterNo}")
  public ResponseEntity<CharacterLockerResponseDTO> equip(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long characterNo) {
    CharacterLocker characterLocker = characterLockerService.equip(user.userNo(), characterNo);

    CharacterLockerResponseDTO response = new CharacterLockerResponseDTO(characterLocker.getNo(),
        characterLocker.getCharacterNo(), characterLocker.getUserNo(), characterLocker.getStatus());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
