package me.whitebear.jpastudy.user.repository;

import me.whitebear.jpastudy.group.Group;
import me.whitebear.jpastudy.group.GroupRepository;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 실무에서 사용시 주의해야합니다. (테스트 대상 환경에 영향을 줌)
@Rollback(value = false)
class UserRepositoryTest {

  private static final User TEST_USER = User.builder().username("new_user").password("pass")
      .build();
  private static final Group TEST_GROUP = Group.builder().name("new_group").build();

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Test
  void insertSelectUserTest() {
    // given

    // when
    var savedUser = userRepository.insertUser(TEST_USER);

    // then
    var foundUser = userRepository.selectUser(savedUser.getId());
    assert foundUser.getId().equals(savedUser.getId());
  }

  @Test
  void updateUserTest() {
    // given
    var savedUser = userRepository.insertUser(TEST_USER);

    // when
    savedUser.updateUserName("new_user_name");
    var updatedUser = userRepository.updateUser(savedUser);

    // then
    var foundUser = userRepository.selectUser(updatedUser.getId());
    assert foundUser.getUsername().equals(updatedUser.getUsername());
  }

  @Test
  void joinGroupTest() {
    // given
    var savedUser = userRepository.insertUser(TEST_USER);
    var newGroup = groupRepository.insertGroup(TEST_GROUP);

    // when
    savedUser.setGroup(newGroup);

    // then
    var savedGroup = groupRepository.selectGroup(newGroup.getId());
    assert savedGroup.getUsers().contains(savedUser);
  }
}