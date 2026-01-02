package kr.modernworld.modernworldv2.asset.application.present.port;

import java.util.List;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.res.GetPresentResponseDTO;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;

public interface PresentQueryRepository {

  List<GetPresentResponseDTO> getPresents(Long userNo, SenderReceiverNoField type);

  GetPresentResponseDTO getOnePresent(Long presentNo);
}
