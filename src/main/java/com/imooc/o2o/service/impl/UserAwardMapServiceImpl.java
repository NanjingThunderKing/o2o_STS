package com.imooc.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.UserAwardMapDao;
import com.imooc.o2o.dao.UserShopMapDao;
import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.entity.UserShopMap;
import com.imooc.o2o.enums.UserAwardMapStateEnum;
import com.imooc.o2o.exceptions.UserAwardMapOperationException;
import com.imooc.o2o.service.UserAwardMapService;
import com.imooc.o2o.util.PageCalculator;

@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {
	
	@Autowired 
	private UserAwardMapDao userAwardMapDao;
	@Autowired 
	private UserShopMapDao userShopMapDao;

	@Override
	public UserAwardMapExecution listUserAwardMap(
			UserAwardMap userAwardMapCondition,
			Integer pageIndex,
			Integer pageSize) {
		// 空值判断
		if (userAwardMapCondition != null && pageIndex != -1 && pageSize != -1) {
			// 页转行
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			// 根据传入的查询条件分页返回用户与奖品信息映射列表
			List<UserAwardMap> userAwardMapList =
					userAwardMapDao.queryUserAwardMapList(userAwardMapCondition,
																						beginIndex, pageSize);
			// 按照同等查询条件返回总数
			int count = userAwardMapDao.queryUserAwardMapCount(userAwardMapCondition);
			UserAwardMapExecution ue = new UserAwardMapExecution();
			ue.setUserAwardMapList(userAwardMapList);
			ue.setCount(count);
			return ue;
		} else {
			return null;
		}
	}

	@Override
	public UserAwardMap getUserAwardMapById(long userAwardMapId) {
		return userAwardMapDao.queryUserAwardMapById(userAwardMapId);
	}

	@Override
	@Transactional
	public UserAwardMapExecution addUserAwardMap(
				UserAwardMap userAwardMap) throws UserAwardMapOperationException {
		// 空值判断
		if (userAwardMap != null && userAwardMap.getUser() != null &&
				userAwardMap.getUser().getUserId() != null &&
				userAwardMap.getShop() != null && userAwardMap.getShop().getShopId() != null) {
			// 设置默认值
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(0);
			try {
				int effectedNum = 0;
				// 若该奖品需要消耗积分，则将tb_user_shop_map对应的用户积分抵扣
				if (userAwardMap.getPoint() != null && userAwardMap.getPoint() > 0) {
					// 根据用户Id和店铺Id获取该用户在店铺的积分
					UserShopMap userShopMap = userShopMapDao.queryUserShopMap(
							userAwardMap.getUser().getUserId(), 
							userAwardMap.getShop().getShopId());
					// 判断该用户在店铺里是否有积分
					if (userShopMap != null) {
						// 若有积分，必须保证店铺积分大于兑换商品所需积分
						if (userShopMap.getPoint() >= userAwardMap.getPoint()) {
							// 积分抵扣
							userShopMap.setPoint(userShopMap.getPoint() - userAwardMap.getPoint());
							// 更新积分信息
							effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
							if (effectedNum <= 0) {
								throw new UserAwardMapOperationException("更新积分信息失败");
							}
						} else {
							throw new UserAwardMapOperationException("您的积分不足");
						}
					} else {
						// 在该店铺没有积分
						throw new UserAwardMapOperationException("您在本店无积分");
					}
				}
				// 插入礼品兑换信息
				effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
				if (effectedNum <= 0) {
					throw new UserAwardMapOperationException("领取奖励失败");
				}
				return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
			} catch (Exception e) {
				throw new UserAwardMapOperationException("领取奖励失败" + e.toString());
			}
		} else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_INFO);
		}
		
	}

	@Override
	@Transactional
	public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap)
			throws UserAwardMapOperationException {
		// 空值判断，检查userAwardId以及领取状态是否为空
		if (userAwardMap == null || userAwardMap.getUserAwardId() == null
				|| userAwardMap.getUsedStatus() == null) {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_INFO);
		} else {
			try {
				// 更新可用状态
				int effectedNunm = userAwardMapDao.updateUserAwardMap(userAwardMap);
				if (effectedNunm <= 0) {
					throw new UserAwardMapOperationException("更新奖品可用状态失败");
				} else {
					return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
				}
			} catch (Exception e) {
				throw new UserAwardMapOperationException("modifyUserAwardMap error : "
																											+ e.toString());
			}
		}
	}


	
}
