package com.czw.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_file")
public class sysFile {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String fileName;

    private String type;

    private Long size;

    private String url;

    private Integer deleted;

    private Integer enable;

    private String md5;

}
