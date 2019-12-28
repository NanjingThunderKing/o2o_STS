package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ShopCategory;

public interface ShopCategoryDao {

	/**
	 * 查询店铺种类
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
	
	/**
	 * 新增店铺种类
	 * @param shopCategory
	 * @return
	 */
	int insertShopCategory(ShopCategory shopCategory);
	
}
