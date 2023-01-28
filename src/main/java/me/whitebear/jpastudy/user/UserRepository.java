package me.whitebear.jpastudy.user;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  @PersistenceContext
  EntityManager entityManager;

  public User insertUser(User user) {
    entityManager.persist(user);
    return user;
  }

  public User updateUser(User user) {
    if (Objects.nonNull(user.getId())) {
      return entityManager.merge(user);
    } else {
      throw new EntityNotFoundException();
    }
  }

  public User selectUser(Long id) {
    return entityManager.find(User.class, id);
  }
}
