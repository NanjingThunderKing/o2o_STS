package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopAuthMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopAuthMapDaoTest {
	
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;
	
	/**
	 * 添加测试功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAinsertShopAuthMap() throws Exception {
		// 创建店铺授权信息1
		ShopAuthMap shopAuthMap1 = new ShopAuthMap();
		PersonInfo employee = new PersonInfo();
		employee.setUserId(1L);
		shopAuthMap1.setEmployee(employee);
		Shop shop = new Shop();
		shop.setShopId(1L);
		shopAuthMap1.setShop(shop);
		shopAuthMap1.setTitle("店家");
		shopAuthMap1.setTitleFlag(0);
		shopAuthMap1.setCreateTime(new Date());
		shopAuthMap1.setLastEditTime(new Date());
		shopAuthMap1.setEnableStatus(1);
		int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap1);
		assertEquals(1, effectedNum);
		// 创建店铺授权信息2
		ShopAuthMap shopAuthMap2 = new ShopAuthMap();
		PersonInfo employee2 = new PersonInfo();
		employee2.setUserId(3L);
		shopAuthMap2.setEmployee(employee2);
		Shop shop2 = new Shop();
		shop2.setShopId(1L);
		shopAuthMap2.setShop(shop2);
		shopAuthMap2.setTitle("资深员工");
		shopAuthMap2.setTitleFlag(1);
		shopAuthMap2.setCreateTime(new Date());
		shopAuthMap2.setLastEditTime(new Date());
		shopAuthMap2.setEnableStatus(0);
		effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap2);
		assertEquals(1, effectedNum);
		
	}
	
	/**
	 * 测试查询功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBqueryShopAuth() throws Exception {
		// 测试queryShopAuthMapListByShopId
		List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(1, 0, 2);
		assertEquals(1, shopAuthMapList.size());
		// 测试queryShopAuthMapById
		ShopAuthMap shopAuth = shopAuthMapDao.queryShopAuthMapById(shopAuthMapList.get(0).getShopAuthId());
		assertEquals("CEO", shopAuth.getTitle());
		System.out.println("employee's name is" + shopAuth.getEmployee().getName());
		System.out.println("shop name is" + shopAuth.getShop().getShopName());
		// 测试queryShopAuthCountByShopId
		int count = shopAuthMapDao.queryShopAuthCountByShopId(1);
		assertEquals(1, count);
		
	}
	
	/**
	 * 测试更新功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCupdateShopAuthMap() throws Exception {
		List<ShopAuthMap> shopAuthMapList= shopAuthMapDao.queryShopAuthMapListByShopId(1, 0, 1);
		shopAuthMapList.get(0).setTitle("WAF");
		shopAuthMapList.get(0).setTitleFlag(2);
		int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMapList.get(0));
		assertEquals(1, effectedNum);
	}
	
	/**
	 * 测试删除功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDdeleteShopAuthMap() throws Exception {
		List<ShopAuthMap> shopAuthMapList1= shopAuthMapDao.queryShopAuthMapListByShopId(1, 0, 2);
		List<ShopAuthMap> shopAuthMapList2= shopAuthMapDao.queryShopAuthMapListByShopId(10, 0, 3);
		int effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList1.get(0).getShopAuthId());
		assertEquals(1, effectedNum);
		effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList2.get(0).getShopAuthId());
		assertEquals(1, effectedNum);
	}

	
}
