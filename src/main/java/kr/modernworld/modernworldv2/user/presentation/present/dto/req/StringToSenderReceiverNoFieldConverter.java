package kr.modernworld.modernworldv2.user.presentation.present.dto.req;

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
