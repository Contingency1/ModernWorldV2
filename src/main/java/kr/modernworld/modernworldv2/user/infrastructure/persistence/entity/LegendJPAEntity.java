package kr.modernworld.modernworldv2.user.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "legend", schema = "modernworld")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LegendJPAEntity {

  @Id
  @Column(name = "user_no")
  private Long no;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_no", nullable = false)
  private UserJPAEntity user;

  @ColumnDefault("'0'")
  @Column(name = "attendance_count")
  private Long attendanceCount;

  @ColumnDefault("'0'")
  @Column(name = "item_count")
  private Long itemCount;

  @ColumnDefault("'0'")
  @Column(name = "present_count")
  private Long presentCount;

  @ColumnDefault("'0'")
  @Column(name = "like_count")
  private Long likeCount;

  @ColumnDefault("'0'")
  @Column(name = "comment_count")
  private Long commentCount;

  @ColumnDefault("'0'")
  @Column(name = "RSP_win_count")
  private Long rspWinCount;

}