package kr.modernworld.modernworldv2.user.presentation;

import java.util.List;
import kr.modernworld.modernworldv2.user.application.CharacterLockerService;
import kr.modernworld.modernworldv2.user.presentation.dto.req.CharacterNoRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.dto.req.GetCharacterRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.dto.res.CharacterLockerResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.dto.res.GetCharacterLockerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/{userNo}/characters")
  public ResponseEntity<List<GetCharacterLockerResponseDTO>> getUserCharacters(
      @PathVariable Long userNo,
      GetCharacterRequestDTO characterInfo) {
    List<GetCharacterLockerResponseDTO> response = characterLockerService.getUserCharacters(
        userNo, characterInfo.status(),
        characterInfo.species());

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @PostMapping("/my/characters")
  public ResponseEntity<CharacterLockerResponseDTO> createOneUserCharacter(
      CharacterNoRequestDTO characterInfo) {
    return null;
  }

  @PatchMapping("/my/characters/{characterNo}")
  public ResponseEntity<CharacterLockerResponseDTO> equip(
      @PathVariable Long characterNo) {

    return null;
  }

}
