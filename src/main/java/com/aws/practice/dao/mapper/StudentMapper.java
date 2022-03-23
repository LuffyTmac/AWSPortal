package com.aws.practice.dao.mapper;

import com.aws.practice.dao.entity.Student;
import com.aws.practice.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface StudentMapper extends IBaseMapper<Student> {
	void addStudent();
	List<Student> getAllStudent();
}