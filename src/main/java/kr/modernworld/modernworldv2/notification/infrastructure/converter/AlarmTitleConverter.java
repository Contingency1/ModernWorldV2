package kr.modernworld.modernworldv2.notification.infrastructure.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;

@Converter(autoApply = true)
public class AlarmTitleConverter implements AttributeConverter<AlarmTitle, String> {

  @Override
  public String convertToDatabaseColumn(AlarmTitle attribute) {
    return attribute.toString();
  }

  @Override
  public AlarmTitle convertToEntityAttribute(String dbData) {
    return AlarmTitle.stringToAlarmTitle(dbData);
  }
}
