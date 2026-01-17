package kr.modernworld.modernworldv2.member.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;

@Converter(autoApply = true)
public class UserDomainConverter implements AttributeConverter<UserDomain, String> {

  @Override
  public String convertToDatabaseColumn(UserDomain attribute) {
    return attribute.getDomainName();
  }

  @Override
  public UserDomain convertToEntityAttribute(String dbData) {
    return UserDomain.strToUserDomain(dbData);
  }
}
