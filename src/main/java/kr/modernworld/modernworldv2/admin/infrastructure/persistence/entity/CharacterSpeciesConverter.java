package kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.admin.domain.character.CharacterSpecies;

@Converter(autoApply = true)
public class CharacterSpeciesConverter implements AttributeConverter<CharacterSpecies, String> {

  @Override
  public String convertToDatabaseColumn(CharacterSpecies attribute) {
    if (attribute == null) {
      return null;
    }

    return attribute.getName();
  }

  @Override
  public CharacterSpecies convertToEntityAttribute(String dbData) {
    return CharacterSpecies.stringToCharacterSpecies(dbData);
  }


}
