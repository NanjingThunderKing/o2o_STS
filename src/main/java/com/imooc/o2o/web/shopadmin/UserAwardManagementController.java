package com.imooc.o2o.web.shopadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopAuthMapExecution;
import com.imooc.o2o.dto.UserAccessToken;
import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.dto.WechatInfo;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.UserAwardMapStateEnum;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.service.ShopAuthMapService;
import com.imooc.o2o.service.UserAwardMapService;
import com.imooc.o2o.service.WechatAuthService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.wechat.WechatUtil;

@Controller
@RequestMapping("/shopadmin")
public class UserAwardManagementController {
	
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private WechatAuthService wechatAuthService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private ShopAuthMapService shopAuthMapService;
	
	@RequestMapping(value = "/listuserawardmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取分页信息
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 从session中获取当前的店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 空值校验
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
																&& (currentShop.getShopId() != null)) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setShop(currentShop);
			// 从请求中获取奖品名
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			if (awardName != null) {
				// 如需按照奖品名称搜索，添加搜索条件
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMapCondition.setAward(award);
			}
			// 分页返回结果
			UserAwardMapExecution ue =
					userAwardMapService.listUserAwardMap(userAwardMapCondition,
																									pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or shopId");
		}
		return modelMap;
	}
	
	
	@RequestMapping(value = "/exchangeaward", method = RequestMethod.GET)
	private String exchangeAward(HttpServletRequest request, HttpServletResponse response)
								throws IOException {
		// 获取负责扫描二维码的店员信息
		WechatAuth auth = getOperatorInfo(request);
		if (auth != null) {
			// 通过userId获取店员信息
			PersonInfo operator = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
			// 设置上用户的Session
			request.getSession().setAttribute("user", operator);
			// 解析微信回传过来的自定义参数state，解码之前编码
			String qrCodeinfo = new String(
					URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8"));
			ObjectMapper mapper = new ObjectMapper();
			WechatInfo wechatInfo = null;
			try {
				wechatInfo = mapper.readValue(qrCodeinfo.replace("aaa", "\""), WechatInfo.class);
			} catch (Exception e) {
				return "shop/operationfail";
			}
			// 校验二维码是否已经过期
			if (!checkQRCodeInfo(wechatInfo)) {
				return "shop/operationfail";
			}
			// 获取用户奖品映射主键
			Long userAwardId = wechatInfo.getUserAwardId();
			// 获取顾客Id
			Long customerId = wechatInfo.getCustomerId();
			// 将顾客信息，操作员信息以及奖品信息封装成userAwardMap
			UserAwardMap userAwardMap =
					compactUserAwardMap4Exchange(customerId, userAwardId, operator);
			if (userAwardMap != null) {
				try {
					// 检查该员工是否具有扫码权限
					if(!checkShopAuth(operator.getUserId(), userAwardMap)) {
						return "shop/operationfail";
					}
					// 修改奖品的领取状态
					UserAwardMapExecution se =
							userAwardMapService.modifyUserAwardMap(userAwardMap);
					if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
						return "shop/operationsuccess";
					}
				} catch (RuntimeException e) {
					return "shop/operationfail";
				}
			}
		}
		return "shop/operationfail";
	}

	/**
	 * 检查扫码人员是否有操作权限
	 * 
	 * @param userId
	 * @param userAwardMap
	 * @return
	 */
	private boolean checkShopAuth(Long userId, UserAwardMap userAwardMap) {
		// 获取该店铺所有授权信息
		ShopAuthMapExecution shopAuthMapExecution =
				shopAuthMapService.listShopAuthMapByShopId(
									userAwardMap.getShop().getShopId(), 1, 1000);
		for (ShopAuthMap shopAuthMap : shopAuthMapExecution.getShopAuthMapList()) {
			// 看看是否给该人员授权权限过
			if (shopAuthMap.getEmployee().getUserId() == userId) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 将顾客信息，操作员信息以及奖品信息封装成UserAwardMap
	 * 
	 * @param customerId
	 * @param userAwardId
	 * @param operatorId
	 * @return
	 */
	private UserAwardMap compactUserAwardMap4Exchange(Long customerId,
																		Long userAwardId, PersonInfo operator) {
		UserAwardMap userAwardMap = null;
		if (customerId != null && userAwardId != null && operator != null) {
			userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
			userAwardMap.setUsedStatus(1);
			PersonInfo customer = new PersonInfo();
			customer.setUserId(customerId);
			userAwardMap.setUser(customer);
//			PersonInfo operator = new PersonInfo();
//			operator.setUserId(operatorId);
			userAwardMap.setOperator(operator);
//			userAwardMap.setUserId(customerId);
		}
		return userAwardMap;
	}


	/**
	 * 根据二维码携带的createTime判断其是否超过10分钟，超时则为过期
	 * 
	 * @param wechatInfo
	 * @return
	 */
	private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
		if (wechatInfo != null && wechatInfo.getShopId() != null && wechatInfo.getCreateTime() != null) {
			long nowTime = System.currentTimeMillis();
			if ((nowTime - wechatInfo.getCreateTime()) <= 600000) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	/**
	 * 获取扫描二维码的店员信息
	 * 
	 * @param request
	 * @return
	 */
	private WechatAuth getOperatorInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		WechatAuth auth = null;
		if (code != null) {
			UserAccessToken token;
			try {
				token = WechatUtil.getUserAccessToken(code);
				String openId = token.getOpenId();
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return auth;
	}

	
}
