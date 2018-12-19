package com.ck.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.ck.dao.entity.TUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */

public interface TUserMapper extends Mapper<TUser> {

    @Select("select count(*) from t_user")
    int selectCountTest();

    @Select("select *from t_user limit 1")
    TUser selectOneTest();


    TUser selectXmlTest(Map map);
}
