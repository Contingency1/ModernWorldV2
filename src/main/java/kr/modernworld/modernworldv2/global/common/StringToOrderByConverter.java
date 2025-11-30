package kr.modernworld.modernworldv2.global.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOrderByConverter implements Converter<String, OrderBy> {

  @Override
  public OrderBy convert(String source) {
    return OrderBy.stringToOrderBy(source);
  }
}
