package me.whitebear.jpastudy.registrar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(MyRepositoryRegistrar.class)
@SpringBootTest
public class TestApplication {

  @Autowired
  MyRepository myRepository;

  @Test
  void registrarTest() {
    System.out.println(myRepository.getValue());
  }
}
