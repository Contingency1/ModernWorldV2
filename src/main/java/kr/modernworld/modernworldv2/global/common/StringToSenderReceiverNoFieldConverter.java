package kr.modernworld.modernworldv2.global.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSenderReceiverNoFieldConverter implements
    Converter<String, SenderReceiverNoField> {

  @Override
  public SenderReceiverNoField convert(String source) {
    return SenderReceiverNoField.stringToSenderReceiverNoField(source);
  }
}
