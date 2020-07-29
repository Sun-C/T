package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/item/")
@RestController//不用经过视图解析器
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 业务需求:展现商品列表数据,以EasyUITable表格数据展现
	 * url: /item/query>page=1&rows=20
	 * 返回值: EasyUITable VO对象
	 * @return
	 */
	@RequestMapping("query")
	public EasyUITable findItemByPage(Integer page, Integer rows){
		return itemService.findItemByPage(page,rows);
	}
	/**
	 * http://localhost:8091/item/query/item/desc/1474391962
	 */
	@RequestMapping("query/item/desc/{id}")
	public SysResult findItemDesc(@PathVariable("id") Long id){
		ItemDesc itemDesc = itemService.findItemDesc(id);
		return SysResult.success(itemDesc);
	}


	/**
	 * 商品新增操作
	 * url: /item/save
	 * 参数: form表单提交
	 * 返回结果:SysResult
	 */
	@RequestMapping("save")
	public SysResult saveItem(Item item, ItemDesc itemDesc) {
//		try {
//			itemService.saveItem(item);
//			return SysResult.success("新增成功!");
//		}catch (Exception e){
//			return SysResult.fail(e);}

		itemService.saveItem(item,itemDesc);
		return SysResult.success("新增成功!");
	}
	/**
	 * 商品修改操作
	 * url: /item/update
	 * 参数: form表单提交
	 * 返回结果:SysResult对象
	 */
	@RequestMapping("update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {

		itemService.updateItem(item,itemDesc);
		return SysResult.success("修改成功!");
	}
	/**
	 * 批量删除商品操作
	 * url: /item/delete
	 * 参数: ids[]
	 * 返回msg
	 */
	@RequestMapping("delete")
	public SysResult deleteItemByIds(Long... ids){
		itemService.deleteItemById(ids);
		return SysResult.success("删除成功");
	}
	/**
	 * 批量上下架商品操作
	 * url: /item/instock
	 * 参数: ids[]
	 * 返回msg
	 */
	@RequestMapping("{status}")
	public SysResult updateItemStatusByIds(@PathVariable(value = "status") Integer stasus, Long... ids){
		return SysResult.success(itemService.updateItemStatus(stasus,ids));
	}

	/**
	 * 删除商品操作
	 * url: /item/delete
	 * 参数: ids[]
	 * 返回msg
	 */
//	@RequestMapping("delete")
//	public SysResult deleteItems(Long...ids){
//		return SysResult.success(itemService.deleteItems(ids));
//	}
}
