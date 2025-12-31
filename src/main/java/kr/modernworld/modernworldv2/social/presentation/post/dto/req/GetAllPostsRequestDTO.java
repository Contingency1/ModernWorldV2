package kr.modernworld.modernworldv2.social.presentation.post.dto.req;

import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;

public record GetAllPostsRequestDTO(
    SenderReceiverNoField senderReceiverNoField,
    OrderBy orderBy
) {

}
