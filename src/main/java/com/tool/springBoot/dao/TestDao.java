package com.tool.springBoot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tool.springBoot.vo.StudentVO;

@Mapper
public interface TestDao {
//	public void myInsert(StudentVO vo);
	
	public List<StudentVO> findAll();

}
