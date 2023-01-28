package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jdbc.dao.AccountDAO;
import jdbc.vo.AccountVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class jdbcTest {

  @Test
  @DisplayName("JDBC DB 연결 실습")
  void jdbcTest() throws SQLException {
    // given
    String url = "jdbc:postgresql://localhost:5432/messenger";
    String username = "teasun";
    String password = "pass";

    // when
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      try {
        String creatSql = "CREATE TABLE ACCOUNT (id int, username varchar(255), password varchar(255))";
        try (PreparedStatement statement = connection.prepareStatement(creatSql)) {
          statement.execute();
        }
      } catch (SQLException e) {
        if (e.getMessage().equals("ERROR: relation \"account\" already exists")) {
          System.out.println("ACCOUNT 테이블이 이미 존재합니다.");
        } else {
          throw new RuntimeException();
        }
      }
    }

    // then
    // docker exec -i -t postgres_boot bash
    // su - postgres
    // psql --username teasun --dbname messenger
    // \list (데이터 베이스 조회)
    // \dt (테이블 조회)

    // IntelliJ Database 에서도 조회
  }

  @Test
  @DisplayName("JDBC 삽입/조회 실습")
  void jdbcInsertSelectTest() throws SQLException {
    // given
    String url = "jdbc:postgresql://localhost:5432/messenger";
    String username = "teasun";
    String password = "pass";

    // when
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      System.out.println("Connection created: " + connection);

      String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), 'user1', 'pass1')";
      try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
        statement.execute();
      }

      // then
      String selectSql = "SELECT * FROM ACCOUNT";
      try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
        var rs = statement.executeQuery();
        while (rs.next()) {
          System.out.printf("%d, %s, %s", rs.getInt("id"), rs.getString("username"),
              rs.getString("password"));
        }
      }
    }
  }


  @Test
  @DisplayName("JDBC DAO 삽입/조회 실습")
  void jdbcDAOInsertSelectTest() {
    // given
    AccountDAO accountDAO = new AccountDAO();

    // when
    var id = accountDAO.insertAccount(new AccountVO("new user", "new password"));

    // then
    var account = accountDAO.selectAccount(id);
    assert account.getUsername().equals("new user");
  }


  @Test
  @DisplayName("SQL Mapper - JDBC Template 실습")
  void sqlMapper_JDBCTemplateTest() throws SQLException {
    // given
    String url = "jdbc:postgresql://localhost:5432/messenger";
    String username = "teasun";
    String password = "pass";

    // when
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      System.out.println("Connection created: " + connection);

      // then
      String selectSql = "SELECT * FROM ACCOUNT";
      try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
        var rs = statement.executeQuery();
        while (rs.next()) {

        }
      }
    }
  }
}
