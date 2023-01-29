package me.whitebear.jpastudy.userchannel;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.channel.ChannelRepository;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 실무에서 사용시 주의해야합니다. (테스트 대상 환경에 영향을 줌)
@Rollback(value = false)
class UserChannelRepositoryTest {

  @Autowired
  private UserChannelRepository userChannelRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  @DisplayName("채널에 유저가입 테스트")
  void userJoinChannelTest() {
    // given
    var TEST_USER = User.builder().username("new_user").password("pass")
        .build();
    var TEST_CHANNEL = Channel.builder().name("new_group").build();
    var savedUser = userRepository.insertUser(TEST_USER);
    var savedChannel = channelRepository.insertChannel(TEST_CHANNEL);

    // when
    var TEST_USER_CHANNEL = savedChannel.joinUser(savedUser);
    userChannelRepository.insertUserChannel(TEST_USER_CHANNEL);

    // then
    var foundUser = userRepository.selectUser(savedUser.getId());
    assert foundUser.getUserChannel().stream()
        .map(UserChannel::getChannel)
        .map(Channel::getName)
        .anyMatch(name -> name.equals(TEST_CHANNEL.getName()));
  }

  @Test
  @DisplayName("채널에 유저가입 테스트 (with Cascade)")
  void userJoinChannelWithCascadeTest() {
    // given
    var TEST_USER = User.builder().username("new_user").password("pass")
        .build();
    var TEST_CHANNEL = Channel.builder().name("new_group").build();
    var savedUser = userRepository.insertUser(TEST_USER);

    // when
    TEST_CHANNEL.joinUser(savedUser);
    channelRepository.insertChannel(TEST_CHANNEL);

    // then
    var foundUser = userRepository.selectUser(savedUser.getId());
    assert foundUser.getUserChannel().stream()
        .map(UserChannel::getChannel)
        .map(Channel::getName)
        .anyMatch(name -> name.equals(TEST_CHANNEL.getName()));
  }
}