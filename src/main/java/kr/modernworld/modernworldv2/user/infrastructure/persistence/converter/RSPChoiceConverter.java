package kr.modernworld.modernworldv2.user.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.user.domain.rsp.RSPChoice;

@Converter(autoApply = true)
public class RSPChoiceConverter implements AttributeConverter<RSPChoice, String> {

  @Override
  public String convertToDatabaseColumn(RSPChoice attribute) {
    return attribute.getName();
  }

  @Override
  public RSPChoice convertToEntityAttribute(String dbData) {
    return RSPChoice.stringToRSPChoice(dbData);
  }
}
