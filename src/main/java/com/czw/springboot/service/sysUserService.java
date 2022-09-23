package com.czw.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czw.springboot.common.Constants;
import com.czw.springboot.controller.dto.userDto;
import com.czw.springboot.entity.sysUser;
import com.czw.springboot.exception.BaseException;
import com.czw.springboot.mapper.sysUserMapper;

import com.czw.springboot.utils.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class sysUserService extends ServiceImpl<sysUserMapper,sysUser> {

    public static Log LOG = Log.get();

    public userDto login(userDto userDto) {
        sysUser info = getInfo(userDto);
        if(info!=null){
            BeanUtil.copyProperties(info,userDto);
            String token = JwtUtils.getToken(userDto.getId(), userDto.getPassword());
            userDto.setToken(token);
            return userDto;
        }else {
            throw new BaseException(Constants.CODE_400,"账号或密码错误");
        }
    }

    public Boolean register(userDto userDto) {
        sysUser sysUser;
        try {
            sysUser = this.baseMapper.selectOne(new QueryWrapper<sysUser>().lambda().eq(com.czw.springboot.entity.sysUser::getUsername, userDto.getUsername()));
        }catch (Exception e){
            LOG.error(e);
            throw new BaseException(Constants.CODE_500,"系统错误");
        }
        if(sysUser==null){
            com.czw.springboot.entity.sysUser info = new sysUser();
            BeanUtil.copyProperties(userDto,info);
            boolean save = save(info);
            return save;
        }else {
            throw new BaseException(Constants.CODE_400,"用户已存在");
        }
    }

    public sysUser getInfo(userDto userDto){
        sysUser sysUser;
        try {
            sysUser = this.baseMapper.selectOne(new QueryWrapper<sysUser>().lambda().eq(com.czw.springboot.entity.sysUser::getUsername, userDto.getUsername())
                    .eq(com.czw.springboot.entity.sysUser::getPassword, userDto.getPassword()));
        }catch (Exception e){
            LOG.error(e);
            throw new BaseException(Constants.CODE_500,"系统错误");
        }
        return sysUser;
    }
}
