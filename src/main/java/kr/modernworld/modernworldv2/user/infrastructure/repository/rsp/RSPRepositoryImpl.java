package kr.modernworld.modernworldv2.user.infrastructure.repository.rsp;

import kr.modernworld.modernworldv2.user.domain.rsp.RSP;
import kr.modernworld.modernworldv2.user.domain.rsp.port.RSPRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.RSPMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.RspGameRecordJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RSPRepositoryImpl implements RSPRepository {

  private final RSPMapper rspMapper;
  private final RSPJPARepository rspJPARepository;

  @Override
  public RSP save(RSP rsp) {
    if (rsp.getNo() == null) {
      RspGameRecordJPAEntity save = rspJPARepository.save(rspMapper.toEntity(rsp));

      return rspMapper.toDomain(save);
    }

    RspGameRecordJPAEntity rspRecord = rspJPARepository.findById(rsp.getNo())
        .orElseThrow(() -> new IllegalArgumentException("No such RSP"));

    rspMapper.updateEntityFromDomain(rsp, rspRecord);

    return rspMapper.toDomain(rspRecord);
  }
}
