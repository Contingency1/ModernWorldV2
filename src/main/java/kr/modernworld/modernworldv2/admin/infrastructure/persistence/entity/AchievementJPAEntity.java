package kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.achievement.AchievementLevel;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserAchievementJPAEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "achievement", schema = "modernworld")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AchievementJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  private Long no;

  @Size(max = 20)
  @NotNull
  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Size(max = 100)
  @NotNull
  @Column(name = "description", nullable = false, length = 100)
  private String description;

  @Size(max = 20)
  @NotNull
  @Column(name = "title", nullable = false, length = 20)
  private String title;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "level", nullable = false)
  private AchievementLevel level;

  @Column(name = "point")
  private Long point;

  @Size(max = 10)
  @NotNull
  @ColumnDefault("'기타'")
  @Column(name = "category", nullable = false, length = 10)
  private String category;

  @OneToMany(mappedBy = "achievement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<UserAchievementJPAEntity> userAchievements = new ArrayList<>();

}