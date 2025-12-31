package kr.modernworld.modernworldv2.global.config.webconverter;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CharacterSpeciesWebConverter implements Converter<String, CharacterSpecies> {

  @Override
  public CharacterSpecies convert(String source) {
    return CharacterSpecies.stringToCharacterSpecies(source);
  }
}
