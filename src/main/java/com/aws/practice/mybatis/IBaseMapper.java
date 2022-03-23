package com.aws.practice.mybatis;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;


public interface IBaseMapper<T>
        extends
        Mapper<T>,
        NewMySqlMapper<T>,
        MyInsertListMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T> {
}