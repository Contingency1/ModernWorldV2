package kr.modernworld.modernworldv2.global.common.dto;

public record PageMetaDTO(
    Long page,
    Long take,
    Long totalCount,
    Long totalPage
) {

}