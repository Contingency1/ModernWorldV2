package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.comment.Comment;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.CommentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "senderNo", source = "sender.no")
  @Mapping(target = "receiverNo", source = "receiver.no")
  Comment toDomain(CommentJPAEntity entity);

  @Mapping(target = "replies", ignore = true)
  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  CommentJPAEntity toEntity(Comment domain);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "replies", ignore = true)
  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  void updateEntityFromDomain(Comment domain, @MappingTarget CommentJPAEntity entity);
}
