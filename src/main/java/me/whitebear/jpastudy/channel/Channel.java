package me.whitebear.jpastudy.channel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.userchannel.UserChannel;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// JPA
@Entity
@Table(name = "channel")
public class Channel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(length = 30)
  private String name;

  @Builder
  public Channel(String name) {
    this.name = name;
  }

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */
  public UserChannel joinUser(User user) {
    var userChannel = UserChannel.builder().user(user).channel(this).build();
    this.userChannels.add(userChannel);
    user.getUserChannel().add(userChannel);
    return userChannel;
  }

  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @OneToMany(mappedBy = "channel")
  @Exclude
  private Set<UserChannel> userChannels = new LinkedHashSet<>();

}
