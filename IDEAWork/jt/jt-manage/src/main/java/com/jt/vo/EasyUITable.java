package com.jt.vo;

import com.jt.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EasyUITable implements Serializable {
    private static final long serialVersionUID = 4393767531689032263L;
    private Integer total;
    private List<Item> rows;
    //json结构
    /**
     * {
     * 	"total":2000,
     * 	"rows":[
     *        {"code":"A","name":"果汁","price":"20"},
     *        {"code":"B","name":"汉堡","price":"30"},
     *        {"code":"C","name":"鸡柳","price":"40"},
     *        {"code":"D","name":"可乐","price":"50"},
     *        {"code":"E","name":"薯条","price":"10"},
     *        {"code":"F","name":"麦旋风","price":"20"},
     *        {"code":"G","name":"套餐","price":"100"}
     * 	]
     * }
     */

}
