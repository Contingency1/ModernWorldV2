package kr.modernworld.modernworldv2.user.domain;

public class CharacterLocker {

  Long no;

  Long userNo;

  Long characterNo;

  Boolean status;

  public void equip() {
    this.status = true;
  }

  public void unequip() {
    this.status = false;
  }

  public CharacterLocker(Long no, Long userNo, Long characterNo, Boolean status) {
    this.no = no;
    this.userNo = userNo;
    this.characterNo = characterNo;
    this.status = status;
  }
}
