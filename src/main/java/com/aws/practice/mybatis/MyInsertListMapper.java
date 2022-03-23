package com.aws.practice.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;


@RegisterMapper
public interface MyInsertListMapper<T> {
    @InsertProvider(type = MySpecialProvider.class, method = "dynamicSQL")
    int insertList(List<T> var1);
}
