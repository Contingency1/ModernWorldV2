package kr.modernworld.modernworldv2.asset.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.asset.domain.item.ItemType;

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

    return ItemType.stringToItemType(dbData);
  }
}
