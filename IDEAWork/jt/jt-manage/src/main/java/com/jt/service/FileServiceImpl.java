package com.jt.service;

import com.jt.mapper.ItemMapper;
import com.jt.util.ImageTypeUtil;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通过指定配置文件,进行属性的注入
 *
 */
@PropertySource("classpath:/properties/image.properties")
@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ImageTypeUtil imageTypeUtil;
    @Value("${image.localDir}")
    private String localDir;//="E:\\Image\\测试文件上传目录";//1.优化点
    @Value("${image.imageUrl}")
    private String imageUrl;
    /**
     * 1.如何校验上传内容是图片???  正则 或者 map里面看能取到想要的不
     * 2.如何保证图片检索速度更快???    1)hash  2)时间维度
     * 3.如何防止文件的重名???   UUID
     * @param uploadFile
     * @return
     */
    @Override
    public ImageVO uploadFile(MultipartFile uploadFile) {
        //1.校验上传的文件是否是图片
        //1.1 初始化图片的类型集合  优化1 利用静态代码块实现
        Set<String> typeSet = imageTypeUtil.getTypeSet();
        //1.2 优化方式2 利用spring
//        List<String> typeList = new ArrayList<>();
//        Set<String> typeSet = new HashSet<>();//2.优化点2
//        typeSet.add(".jpg");
//        typeSet.add(".png");
//        typeSet.add(".gif");
        //1.2校验图片类型是否存在有效 abc.jpg
        //if(typeSet.contains(".jpg")){}
        String fileName = uploadFile.getOriginalFilename();
        fileName.toLowerCase();//将所有字符转换为小写;
        int index = fileName.lastIndexOf(".");
        //.jpg
        System.out.println(typeSet.toString());
        String fileType = fileName.substring(index);
        if (!typeSet.contains(fileType)){
            return ImageVO.fail();
        }
        //2.准备文件上传的目录结构  .指定文件的根目录 加 一个 动态变化的目录
        //2.1 E:/Image/测试文件上传目录+/2020/7/10/
        String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        //拼接字符串地址  本地加 时间
        String dirPath = localDir+dateDir;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();//目录如果不存在就创建
        }
        //3.重新指定文件名称
        String uuid = UUID.randomUUID().toString();
        String realFileName = uuid+fileType;
        //4.执行文件上传代码
        String url = dirPath+realFileName;
        File imageFile = new File(url);
        try {
            uploadFile.transferTo(imageFile);
            //图片访问的虚拟路径
            return ImageVO.success(imageUrl+dateDir+realFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ImageVO.fail();
        }
    }
}
