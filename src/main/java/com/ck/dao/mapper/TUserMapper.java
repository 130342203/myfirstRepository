package com.ck.dao.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2018/6/11.
 */
public interface TUserMapper {
    @Select("select count(*) from t_user")
    int selectCountTest();
}
