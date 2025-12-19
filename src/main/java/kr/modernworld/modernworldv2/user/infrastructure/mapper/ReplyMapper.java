package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.reply.Reply;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.ReplyJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

  @Mapping(target = "userNo", source = "user.no")
  @Mapping(target = "commentNo", source = "comment.no")
  Reply toDomain(ReplyJPAEntity reply);

  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "comment.no", source = "commentNo")
  ReplyJPAEntity toEntity(Reply reply);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "comment", ignore = true)
  void updateEntityFromDomain(Reply reply, @MappingTarget ReplyJPAEntity replyJPAEntity);
}
