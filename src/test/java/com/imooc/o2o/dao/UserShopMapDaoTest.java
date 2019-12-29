package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.imooc.o2o.entity.UserShopMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserShopMapDaoTest {
	
	@Autowired
	private UserShopMapDao userShopMapDao;
	
	/**
	 * 测试添加功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAinsertUserShopMap() throws Exception {
		// 创建用户店铺积分统计信息1
		UserShopMap userShopMap = new UserShopMap();
		PersonInfo customer = new PersonInfo();
		customer.setUserId(1L);
		userShopMap.setUser(customer);
		Shop shop = new Shop();
		shop.setShopId(1L);
		userShopMap.setShop(shop);
		userShopMap.setCreateTime(new Date());
		userShopMap.setPoint(2);
		int effectedNum = userShopMapDao.insertUserShopMap(userShopMap);
		assertEquals(1, effectedNum);
		// 创建用户店铺积分统计信息2
		UserShopMap userShopMap2 = new UserShopMap();
		PersonInfo customer2 = new PersonInfo();
		customer2.setUserId(4L);
		userShopMap2.setUser(customer2);
		Shop shop2 = new Shop();
		shop2.setShopId(1L);
		userShopMap2.setShop(shop2);
		userShopMap2.setCreateTime(new Date());
		userShopMap2.setPoint(6);
		effectedNum = userShopMapDao.insertUserShopMap(userShopMap2);
		assertEquals(1, effectedNum);
		
	}
	
	/**
	 * 测试查询功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBqueryUserShopMap() throws Exception {
		UserShopMap userShopMap = new UserShopMap();
		// 查全部
		List<UserShopMap> userShopMapMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 2);
		assertEquals(2, userShopMapMapList.size());
		int count = userShopMapDao.queryUserShopMapCount(userShopMap);
		assertEquals(2, count);
		// 按店铺来查询
		Shop shop = new Shop();
		shop.setShopId(19L);
		userShopMap.setShop(shop);
		userShopMapMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 3);
		assertEquals(1, userShopMapMapList.size());
		count = userShopMapDao.queryUserShopMapCount(userShopMap);
		assertEquals(1, count);
		// 按用户ID和店铺查询
		userShopMap = userShopMapDao.queryUserShopMap(3L, 36L);
		assertEquals("测试一下", userShopMap.getUser().getName());
		
	}
	
	/**
	 * 测试更新功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCupdateUserShopMapPoint() throws Exception {
		UserShopMap userShopMap = new UserShopMap();
		userShopMap = userShopMapDao.queryUserShopMap(3L, 36L);
		assertTrue("Error,积分不一致！", 1 == userShopMap.getPoint());
		userShopMap.setPoint(2);
		int effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
		assertEquals(1, effectedNum);
		
	}
	

}