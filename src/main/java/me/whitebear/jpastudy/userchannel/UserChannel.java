package me.whitebear.jpastudy.userchannel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.user.User;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// JPA
@Entity
@Table(name = "user_channel")
public class UserChannel {

  /**
   * 컬럼
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * 생성자
   */
  @Builder
  public UserChannel(User user, Channel channel) {
    this.user = user;
    this.channel = channel;
  }

  /**
   * 연관관계
   */
  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne
  @JoinColumn(name = "channel_id")
  Channel channel;

  /**
   * 연관관계 편의 메소드
   */

  /**
   * 서비스 메소드
   */
}
