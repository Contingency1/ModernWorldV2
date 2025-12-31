package kr.modernworld.modernworldv2.asset.infrastructure.repository.present;


import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QItemJPAEntity.itemJPAEntity;
import static kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.QPresentJPAEntity.presentJPAEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.modernworld.modernworldv2.asset.domain.present.port.PresentQueryRepository;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.res.GetPresentResponseDTO;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.res.PresentItemDTO;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.res.PresentUserDTO;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PresentQueryRepositoryImpl implements PresentQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<GetPresentResponseDTO> getPresents(Long userNo, SenderReceiverNoField type) {
    QUserJPAEntity sender = new QUserJPAEntity("sender");
    QUserJPAEntity receiver = new QUserJPAEntity("receiver");

    return queryFactory
        .select(presentSelect(sender, receiver))
        .from(presentJPAEntity)
        .join(presentJPAEntity.item, itemJPAEntity)
        .join(presentJPAEntity.sender, sender)
        .join(presentJPAEntity.receiver, receiver)
        .where(filterPresents(userNo, type))
        .orderBy(presentJPAEntity.createdAt.desc())
        .fetch();
  }

  @Override
  public GetPresentResponseDTO getOnePresent(Long presentNo) {
    QUserJPAEntity sender = new QUserJPAEntity("sender");
    QUserJPAEntity receiver = new QUserJPAEntity("receiver");

    return queryFactory
        .select(presentSelect(sender, receiver))
        .from(presentJPAEntity)
        .join(presentJPAEntity.item, itemJPAEntity)
        .join(presentJPAEntity.sender, sender)
        .join(presentJPAEntity.receiver, receiver)
        .where(presentJPAEntity.no.eq(presentNo))
        .fetchOne();

  }

  private static ConstructorExpression<GetPresentResponseDTO> presentSelect(QUserJPAEntity sender,
      QUserJPAEntity receiver) {
    return Projections.constructor(GetPresentResponseDTO.class,
        presentJPAEntity.no,
        presentJPAEntity.status,
        presentJPAEntity.createdAt,
        Projections.constructor(PresentItemDTO.class,
            itemJPAEntity.name,
            itemJPAEntity.image,
            itemJPAEntity.description),
        Projections.constructor(PresentUserDTO.class,
            sender.no,
            sender.nickname
        ),
        Projections.constructor(PresentUserDTO.class,
            receiver.no,
            receiver.nickname
        )
    );
  }

  private BooleanExpression filterPresents(Long userNo, SenderReceiverNoField type) {
    BooleanExpression isSender = presentJPAEntity.sender.no.eq(userNo)
        .and(presentJPAEntity.senderDelete.isFalse());

    BooleanExpression isReceiver = presentJPAEntity.receiver.no.eq(userNo)
        .and(presentJPAEntity.receiverDelete.isFalse());

    if (type == SenderReceiverNoField.SENDER_NO) {
      return isSender;
    }

    if (type == SenderReceiverNoField.RECEIVER_NO) {
      return isReceiver;
    }

    return isSender.or(isReceiver);
  }
}
