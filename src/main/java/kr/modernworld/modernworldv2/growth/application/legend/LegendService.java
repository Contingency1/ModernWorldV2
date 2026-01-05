package kr.modernworld.modernworldv2.growth.application.legend;


import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.legend.port.LegendQueryRepository;
import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.growth.domain.legend.Legend;
import kr.modernworld.modernworldv2.growth.domain.legend.port.LegendRepository;
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
  public Legend increment(Long userNo, LegendField legendField) {
    Legend legend = legendRepository.findByUserNoForUpdate(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.LEGEND_NOT_FOUND));

    try {
      legend.incrementLegend(userNo, legendField);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.LEGEND_NOT_FOUND, e.getMessage());
    }

    return legendRepository.save(legend);
  }

  @Transactional
  public void decrement(Long userNo, LegendField legendField) {
    Legend legend = legendRepository.findByUserNoForUpdate(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.LEGEND_NOT_FOUND));

    try {
      legend.decrementLegend(userNo, legendField);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.LEGEND_NOT_FOUND, " reason: " + e.getMessage());
    }

    legendRepository.save(legend);
  }
}
