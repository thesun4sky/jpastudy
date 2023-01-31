package me.whitebear.jpastudy.jdbc.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.whitebear.jpastudy.jdbc.vo.AccountVO;
import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper implements RowMapper<AccountVO> {

  @Override
  public AccountVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    var account = new AccountVO();
    account.setId(rs.getInt("ID"));
    account.setUsername(rs.getString("USERNAME"));
    account.setPassword(rs.getString("PASSWORD"));
    return account;
  }
}
