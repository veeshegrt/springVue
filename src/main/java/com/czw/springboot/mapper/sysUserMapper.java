package com.czw.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czw.springboot.entity.sysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface sysUserMapper extends BaseMapper<sysUser> {

//    @Select("select * from sys_user")
//    List<sysUser> findAll();
//
//    @Insert("insert into sys_user(username,password,nickname,email,phone,address) " +
//            "values (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
//    Integer insert(sysUser sysUser);
//
//    Integer update(sysUser sysUser);
//
//    @Delete("delete from sys_user where id = #{id}")
//    Integer deleteById(Integer id);
//
//    @Select("select * from sys_user limit #{pageNum},#{pageSize}")
//    List<sysUser> page(Integer pageNum, Integer pageSize);
}
