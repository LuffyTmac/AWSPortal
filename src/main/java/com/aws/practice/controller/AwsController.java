package com.aws.practice.controller;

import com.aws.practice.dao.entity.Student;
import com.aws.practice.dao.mapper.StudentMapper;
import com.aws.practice.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
public class AwsController
{
	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private RedisUtil redisUtil;

	@GetMapping(value = "sayHello")
	public String sayHello(){
		return "Hi, AWS 啦啦啦啦啦啦!";
	}

	@RequestMapping(value = "saveDataToDB")
	public String saveDataToDB(){
		try {
			studentMapper.addStudent();
			return "Success,please check db data !";
		}catch (Exception e){
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	@RequestMapping(value = "getDataFromDB")
	public List<Student> getDataFromDB(){
		try {
			return studentMapper.getAllStudent();
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "saveDataToRedis")
	public String saveDataToRedis(@RequestParam("msg") String msg){
		try {
			redisUtil.set("yangxiaowei", "aws_" + msg);
			return "Success,please check redis !";
		}catch (Exception e){
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	@RequestMapping(value = "getDataFromRedis")
	public String getDataFromRedis(@RequestParam("key") String key){
		try {
			return redisUtil.get(key).toString();
		}catch (Exception e){
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}
}
