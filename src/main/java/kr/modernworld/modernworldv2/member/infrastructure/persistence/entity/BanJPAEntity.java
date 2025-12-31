package kr.modernworld.modernworldv2.member.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "ban", schema = "modernworld", uniqueConstraints = {
    @UniqueConstraint(name = "ban_unique_identifier_key", columnNames = {"unique_identifier"})
})
public class BanJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  private Long no;

  @Size(max = 300)
  @NotNull
  @Column(name = "unique_identifier", nullable = false, length = 300)
  private String uniqueIdentifier;

  @Size(max = 100)
  @NotNull
  @Column(name = "content", nullable = false, length = 100)
  private String content;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "expired_at")
  private Instant expiredAt;

}