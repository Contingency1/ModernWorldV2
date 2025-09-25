package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "character_locker", schema = "modernworld", indexes = {
    @Index(name = "character_no", columnList = "character_no"),
    @Index(name = "user_no", columnList = "user_no")
}, uniqueConstraints = {
    @UniqueConstraint(name = "character_locker_user_no_character_no_key", columnNames = {"user_no",
        "character_no"})
})
public class CharacterLockerJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "character_no", nullable = false)
  private CharacterJPAEntity characterNo;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_no", nullable = false)
  private UserJPAEntity userNo;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "status", nullable = false)
  private Boolean status = false;

}