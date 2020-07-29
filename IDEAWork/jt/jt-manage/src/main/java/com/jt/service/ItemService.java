package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

    EasyUITable findItemByPage(Integer page, Integer rows);

    int saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item, ItemDesc itemDesc);

    void deleteItemById(Long[] ids);

    String updateItemStatus(Integer stasus, Long[] ids);

   // String deleteItems(Long[] ids);

    ItemDesc findItemDesc(Long id);
}
