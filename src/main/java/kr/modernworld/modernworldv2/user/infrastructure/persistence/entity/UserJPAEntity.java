package kr.modernworld.modernworldv2.user.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long no;

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
  @ColumnDefault("'https://ma7-production-s3-2.s3.ap-northeast-2.amazonaws.com/page/BaseProfileImage/pngwing.com.png'")
  @Column(name = "image", length = 300)
  private String image;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "domain", nullable = false)
  private UserDomain domain;

  @ColumnDefault("'10'")
  @Column(name = "chance", columnDefinition = "int UNSIGNED not null")
  private Long chance;

  // User의 생명주기와 완전히 동일한 관계
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<AlarmJPAEntity> alarms = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CharacterLockerJPAEntity> characterLockers = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<InventoryJPAEntity> inventories = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<RspGameRecordJPAEntity> rspGameRecords = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<UserAchievementJPAEntity> userAchievements = new ArrayList<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private LegendJPAEntity legend;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private TokenJPAEntity token;

  // User가 삭제되어도 다른 User는 남아있어야 하는 관계
  @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
  private List<CommentJPAEntity> sentComments = new ArrayList<>();

  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<CommentJPAEntity> receivedComments = new ArrayList<>();

  @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
  private List<LikeJPAEntity> sentLikes = new ArrayList<>();

  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<LikeJPAEntity> receivedLikes = new ArrayList<>();

  @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
  private List<NeighborJPAEntity> sentNeighbors = new ArrayList<>();

  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<NeighborJPAEntity> receivedNeighbors = new ArrayList<>();

  @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
  private List<PostJPAEntity> sentPosts = new ArrayList<>();

  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<PostJPAEntity> receivedPosts = new ArrayList<>();

  @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
  private List<PresentJPAEntity> sentPresents = new ArrayList<>();

  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<PresentJPAEntity> receivedPresents = new ArrayList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<ReplyJPAEntity> replies = new ArrayList<>();

  @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
  private List<ReportJPAEntity> sentReports = new ArrayList<>();

  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<ReportJPAEntity> receivedReports = new ArrayList<>();

}