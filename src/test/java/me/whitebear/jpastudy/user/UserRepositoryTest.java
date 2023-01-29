package me.whitebear.jpastudy.user;

import me.whitebear.jpastudy.channel.ChannelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 실무에서 사용시 주의해야합니다. (테스트 대상 환경에 영향을 줌)
@Rollback(value = false)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  void insertSelectUserTest() {
    // given
    var TEST_USER = User.builder().username("new_user").password("pass")
        .build();

    // when
    var savedUser = userRepository.insertUser(TEST_USER);

    // then
    var foundUser = userRepository.selectUser(savedUser.getId());
    assert foundUser.getId().equals(savedUser.getId());
  }

  @Test
  void updateUserTest() {
    // given
    var TEST_USER = User.builder().username("new_user").password("pass")
        .build();
    var savedUser = userRepository.insertUser(TEST_USER);

    // when
    savedUser.updateUserName("new_user_name");
    var updatedUser = userRepository.updateUser(savedUser);

    // then
    var foundUser = userRepository.selectUser(updatedUser.getId());
    assert foundUser.getUsername().equals(updatedUser.getUsername());
  }

}