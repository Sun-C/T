package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import com.jt.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	//	@Override
//	public EasyUITable findItemByPage(Integer page, Integer rows) {
//		//1.获取总记录是
//		int total = itemMapper.selectCount(null);
//		int start = (page-1)*rows;
//		//Page<EasyUITable> easyUI = new Page<>(page, rows);
//		//QueryWrapper<Item> qw = new QueryWrapper<>();
//		List<Item> items = itemMapper.selectItemByPage(start, rows);
//		return new EasyUITable(total,items);
//	}

	/**
	 * 需要配置xml 也就是springboot的@Configuration类 还有构建对象@Bean
	 *
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//1.定义分页对象
		Page<Item> itemPage = new Page<>(page, rows);
		//2.定义分页构造器
		QueryWrapper<Item> itemQueryWrapper = new QueryWrapper<>();
		//3.要求:按照更新时间排序
		itemQueryWrapper.orderByDesc("created");
		itemPage = itemMapper.selectPage(itemPage, itemQueryWrapper);
		//获取总记录数
		long total = itemPage.getTotal();
		//获取查询的对象集合
		List<Item> records = itemPage.getRecords();
		return new EasyUITable((int) total, records);
	}

	@Transactional
	@Override
	public int saveItem(Item item, ItemDesc itemDesc) {

		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());

		int rows = itemMapper.insert(item);
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		//if (rows==0)new ServiceException("添加失败");
//		int a = 3;
//		a=a/0;
		return rows;
	}

	@Transactional
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		int i = itemMapper.updateById(item);
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
		if (i == 0) throw new RuntimeException("修改商品失败");
	}

	@Transactional
	@Override
	public void deleteItemById(Long[] ids) {
		List<Long> idList = Arrays.asList(ids);
		//1.删除商品详情信息
		itemDescMapper.deleteBatchIds(idList);
		//2.删除商品信息
		int rows = itemMapper.deleteBatchIds(idList);
		System.out.println("删除了" + rows + "条记录");
	}

	/**
	 * MP的更新操作
	 * 1.entity 要修改的记录
	 * 2.updataWrapper   修改条件构造器
	 * Sql: update tb_item set status=#{status} ,updated = #{updated} where id in (id,id,id,id);
	 *  单表操作 性能消耗可以忽略
	 * @param stasus
	 * @param ids
	 * @return
	 */
	@Transactional
	@Override
	public String updateItemStatus(Integer stasus, Long[] ids) {
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", Arrays.asList(ids));
		//updateWrapper.set("status",2);
		//设定商品数据
		Item item = new Item();
		item.setStatus(stasus).setUpdated(new Date());
		int update = itemMapper.update(item, updateWrapper);
		System.out.println("修改了" + update + "件商品");
		return stasus == 1 ? "上架成功" : "下架成功";
	}

	@Override
	public ItemDesc findItemDesc(Long id) {
		QueryWrapper<ItemDesc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("item_id",id);
		return itemDescMapper.selectOne(queryWrapper);
	}
	//	@Override
	//	public String deleteItems(Long[] ids) {
	//		int i = itemMapper.deleteBatchIds(Arrays.asList(ids));
	//		return i > 0 ? "删除成功" : "商品可能已经不存";
	//	}
}
