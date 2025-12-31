package kr.modernworld.modernworldv2.member.infrastructure.repository.user.socialtoken;

import kr.modernworld.modernworldv2.member.domain.user.UserSocialToken;
import kr.modernworld.modernworldv2.member.domain.user.socialtoken.port.SocialTokenRepository;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.TokenJPAEntity;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.SocialTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class SocialTokenRepositoryImpl implements SocialTokenRepository {

  private final SocialTokenJPARepository socialTokenJPARepository;
  private final SocialTokenMapper socialTokenMapper;

  @Override
  public UserSocialToken save(UserSocialToken token, Long userNo) {
    TokenJPAEntity entity = findByUserNo(userNo);

    if (entity == null) {
      entity = socialTokenMapper.toEntity(token);

      UserJPAEntity userRef = UserJPAEntity.builder()
          .no(userNo)
          .build();

      entity.setUser(userRef);
    } else {
      socialTokenMapper.updateEntityFromDomain(token, entity);
    }

    TokenJPAEntity saved = socialTokenJPARepository.save(entity);
    return socialTokenMapper.toDomain(saved);
  }

  private TokenJPAEntity findByUserNo(Long userNo) {
    return socialTokenJPARepository.findByUser_No(userNo);
  }

}
