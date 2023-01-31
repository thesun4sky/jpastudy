package me.whitebear.jpastudy.jdbc.template;

import me.whitebear.jpastudy.jdbc.vo.AccountVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository // Java 패키지에 있었다면 JdbcTemplate 생성자 주입받음
public class AccountTemplateDAO {

  private final JdbcTemplate jdbcTemplate;

  public AccountTemplateDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  //SQL 관련 명령어
  private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
      + "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
  // coalesce 은 Postgresql 용 IFNULL
  private final String ACCOUNT_GET = "SELECT * FROM account WHERE ID = ?";

  //CRUD 기능의 메소드 구현
  //계정 등록
  public Integer insertAccount(AccountVO vo) {
    // 기본 코드 (응답값은 생성된 갯수)
    // return jdbcTemplate.update(ACCOUNT_INSERT, vo.getUsername(), vo.getPassword());

    // id 값을 받아오기 위한 코드
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection
          .prepareStatement(ACCOUNT_INSERT, new String[]{"id"});
      ps.setString(1, vo.getUsername());
      ps.setString(2, vo.getPassword());
      return ps;
    }, keyHolder);

    return (Integer) keyHolder.getKey();
  }

  //계정 조회
  public AccountVO selectAccount(Integer id) {
    return jdbcTemplate.queryForObject(ACCOUNT_GET, new AccountRowMapper(), id);
  }


}
