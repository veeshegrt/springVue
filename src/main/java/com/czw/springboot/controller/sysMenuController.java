package com.czw.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czw.springboot.common.Result;
import com.czw.springboot.entity.sysMenu;
import com.czw.springboot.entity.sysRole;
import com.czw.springboot.service.sysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysMenu")
public class sysMenuController {

    @Autowired
    private sysMenuService sysMenuService;

    // 新增或修改
    @PostMapping
    public Result save(@RequestBody sysMenu sysMenu){
        return Result.success(sysMenuService.saveOrUpdate(sysMenu));
    }

    // 删除
    @PostMapping("/delete/{id}")
    public Boolean deleteById(@PathVariable Integer id){
        return sysMenuService.removeById(id);
    }

    // 查询
    @GetMapping("/getList")
    public Result getList(@RequestParam int pageNum,@RequestParam int pageSize,
                          @RequestParam String name){
        Page page = new Page(pageNum,pageSize);
        Page data = sysMenuService.page(page,new QueryWrapper<sysMenu>().lambda().like((sysMenu::getName),name));
        return Result.success(data);
    }

}
