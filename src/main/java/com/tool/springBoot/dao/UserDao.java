package com.tool.springBoot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tool.springBoot.vo.StudentVO;
import com.tool.springBoot.vo.User;

/**
 * Created by panxiang on 2017-06-25.
 */
@Mapper
public interface UserDao {

    User getUserByName(String name);
    public List<StudentVO> findAll();
}
