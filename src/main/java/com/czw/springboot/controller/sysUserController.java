package com.czw.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czw.springboot.common.Constants;
import com.czw.springboot.common.Result;
import com.czw.springboot.controller.dto.userDto;
import com.czw.springboot.entity.sysUser;
import com.czw.springboot.service.sysUserService;
import com.czw.springboot.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/sysUser")
public class sysUserController {

    @Autowired
    private sysUserService sysUserService;

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody userDto userDto){
        if(StrUtil.isBlank(userDto.getUsername()) || StrUtil.isBlank(userDto.getUsername())){
            return Result.error(Constants.CODE_400,"用户名或密码为空");
        }
        userDto user = sysUserService.login(userDto);
        return Result.success(user);
    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody userDto userDto){
        if(StrUtil.isBlank(userDto.getUsername()) || StrUtil.isBlank(userDto.getUsername())){
            return Result.error(Constants.CODE_400,"用户名或密码为空");
        }
        return Result.success(sysUserService.register(userDto));
    }



    // 查询
    @GetMapping("/findAll")
    public List<sysUser> getOk(){ return sysUserService.list();
    }

    // 根据用户名查询信息
    @PostMapping("/find/{username}")
    public Result find(@PathVariable String username){
        sysUser one = sysUserService.getOne(new QueryWrapper<sysUser>().lambda().eq((sysUser::getUsername), username));
        return Result.success(one);
    }

    // 新增或修改
    @PostMapping("/save")
    public Boolean save(@RequestBody sysUser sysUser){
        return sysUserService.saveOrUpdate(sysUser);
    }

    // 删除
    @PostMapping("/delete/{id}")
    public Boolean deleteById(@PathVariable Integer id){
        return sysUserService.removeById(id);
    }

    // 删除
    @PostMapping("/batchDel")
    public Boolean batchDelById(@RequestBody List<Integer> ids){
        return sysUserService.removeBatchByIds(ids);
    }

    @GetMapping("/getData")
    public IPage getData(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String username){
        Page<sysUser> page = new Page<>(pageNum,pageSize);
        QueryWrapper<sysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Objects.nonNull(username), sysUser::getUsername, username).orderByDesc(sysUser::getId);
        IPage<sysUser> pages = sysUserService.page(page,queryWrapper);
        return pages;
    }

    // 导出
    @GetMapping("/export")
    public Boolean export(HttpServletResponse response) throws IOException {
        List<sysUser> list = sysUserService.list();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return true;
    }
    // 导入
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<sysUser> sysUsers = reader.readAll(sysUser.class);
        boolean save = sysUserService.saveBatch(sysUsers);
        return save;
    }

    @GetMapping("/echartsData")
    public Result echartsData(){
         int q1 = 0;
         int q2 = 0;
         int q3 = 0;
         int q4 = 0;
        List<sysUser> list = sysUserService.list();
        for(sysUser user : list){
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: q1 += 1;break;
                case Q2: q2 += 1;break;
                case Q3: q3 += 1;break;
                case Q4: q4 += 1;break;
                default:break;
            }
        }
        ArrayList<Object> echartsList = new ArrayList<>();
        ArrayList<Object> dataList = new ArrayList<>();
        ArrayList<Object> piMapList = new ArrayList<>();
        ArrayList<String> piList = CollUtil.newArrayList("第一季度", "第二季度", "第三季度", "第四季度");
        echartsList.add(q1);
        echartsList.add(q2);
        echartsList.add(q3);
        echartsList.add(q4);

        for(int i = 0;i<echartsList.size();i++){
            HashMap<Object, Object> piMap = new HashMap<>();
            piMap.put("name",piList.get(i));
            piMap.put("value",echartsList.get(i));
            piMapList.add(piMap);
        }

        dataList.add(piMapList.toArray());
        dataList.add(echartsList.toArray());
        return Result.success(dataList);
    }

}
