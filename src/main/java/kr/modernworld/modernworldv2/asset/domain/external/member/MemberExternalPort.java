package kr.modernworld.modernworldv2.asset.domain.external.member;

public interface MemberExternalPort {

  void lockUserByUserNo(Long userNo);

  void validateUser(Long userNo);

  void decreaseCurrentPoint(Long userNo, Long amount);

  void increaseCurrentAccumulationPoint(Long userNo, Long amount);

  String getUserName(Long userNo);
}
