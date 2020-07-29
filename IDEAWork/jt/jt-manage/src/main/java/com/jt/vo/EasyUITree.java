package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITree implements Serializable {
    private static final long serialVersionUID = -6430564299899005614L;
    //{"id","2","text":"xxx","state":"colsed"}
    private Long id;        //id值 与ItemCat中的id一致
    private String text;    //文本信息 itemCat中的name属性
    private String state;   //状态 打开:open 关闭树结构默认 closed
}
