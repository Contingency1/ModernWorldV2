package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "achievement", schema = "modernworld")
public class AchievementJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

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
  @Lob
  @Column(name = "level", nullable = false)
  private String level;

  @Column(name = "point", columnDefinition = "int UNSIGNED not null")
  private Long point;

  @Size(max = 10)
  @NotNull
  @ColumnDefault("'기타'")
  @Column(name = "category", nullable = false, length = 10)
  private String category;

}