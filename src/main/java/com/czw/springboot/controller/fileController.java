package com.czw.springboot.controller;


import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czw.springboot.common.Result;
import com.czw.springboot.entity.sysFile;
import com.czw.springboot.mapper.sysFileMapper;
import com.czw.springboot.service.sysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/file")
public class fileController {

    @Value("${files.upload.path}")
    String path;

    @Resource
    sysFileMapper sysFileMapper;

    @Autowired
    private sysFileService fileService;

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        String s = IdUtil.fastSimpleUUID();
        String mark = s+StrUtil.DOT+type;
        File realFile = new File(path + mark);
        file.transferTo(realFile);
        String md5 = SecureUtil.md5(realFile);
        String url;
        List<sysFile> sysFiles = sysFileMapper.selectList(new QueryWrapper<sysFile>().lambda().eq((sysFile::getMd5), md5));
        if(sysFiles.size()!=0){
            url = sysFiles.get(0).getUrl();
            realFile.delete();
        }else {
            url = "http://localhost:9090/file/" + mark;
        }
        sysFile sysFile = new sysFile();
        sysFile.setFileName(originalFilename);
        sysFile.setSize(size/1024);
        sysFile.setType(type);
        sysFile.setUrl(url);
        sysFile.setMd5(md5);
        sysFileMapper.insert(sysFile);
        return Result.success(url);
    }

    @GetMapping("/{fileUUID}")
    public Result download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File outFile = new File(path + fileUUID);
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        outputStream.write(FileUtil.readBytes(outFile));
        outputStream.flush();
        outputStream.close();
        return Result.success();
    }

    @GetMapping("/getFiles")
    public Result getFiles(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String fileName){
        Page page = new Page(pageNum, pageSize);
        Page filePage = fileService.page(page, new QueryWrapper<sysFile>().lambda().like((sysFile::getFileName), fileName));
        return Result.success(filePage);
    }

    // 删除
    @PostMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        return Result.success(fileService.removeById(id));
    }

    // 批量删除
    @PostMapping("/batchDel")
    public Result batchDelById(@RequestBody List<Integer> ids){
        return Result.success(fileService.removeBatchByIds(ids));
    }

}
