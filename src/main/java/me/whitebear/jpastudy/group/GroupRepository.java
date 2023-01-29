package me.whitebear.jpastudy.group;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepository {

  @PersistenceContext
  EntityManager entityManager;


  public Group insertGroup(Group group) {
    entityManager.persist(group);
    return group;
  }

  public Group selectGroup(Long id) {
    return entityManager.find(Group.class, id);
  }
}
