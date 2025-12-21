package kr.modernworld.modernworldv2.user.infrastructure.repository.like;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QLikeJPAEntity.likeJPAEntity;
import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.domain.like.port.LikeQueryRepository;
import kr.modernworld.modernworldv2.user.presentation.like.dto.res.CreateLikeResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.like.dto.res.CreateLikeUserInfoDTO;
import kr.modernworld.modernworldv2.user.presentation.like.dto.res.get.GetLikeUserInfoDTO;
import kr.modernworld.modernworldv2.user.presentation.like.dto.res.get.GivenLikeResponse;
import kr.modernworld.modernworldv2.user.presentation.like.dto.res.get.LikeResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.like.dto.res.get.ReceivedLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeQueryRepositoryImpl implements LikeQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Boolean existsBySenderNoAndReceiverNo(Long senderNo, Long receiverNo) {
    Integer exist = queryFactory
        .selectOne()
        .from(likeJPAEntity)
        .where(
            likeJPAEntity.sender.no.eq(senderNo),
            likeJPAEntity.receiver.no.eq(receiverNo))
        .fetchFirst();

    return exist != null;
  }

  @Override
  public CreateLikeResponseDTO findByNo(Long likeNo) {
    return queryFactory
        .select(Projections.constructor(
            CreateLikeResponseDTO.class,
            likeJPAEntity.no,
            Projections.constructor(
                CreateLikeUserInfoDTO.class,
                likeJPAEntity.sender.no,
                likeJPAEntity.sender.nickname),
            Projections.constructor(
                CreateLikeUserInfoDTO.class,
                likeJPAEntity.receiver.no,
                likeJPAEntity.receiver.nickname
            )
        ))
        .from(likeJPAEntity)
        .where(likeJPAEntity.no.eq(likeNo))
        .fetchFirst();
  }

  @Override
  public List<? extends LikeResponseDTO> findAllByUserNo(Long userNo, SenderReceiverNoField type) {

    if (type.equals(SenderReceiverNoField.RECEIVER_NO)) {
      return queryFactory
          .select(Projections.constructor(ReceivedLikeResponse.class,
              likeJPAEntity.no,
              Projections.constructor(GetLikeUserInfoDTO.class,
                  likeJPAEntity.sender.no,
                  likeJPAEntity.sender.nickname,
                  likeJPAEntity.sender.image
              )
          ))
          .from(likeJPAEntity)
          .innerJoin(likeJPAEntity.sender, userJPAEntity)
          .where(likeJPAEntity.receiver.no.eq(userNo))
          .orderBy(likeJPAEntity.no.desc())
          .fetch();
    }

    return queryFactory
        .select(Projections.constructor(GivenLikeResponse.class,
            likeJPAEntity.no,
            Projections.constructor(GetLikeUserInfoDTO.class,
                likeJPAEntity.receiver.no,
                likeJPAEntity.receiver.nickname,
                likeJPAEntity.receiver.image
            )
        ))
        .from(likeJPAEntity)
        .innerJoin(likeJPAEntity.receiver, userJPAEntity)
        .where(likeJPAEntity.sender.no.eq(userNo))
        .orderBy(likeJPAEntity.no.desc())
        .fetch();
  }
}
