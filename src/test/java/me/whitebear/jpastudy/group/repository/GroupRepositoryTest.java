package me.whitebear.jpastudy.group.repository;

import me.whitebear.jpastudy.group.Group;
import me.whitebear.jpastudy.group.GroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 실무에서 사용시 주의해야합니다. (테스트 대상 환경에 영향을 줌)
@Rollback(value = false)
class GroupRepositoryTest {

  @Autowired
  private GroupRepository groupRepository;

  @Test
  void insertSelectGroupTest() {
    // given
    var newGroup = Group.builder().name("new-group").build();

    // when
    var savedGroup = groupRepository.insertGroup(newGroup);

    // then
    var foundUser = groupRepository.selectGroup(savedGroup.getId());
    assert foundUser.getId().equals(savedGroup.getId());
  }
}