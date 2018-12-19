package com.ck.service;

import com.ck.dao.entity.TUser;
import com.ck.dao.mapper.TUserMapper;
import com.ck.service.basicService.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class TUserService extends BaseService<TUser> {
    @Autowired
    TUserMapper tUserMapper;

    @Override
    public Mapper getMapper() {
        return tUserMapper;
    }
}
