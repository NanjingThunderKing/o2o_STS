package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductSellDailyDaoTest {
	
	@Autowired
	private ProductSellDailyDao productSellDailyDao;
	
	/**
	 * 测试有销量的商品添加功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAinsertProductSellDaily() throws Exception {
		// 创建商品日销量统计表
		int effectedNum = productSellDailyDao.insertProductSellDaily();
		assertEquals(3, effectedNum);
	}
	
	/**
	 * 测试无销量的商品添加功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBinsertDefaultProductSellDaily() throws Exception {
		int effectedNum = productSellDailyDao.insertDefaultProductSellDaily();
		assertEquals(7, effectedNum);
	}
	
	
	@Test
	public void testCqueryProductSellDailyList() throws Exception {
		ProductSellDaily productSellDaily = new ProductSellDaily();
		// 叠加店铺查询
		Shop shop = new Shop();
		shop.setShopId(1L);
		productSellDaily.setShop(shop);
		List<ProductSellDaily> productSellDailyList =
				productSellDailyDao.queryProductSellDailyList(productSellDaily, null, null);
		assertEquals(6, productSellDailyList.size());
	}
	

}
