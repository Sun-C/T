package com.jt.util;

import org.apache.taglibs.standard.tag.el.core.SetTag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

/**
 * 定义文件上传类型的集合信息
 */
@PropertySource("classpath:/properties/image.properties")
@Component//一般从来标识该类交给spring容器管理  不是任何业务层   就是工具api
public class ImageTypeUtil {
    //利用spring容器为属性赋值
    @Value("${image.imageTypes}")
    private String imageTypes;//type1,type2.....
    private Set<String> typeSet = new HashSet<>();
    //初始化集合信息
    //@PreDestroy  spring 容器关闭前执行该注解描述的方法
    @PostConstruct//当类也就是对象交个给容易管理后  执行方法
    public void init(){
        String[] typeArray = imageTypes.split(",");
        for (String type : typeArray){
            typeSet.add(type);
            //System.out.println(type);
        }
        //循环遍历候,typeSet就有值
        System.out.println("set集合初始化完成init()");
    }
    public Set<String> getTypeSet(){
        return typeSet;
    }
    //private static Set<String> typeSet = new HashSet<>();
    /**
     * 一般图片的类型就是常见的几种变化不大, 暂时可以写死
     * 如果不想写死 可以同过IO流进行读取
     * bmp，jpg，png，tif，gif，pcx，tga，exif，fpx，svg，psd，cdr，pcd，dxf，ufo，eps，ai，raw，WMF，webp
     */
//    static {
//        typeSet.add(".jpg");
//        typeSet.add(".png");
//        typeSet.add(".bmp");
//        typeSet.add(".tif");
//        typeSet.add(".pcx");
//        typeSet.add(".tga");
//        typeSet.add(".svg");
//        typeSet.add(".ufo");
//    }
//    public static Set<String> getImageType(){
//        return typeSet;
//    }
}
