package kr.modernworld.modernworldv2.user.domain.present.port;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.present.dto.res.GetPresentResponseDTO;

public interface PresentQueryRepository {

  List<GetPresentResponseDTO> getPresents(Long userNo, SenderReceiverNoField type);

  GetPresentResponseDTO getOnePresent(Long presentNo);
}
