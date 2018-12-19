package com.ck.service.basicService;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by tpeng on 2017/3/14.
 */
public abstract class BaseService<T> {

    public abstract Mapper getMapper();

    public int deleteByPrimaryKey(Object key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public int delete(T record) {
        return getMapper().delete(record);
    }

    public int insert(T record) {
        return getMapper().insert(record);
    }

    public int insertSelective(T record) {
        return getMapper().insertSelective(record);
    }

    public List selectAll() {
        return getMapper().selectAll();
    }

    public T selectByPrimaryKey(Object key) {
        return (T) getMapper().selectByPrimaryKey(key);
    }

    public int selectCount(T record) {
        return getMapper().selectCount(record);
    }

    public List select(T record) {
        return getMapper().select(record);
    }

    public T selectOne(T record) {
        return (T) getMapper().selectOne(record);
    }

    public int updateByPrimaryKey(Object record) {
        return getMapper().updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(Object record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }
}
