package me.whitebear.jpastudy.mybatis.mapper;

import me.whitebear.jpastudy.mybatis.vo.AccountMyBatisVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

  AccountMyBatisVO selectAccount(@Param("id") int id);

  void insertAccount(AccountMyBatisVO vo);
}
