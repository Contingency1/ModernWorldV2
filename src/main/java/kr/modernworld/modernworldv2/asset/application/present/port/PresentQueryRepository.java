package kr.modernworld.modernworldv2.asset.application.present.port;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.present.dto.GetPresentDTO;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;

public interface PresentQueryRepository {

  List<GetPresentDTO> getPresents(Long userNo, SenderReceiverNoField type);

  GetPresentDTO getOnePresent(Long presentNo);
}
