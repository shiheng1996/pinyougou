package com.pinyougou.sellergoods.service.impl;
import java.util.List;

import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.pojo.TbItemCatExample.Criteria;
import com.pinyougou.sellergoods.service.ItemCatService;

import entity.PageResult;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItemCat> page=   (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public Result delete(Long[] ids) {
		for(Long id:ids){
			TbItemCatExample example=new TbItemCatExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(id);
			List<TbItemCat> list = itemCatMapper.selectByExample(example);
			if(list.size()>0){
				return new Result(false,"id:"+id+"有下级分类,无法删除!");
			}else {
				itemCatMapper.deleteByPrimaryKey(id);
			}
		}
		return new Result(true,"删除成功");
	}
	
	
		@Override
	public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
						if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		Page<TbItemCat> page= (Page<TbItemCat>)itemCatMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
		@Autowired
        private RedisTemplate redistemplate;


	   public List<TbItemCat> findByParentId(Long id){
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
		//每次执行查询时,一次性读取数据进缓存(因为每次进行分类的增删改操作后,都要进行查询)
        List<TbItemCat> list = findAll();
        for (TbItemCat itemCat : list) {
            //itemcats作为大的key 分类的名称作为小key,模板id作为value
          redistemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());
        }
        System.out.println("更新缓存:商品分类表");
        return  tbItemCats;
	};
}
