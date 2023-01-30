package com.czw.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czw.springboot.common.Result;
import com.czw.springboot.entity.sysRole;
import com.czw.springboot.entity.sysUser;
import com.czw.springboot.service.sysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: czw
 * @date: 2022/11/23 15:23
 * @description:角色管理
 */
@RestController
@RequestMapping("/role")
public class sysRoleController {

    @Autowired
    private sysRoleService sysRoleService;

    @GetMapping("/getList")
    public Result getList(@RequestParam int pageNum,@RequestParam int pageSize,
                          @RequestParam String name){
        Page page = new Page(pageNum,pageSize);
        Page data = sysRoleService.page(page,new QueryWrapper<sysRole>().lambda().like((sysRole::getName),name));
        return Result.success(data);
    }

    // 新增或修改
    @PostMapping("/save")
    public Boolean save(@RequestBody sysRole sysRole){
        return sysRoleService.saveOrUpdate(sysRole);
    }

    // 删除
    @PostMapping("/delete/{id}")
    public Boolean deleteById(@PathVariable Integer id){
        return sysRoleService.removeById(id);
    }




}
