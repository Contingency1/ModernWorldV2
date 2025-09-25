package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.Getter;
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
public class RspGameRecordJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_no", nullable = false)
  private UserJPAEntity userNo;

  @Size(max = 10)
  @NotNull
  @Column(name = "user_choice", nullable = false, length = 10)
  private String userChoice;

  @Size(max = 10)
  @NotNull
  @Column(name = "computer_choice", nullable = false, length = 10)
  private String computerChoice;

  @NotNull
  @Lob
  @Column(name = "result", nullable = false)
  private String result;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

}