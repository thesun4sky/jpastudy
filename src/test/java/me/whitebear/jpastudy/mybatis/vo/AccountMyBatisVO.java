package me.whitebear.jpastudy.mybatis.vo;


public class AccountMyBatisVO {

  private Integer id;

  private String username;

  private String password;


  public AccountMyBatisVO() {
  }

  public AccountMyBatisVO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
