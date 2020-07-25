package org.dev.framework.modules.sys.controller;


import lombok.extern.slf4j.Slf4j;
import org.dev.framework.modules.sys.entity.SysUpload;
import org.dev.framework.modules.sys.service.SysUploadService;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.entity.SysUpload;
import org.dev.framework.modules.sys.service.SysUploadService;
import org.dev.framework.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 上传文件 前端控制器
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-18
 */
@RestController
@RequestMapping("/sysUpload")
@Slf4j
public class SysUploadController {


    @Autowired
    SysUploadService sysUploadService;

    @Value("${springboot_vue.upload.local-upload-path}")
    private String upload_path;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 文件上传
     *
     * @param multipartfiles
     * @return
     */
    @PostMapping(value = "/upload")
    public ResponseResult uploading(@RequestParam("multipartfiles") MultipartFile[] multipartfiles) {
        Date now = new Date();
        String dirName = sdf.format(now);
        String upload_dir = upload_path + "/" + dirName;
        List<SysUpload> sysUploadList = new ArrayList();
        File targetFile = new File(upload_dir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        for (MultipartFile file : multipartfiles) {
            String fileName = UUID.randomUUID().toString();
            fileName = fileName + "." + this.lastName(file);
            try (FileOutputStream out = new FileOutputStream(upload_path + "/" + fileName)) {
                out.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("文件上传失败!");
                return ResponseResult.error("uploading failure");
            }
            //写入日志
            String url = dirName + "/" + fileName;
            SysUpload sysUpload = new SysUpload();
            sysUpload.setName(file.getOriginalFilename());
            sysUpload.setUrl(url);
            sysUploadList.add(sysUpload);
        }
        this.sysUploadService.saveBatch(sysUploadList);
        log.info("文件上传成功!");
        return ResponseResult.success();
    }

    /**
     * @param file
     * @return
     */
    private String lastName(MultipartFile file) {
        if (file == null) return null;
        String filename = file.getOriginalFilename();
        if (filename.lastIndexOf(".") == -1) {
            return "";//文件没有后缀名的情况
        }
        //此时返回的是带有 . 的后缀名，
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 文件下载
     *
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/download")
    public void downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "JAVA核心知识点整理.pdf";
        String filePath = "D:/file";
        File file = new File(filePath + "/" + filename);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

