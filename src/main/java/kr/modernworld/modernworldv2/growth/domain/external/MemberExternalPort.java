package kr.modernworld.modernworldv2.growth.domain.external;

public interface MemberExternalPort {

  void processGameResult(Long userNo, Long point);

  void increaseCurrentAccumulationPoint(Long userNo, Long amount);

}