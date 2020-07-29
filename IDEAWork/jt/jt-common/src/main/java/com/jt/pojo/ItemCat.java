package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@TableName("tb_item_cat")
public class ItemCat extends BasePojo{
    private static final long serialVersionUID = 5006550716849464495L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private Integer status;//1:正常 2:删除
    private Integer sortOrder;//排序号
    private Boolean isParent;//0 fales !1真
}
