package com.aws.practice.mybatis;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;


@RegisterMapper
public interface NewMySqlMapper<T> extends MyInsertListMapper<T>, InsertUseGeneratedKeysMapper<T> {
}
