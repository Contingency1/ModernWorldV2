package kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
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
@Table(name = "report", schema = "modernworld", indexes = {
    @Index(name = "sender_no", columnList = "sender_no"),
    @Index(name = "receiver_no", columnList = "receiver_no")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReportJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  private Long no;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  @JoinColumn(name = "sender_no")
  private UserJPAEntity sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  @JoinColumn(name = "receiver_no")
  private UserJPAEntity receiver;

  @Size(max = 300)
  @NotNull
  @Column(name = "content", nullable = false, length = 300)
  private String content;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @NotNull
  @ColumnDefault("'other'")
  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private ReportCategory category;

}