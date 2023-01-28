package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jdbc.vo.AccountVO;
import util.JDBCUtil;

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
  public Integer insertAccount(AccountVO vo) {
    var id = -1;
    try {
      conn = JDBCUtil.getConnection();
      assert conn != null;
      stmt = conn.prepareStatement(ACCOUNT_INSERT);
      stmt.setString(1, vo.getUsername());
      stmt.setString(2, vo.getPassword());
      id = stmt.executeUpdate();
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
