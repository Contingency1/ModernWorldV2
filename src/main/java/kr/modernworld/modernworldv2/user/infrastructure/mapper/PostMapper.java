package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.post.Post;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.PostJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mapping(target = "senderNo", source = "sender.no")
  @Mapping(target = "receiverNo", source = "receiver.no")
  Post toDomain(PostJPAEntity post);

  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  PostJPAEntity toEntity(Post post);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  void updateEntityFromDomain(Post post, @MappingTarget PostJPAEntity postJPAEntity);
}
