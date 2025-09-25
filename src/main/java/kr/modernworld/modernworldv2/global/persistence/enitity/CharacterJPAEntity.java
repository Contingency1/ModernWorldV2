package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "`character`", schema = "modernworld")
public class CharacterJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

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
  @Column(name = "price", columnDefinition = "int UNSIGNED not null")
  private Long price;

}