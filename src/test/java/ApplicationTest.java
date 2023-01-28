import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;


class ApplicationTest {

  @Test
  void jdbcTest() throws SQLException {
    String url = "jdbc:postgresql://localhost:5432/messenger";
    String username = "teasun";
    String password = "pass";

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      System.out.println("Connection created: " + connection);
      String sql = "CREATE TABLE ACCOUNT (id int, username varchar(255), password varchar(255))";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.execute();
      }
    }

    // docker exec -i -t postgres_boot bash
    // su - postgres
    // psql --username teasun --dbname messenger
    // \list (데이터 베이스 조회)
    // \dt (테이블 조회)

    // IntelliJ Database 에서도 조회
  }

}