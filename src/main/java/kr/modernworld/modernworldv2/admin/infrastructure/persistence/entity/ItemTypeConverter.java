package kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.admin.domain.ItemType;

@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, String> {

  @Override
  public String convertToDatabaseColumn(ItemType entityType) {
    if (entityType == null) {
      return null;
    }

    return entityType.getStr();
  }

  @Override
  public ItemType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return ItemType.strToItemType(dbData);
  }
}
