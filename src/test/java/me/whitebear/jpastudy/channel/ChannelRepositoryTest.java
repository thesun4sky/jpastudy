package me.whitebear.jpastudy.channel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 실무에서 사용시 주의해야합니다. (테스트 대상 환경에 영향을 줌)
@Rollback(value = false)
class ChannelRepositoryTest {

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  void insertSelectGroupTest() {
    // given
    var newChannel = Channel.builder().name("new-group").build();

    // when
    var savedChannel = channelRepository.insertChannel(newChannel);

    // then
    var foundUser = channelRepository.selectChannel(savedChannel.getId());
    assert foundUser.getId().equals(savedChannel.getId());
  }
}