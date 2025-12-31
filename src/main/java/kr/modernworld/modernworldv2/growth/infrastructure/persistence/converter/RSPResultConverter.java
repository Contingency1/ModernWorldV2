package kr.modernworld.modernworldv2.growth.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.growth.domain.rsp.GameResult;

@Converter(autoApply = true)
public class RSPResultConverter implements AttributeConverter<GameResult, String> {

  @Override
  public String convertToDatabaseColumn(GameResult attribute) {
    return attribute == null ? null : attribute.toString();
  }

  @Override
  public GameResult convertToEntityAttribute(String dbData) {
    return dbData == null ? null : GameResult.stringToGameResult(dbData);
  }
}
