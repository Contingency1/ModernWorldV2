package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
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
@Table(name = "post", schema = "modernworld", indexes = {
    @Index(name = "sender_no", columnList = "sender_no"),
    @Index(name = "receiver_no", columnList = "receiver_no")
})
public class PostJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  @JoinColumn(name = "sender_no")
  private UserJPAEntity senderNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  @JoinColumn(name = "receiver_no")
  private UserJPAEntity receiverNo;

  @Size(max = 150)
  @NotNull
  @Column(name = "content", nullable = false, length = 150)
  private String content;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "`check`", nullable = false)
  private Boolean check = false;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "sender_delete", nullable = false)
  private Boolean senderDelete = false;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "receiver_delete", nullable = false)
  private Boolean receiverDelete = false;

}