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
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.InventoryJPAEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "item", schema = "modernworld")
public class ItemJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long no;

  @Size(max = 15)
  @NotNull
  @Column(name = "name", nullable = false, length = 15)
  private String name;

  @Size(max = 150)
  @NotNull
  @Column(name = "description", nullable = false, length = 150)
  private String description;

  @Size(max = 400)
  @NotNull
  @Column(name = "image", nullable = false, length = 400)
  private String image;

  @Size(max = 10)
  @NotNull
  @Column(name = "theme", nullable = false, length = 10)
  private String theme;

  @Size(max = 20)
  @NotNull
  @Column(name = "type", nullable = false, length = 20)
  private String type;

  @ColumnDefault("'0'")
  @Column(name = "price", columnDefinition = "int UNSIGNED not null")
  private Long price;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<InventoryJPAEntity> inventories = new ArrayList<>();
}