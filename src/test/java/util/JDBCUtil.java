package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {

  public static Connection getConnection() {
    try {

      //ojdbc.jar파일에서 오라클드라이버 클래스 로딩
      //Class.forName("oracle.jdbc.driver.OracleDriver");
      //MySql
      // Class.forName("com.mysql.cj.jdbc.Driver"); 원래 driver 를 명시해줘야 하지만 이젠 자동으로 됨

      return DriverManager.getConnection("jdbc:postgresql://localhost:5432/messenger", "teasun",
          "pass");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void close(PreparedStatement stmt, Connection conn) {
    if (stmt != null) {
      try {
        if (!stmt.isClosed()) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        stmt = null;
      }
    }

    if (conn != null) {
      try {
        if (!conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        conn = null;
      }
    }
  }

  public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
    if (rs != null) {
      try {
        if (!rs.isClosed()) {
          rs.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        rs = null;
      }
    }

    if (stmt != null) {
      try {
        if (!stmt.isClosed()) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        stmt = null;
      }
    }

    if (conn != null) {
      try {
        if (!conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        conn = null;
      }
    }
  }
}
