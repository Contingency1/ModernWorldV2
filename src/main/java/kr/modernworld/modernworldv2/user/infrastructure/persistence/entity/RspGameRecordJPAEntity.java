package kr.modernworld.modernworldv2.user.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
import kr.modernworld.modernworldv2.user.domain.rsp.GameResult;
import kr.modernworld.modernworldv2.user.domain.rsp.RSPChoice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "rsp_game_record", schema = "modernworld", indexes = {
    @Index(name = "user_no", columnList = "user_no")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RspGameRecordJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  private Long no;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_no", nullable = false)
  private UserJPAEntity user;

  @NotNull
  @Column(name = "user_choice", nullable = false, length = 10)
  private RSPChoice userChoice;

  @NotNull
  @Column(name = "computer_choice", nullable = false, length = 10)
  private RSPChoice computerChoice;

  @NotNull
  @Column(name = "result", nullable = false)
  private GameResult result;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

}