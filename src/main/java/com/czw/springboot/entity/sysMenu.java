package com.czw.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_menu")
public class sysMenu {

    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;


}
