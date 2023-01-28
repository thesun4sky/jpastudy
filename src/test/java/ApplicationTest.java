import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ApplicationTest {

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
      } catch (Exception e) {
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

      String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES (1, 'user1', 'pass1')";
      try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
        statement.execute();
      }

      // then
      String selectSql = "SELECT * FROM ACCOUNT";
      try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
        var result = statement.executeQuery();
        while (result.next()) {
          System.out.printf("%d, %s, %s%n", result.getInt(1), result.getString(2),
              result.getString(3));
        }
      }
    }
  }

}