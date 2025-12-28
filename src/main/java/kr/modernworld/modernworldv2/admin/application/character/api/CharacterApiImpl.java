package kr.modernworld.modernworldv2.admin.application.character.api;

import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.admin.domain.character.port.CharacterApi;
import kr.modernworld.modernworldv2.admin.domain.character.port.CharacterQueryRepository;
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

  @Override
  public CharacterApiDTO getOne(Long characterNo) {
    return characterQueryRepository.findOne(characterNo)
        .orElseThrow(
            () -> new BusinessException(BusinessErrorCode.CHARACTER_NOT_FOUND,
                " characterNo: " + characterNo));
  }

  @Override
  public List<CharacterApiDTO> getAll(CharacterSpecies species, String characterName) {
    return characterQueryRepository.findAll(species, characterName);
  }
}
