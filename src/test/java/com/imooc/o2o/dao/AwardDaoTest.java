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

import com.imooc.o2o.entity.Award;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest {
	
	@Autowired
	private AwardDao awardDao;
	
	/**
	 * 添加测试功能
	 * @throws Exception
	 */
	@Test
	public void testAinsertAward() throws Exception {
		long shopId = 1;
		// 创建奖品1号
		Award award1 = new Award();
		award1.setAwardName("销魂女仆情趣内衣");
		award1.setAwardImg("贼爽");
		award1.setPoint(6);
		award1.setPriority(12);
		award1.setEnableStatus(1);
		award1.setCreateTime(new Date());
		award1.setLastEditTime(new Date());
		award1.setShopId(shopId);
		int effectedNum = awardDao.insertAward(award1);
		assertEquals(1, effectedNum);
		// 创建奖品2号
		Award award2 = new Award();
		award2.setAwardName("高跟长腿黑丝诱惑");
		award2.setAwardImg("适度做爱有益健康");
		award2.setPoint(12);
		award2.setPriority(16);
		award2.setEnableStatus(1);
		award2.setCreateTime(new Date());
		award2.setLastEditTime(new Date());
		award2.setShopId(shopId);
		effectedNum = awardDao.insertAward(award2);
		assertEquals(1, effectedNum);
		
	}
	
	/**
	 * 测试查询列表功能
	 * @throws Exception
	 */
	@Test
	public void testBqueryAwardList() throws Exception {
		Award award = new Award();
		List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(3, awardList.size());
		int count = awardDao.queryAwardCount(award);
		assertEquals(4, count);
		award.setAwardName("测试");
		awardList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awardList.size());
		count = awardDao.queryAwardCount(award);
		assertEquals(2, count);
		
	}
	
	/**
	 * 测试按照Id进行查询
	 * @throws Exception
	 */
	@Test
	public void testCqueryAwardByAwardId() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("号");
		// 模糊查询名字带号字的奖品
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 3);
		assertEquals(2, awardList.size());
		// 通过指定Id查询奖品
		Award award = awardDao.queryAwardByAwardId(awardList.get(0).getAwardId());
		System.out.println(award.getAwardName());
	}
	
	
	@Test
	public void testDupdateAward() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("测试一下1");
		// 按照特定名字查询特定商品
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 3);
		// 修改该商品的名称
		awardList.get(0).setAwardName("第一个测试奖品");
		int effectedNum = awardDao.updateAward(awardList.get(0));
		assertEquals(1, effectedNum);
		// 将修改后的奖品找出来并验证
		Award award = awardDao.queryAwardByAwardId(awardList.get(0).getAwardId());
		assertEquals("第一个测试奖品", award.getAwardName());
	}
	
	/**
	 * 测试删除功能
	 * @throws Exception
	 */
	@Test
	public void testEdeleteAward() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("测试");
		// 删除所有名字带测试的奖品
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 2);
		assertEquals(2, awardList.size());
		for (Award award : awardList) {
			int effectedNum = awardDao.deleteAward(award.getAwardId(), award.getShopId());
			assertEquals(1, effectedNum);
		}
	}

}
