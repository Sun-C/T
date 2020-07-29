package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {
    ItemCat queryItemName(Long id);

    List<EasyUITree> FindEasyUITree(Long parentId);

    List<EasyUITree> findItemCatByCache(Long id);
}
