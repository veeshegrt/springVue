package com.czw.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czw.springboot.entity.sysMenu;
import com.czw.springboot.mapper.sysMenuMapper;
import org.springframework.stereotype.Service;

@Service
public class sysMenuService extends ServiceImpl<sysMenuMapper, sysMenu> {
}
