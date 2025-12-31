package kr.modernworld.modernworldv2.social.domain.like.port;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.social.presentation.like.dto.res.CreateLikeResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.like.dto.res.get.LikeResponseDTO;

public interface LikeQueryRepository {

  Boolean existsBySenderNoAndReceiverNo(Long senderNo, Long receiverNo);

  CreateLikeResponseDTO findByNo(Long likeNo);

  List<? extends LikeResponseDTO> findAllByUserNo(Long userNo, SenderReceiverNoField type);

}
