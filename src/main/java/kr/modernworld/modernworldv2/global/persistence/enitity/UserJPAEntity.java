package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "modernworld", uniqueConstraints = {
    @UniqueConstraint(name = "nickname", columnNames = {"nickname"}),
    @UniqueConstraint(name = "unique_identifier", columnNames = {"unique_identifier"})
})
public class UserJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

  @Size(max = 10)
  @Column(name = "nickname", length = 10)
  private String nickname;

  @ColumnDefault("'0'")
  @Column(name = "current_point", columnDefinition = "int UNSIGNED not null")
  private Long currentPoint;

  @ColumnDefault("'0'")
  @Column(name = "accumulation_point", columnDefinition = "int UNSIGNED not null")
  private Long accumulationPoint;

  @Size(max = 150)
  @Column(name = "description", length = 150)
  private String description;

  @Column(name = "attendance")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> attendance;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "status", nullable = false)
  private Boolean status = false;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "admin", nullable = false)
  private Boolean admin = false;

  @Size(max = 300)
  @NotNull
  @Column(name = "unique_identifier", nullable = false, length = 300)
  private String uniqueIdentifier;

  @Size(max = 200)
  @NotNull
  @Column(name = "social_name", nullable = false, length = 200)
  private String socialName;

  @Size(max = 300)
  @ColumnDefault("'https://wang0514.s3.ap-northeast-2.amazonaws.com/page/BaseProfileImage/pngwing.com.png'")
  @Column(name = "image", length = 300)
  private String image;

  @NotNull
  @Lob
  @Column(name = "domain", nullable = false)
  private String domain;

  @ColumnDefault("'10'")
  @Column(name = "chance", columnDefinition = "int UNSIGNED not null")
  private Long chance;

}