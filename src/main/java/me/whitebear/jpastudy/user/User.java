package me.whitebear.jpastudy.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import me.whitebear.jpastudy.group.Group;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString

// jpa
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  private String username;

  private String password;

  @Builder
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  @Exclude
  private Group group;

  public void setGroup(Group group) {
    this.group = group;
    group.addUser(this);
  }

  public void updateUserName(String username) {
    this.username = username;
  }

  public void updatePassword(String password) {
    this.password = password;
  }
}
