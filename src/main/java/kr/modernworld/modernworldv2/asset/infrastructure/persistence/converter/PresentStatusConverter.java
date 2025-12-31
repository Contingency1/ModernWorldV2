package kr.modernworld.modernworldv2.asset.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.asset.domain.present.PresentStatus;

@Converter(autoApply = true)
public class PresentStatusConverter implements AttributeConverter<PresentStatus, String> {

  @Override
  public String convertToDatabaseColumn(PresentStatus attribute) {
    if (attribute == null) {
      throw new NullPointerException("presentStatus must not be null");
    }

    return attribute.toString();
  }

  @Override
  public PresentStatus convertToEntityAttribute(String dbData) {
    return PresentStatus.stringToPresentStatus(dbData);
  }
}
