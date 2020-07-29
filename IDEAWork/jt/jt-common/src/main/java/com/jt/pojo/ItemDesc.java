package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "tb_item_desc")
public class ItemDesc extends BasePojo {
    @TableId
    private Long itemId;        //商品详情id  itemId既是item主键,也是itemDesc表的主键值相同 不能描述自增
    private String itemDesc;//商品详情
}
