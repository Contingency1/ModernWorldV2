package kr.modernworld.modernworldv2.global.common.dto;

import java.util.List;

public record PageResponseDTO<T>(
    List<T> data,
    PageMetaDTO meta
) {

}
