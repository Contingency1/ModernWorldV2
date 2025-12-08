package kr.modernworld.modernworldv2.user.application;


import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.user.domain.Legend;
import kr.modernworld.modernworldv2.user.domain.port.legend.LegendQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.legend.LegendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LegendService {

  private final LegendRepository legendRepository;
  private final LegendQueryRepository legendQueryRepository;

  @Transactional
  public void create(Long userNo) {
    Optional<Legend> legend = legendQueryRepository.findLegendByUserNo(userNo);

    if (legend.isEmpty()) {
      legendRepository.save(Legend.init(userNo));
    }
  }

  @Transactional
  public Legend update(Long userNo, LegendField legendField) {
    Legend legend = legendRepository.findByUserNoForUpdate(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.LEGEND_NOT_FOUND));

    try {
      legend.incrementLegend(userNo, legendField);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.LEGEND_NOT_FOUND, e.getMessage());
    }

    return legendRepository.save(legend);
  }
}
