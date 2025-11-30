package kr.modernworld.modernworldv2.user.application;


import java.util.Optional;
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
}
