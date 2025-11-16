package kr.modernworld.modernworldv2.admin.application.api;

import kr.modernworld.modernworldv2.admin.domain.port.character.CharacterQueryRepository;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterApiImpl implements CharacterApi {

  private final CharacterQueryRepository characterQueryRepository;

  @Override
  public Long getPrice(Long characterNo) {
    return characterQueryRepository.getPrice(characterNo)
        .orElseThrow(() -> new BusinessException(
            BusinessErrorCode.NO_SUCH_CHARACTER, " characterNo: " + characterNo));
  }
}
