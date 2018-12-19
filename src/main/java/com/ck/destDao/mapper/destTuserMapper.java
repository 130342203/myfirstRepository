package com.ck.destDao.mapper;

import com.ck.dao.entity.TUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface destTuserMapper extends Mapper<TUser> {
    @Insert("insert into t_user (user_name) values (#{userName}) ")
    int insertTest(@Param("userName") String userName);
    @Select("select count(*) from t_user")
    int selectTest();
}
