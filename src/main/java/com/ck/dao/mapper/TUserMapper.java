package com.ck.dao.mapper;

import org.apache.ibatis.annotations.Select;
import com.ck.dao.entity.tuser;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2018/6/11.
 */
public interface TUserMapper extends Mapper<tuser> {
    @Select("select count(*) from t_user")
    int selectCountTest();

    @Select("select *from t_user limit 1")
    tuser selectOneTest();
}
