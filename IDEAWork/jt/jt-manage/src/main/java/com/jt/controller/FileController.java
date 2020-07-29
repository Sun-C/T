package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 编辑 此类使用SpringMVC
 */
@RestController //返回json的数据
public class FileController {

    @Value("${server.port}")
    private int port;
    @RequestMapping("/getPort")
    public String getPort(){
       return "当前服务器端口:"+port;
    }
    /**
     * url: localhost:8091/file
     * 参数; File=fileImage
     * 返回值: 字符串
     * @return
     * InputStream 别人向我传输数据我用这个接
     *
     * MultipartFile 接口 主要负责实现文件接收
     *
     * 做法: 1.必须指定文件上传的路径信息 D:\JAVA\image\xxx.jpg
     *      2.将字节信息利用outPutStream 进行传输操作
     */
    @RequestMapping("/file")
    public SysResult file(MultipartFile fileImage) throws IOException {
        //1;定义文件目录信息
        String dirPath = "E:/Image/测试文件上传目录";
        File fileDir = new File(dirPath);
        //2.校验文件目录信息是否存在
        if (!fileDir.exists()){//如果文件目录不存在,应该创建目录
            fileDir.mkdir();//创建多级目录
        }
        //3.获取文件信息,一般都是在上传提交的参数中
        String fileName = fileImage.getOriginalFilename();
        //4.实现文件上传, 指定文件真实路径
        File file = new File(dirPath+"/"+fileName);
        //5.利用Api实现文件上传
        fileImage.transferTo(file);//等于
        System.out.println(fileImage.getSize());
        return SysResult.success(fileImage.getSize());
    }

    /**
     * 业务分析:实现文件上传
     * 1.url地址: http://localhost:8091//pic/upload
     * 2.参数: uploadFile  文件上传对象
     * 3.返回值: ImageVO对象
     * @return
     */
    @Autowired
    private FileService fileService;
    @RequestMapping("/pic/upload")
    public ImageVO uploadFile(MultipartFile uploadFile){

        return fileService.uploadFile(uploadFile);
    }
}
