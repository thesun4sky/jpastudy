package me.whitebear.jpastudy.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.whitebear.jpastudy.jdbc.vo.AccountVO;
import me.whitebear.jpastudy.util.JDBCUtil;

public class AccountDAO {

  //JDBC 관련 변수
  private Connection conn = null;
  private PreparedStatement stmt = null;
  private ResultSet rs = null;

  //SQL 관련 명령어
  private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
      + "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
  // coalesce 은 Postgresql 용 IFNULL
  private final String ACCOUNT_GET = "SELECT * FROM account WHERE ID = ?";

  //CRUD 기능의 메소드 구현
  //계정 등록
  public Integer insertAccount(AccountVO vo) throws SQLException {
    var id = -1;
    String[] returnId = {"id"};
    try {
      conn = JDBCUtil.getConnection();
      assert conn != null;
      stmt = conn.prepareStatement(ACCOUNT_INSERT, returnId);
      stmt.setString(1, vo.getUsername());
      stmt.setString(2, vo.getPassword());
      stmt.executeUpdate();

      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          id = rs.getInt(1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtil.close(stmt, conn);
    }

    return id;
  }

  //계정 조회
  public AccountVO selectAccount(Integer id) {
    AccountVO account = null;
    try {
      conn = JDBCUtil.getConnection();
      assert conn != null;
      stmt = conn.prepareStatement(ACCOUNT_GET);
      stmt.setInt(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        account = new AccountVO();
        account.setId(rs.getInt("ID"));
        account.setUsername(rs.getString("USERNAME"));
        account.setPassword(rs.getString("PASSWORD"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtil.close(rs, stmt, conn);
    }

    return account;
  }


}
