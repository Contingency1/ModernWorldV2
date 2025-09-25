package kr.modernworld.modernworldv2.global.persistence.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "token", schema = "modernworld", indexes = {
    @Index(name = "user_no", columnList = "user_no")
}, uniqueConstraints = {
    @UniqueConstraint(name = "token_user_no_key", columnNames = {"user_no"})
})
public class TokenJPAEntity {

  @Id
  @Column(name = "no", columnDefinition = "int UNSIGNED not null")
  private Long id;

  @NotNull
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_no", nullable = false)
  private UserJPAEntity userNo;

  @Size(max = 300)
  @Column(name = "social_access", length = 300)
  private String socialAccess;

  @Size(max = 300)
  @Column(name = "social_refresh", length = 300)
  private String socialRefresh;

}