package kr.modernworld.modernworldv2.admin.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "`character`", schema = "modernworld")
public class CharacterJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long no;

  @Size(max = 15)
  @NotNull
  @Column(name = "name", nullable = false, length = 15)
  private String name;

  @Size(max = 100)
  @NotNull
  @Column(name = "description", nullable = false, length = 100)
  private String description;

  @Size(max = 400)
  @NotNull
  @Column(name = "image", nullable = false, length = 400)
  private String image;

  @Size(max = 10)
  @NotNull
  @Column(name = "species", nullable = false, length = 10)
  private String species;

  @ColumnDefault("'0'")
  @Column(name = "price")
  private Long price;

  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CharacterLockerJPAEntity> characterLockers = new ArrayList<>();

}