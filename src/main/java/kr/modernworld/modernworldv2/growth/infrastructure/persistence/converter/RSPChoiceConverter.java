package kr.modernworld.modernworldv2.growth.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.growth.domain.rsp.RSPChoice;

@Converter(autoApply = true)
public class RSPChoiceConverter implements AttributeConverter<RSPChoice, String> {

  @Override
  public String convertToDatabaseColumn(RSPChoice attribute) {
    return attribute == null ? null : attribute.getName();
  }

  @Override
  public RSPChoice convertToEntityAttribute(String dbData) {
    return dbData == null ? null : RSPChoice.stringToRSPChoice(dbData);
  }
}
