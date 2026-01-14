package kr.modernworld.modernworldv2.asset.presentation.characterlocker;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.characterlocker.CharacterLockerService;
import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.GetCharacterLockerDTO;
import kr.modernworld.modernworldv2.asset.application.shop.CharacterShopService;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.req.CharacterNoRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.req.GetCharacterRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res.CharacterLockerResponseDTO;
import kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res.GetCharacterLockerResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    List<GetCharacterLockerDTO> response = characterLockerService.getUserCharacters(
        userNo, characterInfo.status(), characterInfo.toCharacterSpecies());

    return new ResponseEntity<>(
        response.stream().map(GetCharacterLockerResponseDTO::from).toList(),
        HttpStatus.OK);
  }

  @PostMapping("/my/characters")
  public ResponseEntity<CharacterLockerResponseDTO> buyOneUserCharacter(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @RequestBody CharacterNoRequestDTO characterInfo) {
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
