package com.imooc.o2o.web.frontend;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.imooc.o2o.dto.UserAwardMapExecution;
import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserAwardMap;
import com.imooc.o2o.enums.UserAwardMapStateEnum;
import com.imooc.o2o.service.AwardService;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.service.UserAwardMapService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class MyAwardController {
	
	@Autowired
	private UserAwardMapService userAwardMapService;
	@Autowired
	private AwardService awardService;
	@Autowired
	private PersonInfoService personInfoService;
	
	/**
	 * 根据顾客奖品映射Id获取单条顾客奖品的映射信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getawardbyuserawardid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardByUserAwardId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取前端传过来的userAwardId
		long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
		// 空值判断
		if (userAwardId > -1) {
			// 根据Id获取顾客奖品的映射信息，进而获取奖品Id
			UserAwardMap userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
			// 根据奖品Id获取奖品信息
			Award award = awardService.getAwardById(userAwardMap.getAward().getAwardId());
			// 将奖品信息和领取状态返回给前端
			modelMap.put("award", award);
			modelMap.put("usedStatus", userAwardMap.getUsedStatus());
			modelMap.put("userAwardMap", userAwardMap);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty userAwardId ! ");
		}
		return modelMap;
	}
	
	
	/**
	 * 在线兑换礼品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adduserawardmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addUserAwardMap(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 从Session中获取用户信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 从session中获取当前的店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 从前端请求中获取奖品Id
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		// 封装成用户奖品映射对象
		UserAwardMap userAwardMap = compactUserAwardMap4Add(user, awardId, currentShop);
		// 空值判断
		if (userAwardMap != null) {
			try {
				// 添加兑换信息
				UserAwardMapExecution se = userAwardMapService.addUserAwardMap(userAwardMap);
				if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请选择领取的奖品");
		}
		return modelMap;
	}
	
	/**
	 * 获取顾客的兑换列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listuserawardmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByCustomer(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取分页信息
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 从session中获取用户信息
		// 从Session中获取用户信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 空值校验
		if ((pageIndex > -1) && (pageSize > -1) && (user != null)
																&& (user.getUserId() != null)) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setUser(user);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if (shopId > -1) {
				// 若店铺id为非空，则将其添加进查询条件，即查询用户在某个店铺的兑换信息
				Shop shop = new Shop();
				shop.setShopId(shopId);
				userAwardMapCondition.setShop(shop);
			}
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			if (awardName != null) {
				// 若奖品名非空，也添加进查询条件
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMapCondition.setAward(award);
			}
			// 根据传入的查询条件分页获取用户奖品映射信息
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(
														userAwardMapCondition, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or userId");
		}
		return modelMap;
	}


	/**
	 * 封装成用户奖品映射对象方法具体实现
	 * 
	 * @param user
	 * @param awardId
	 * @param currentShop
	 * @return
	 */
	private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId,
																									Shop currentShop) {
		UserAwardMap userAwardMap = null;
		if (user != null && user.getUserId() != null && awardId != -1) {
			userAwardMap = new UserAwardMap();
//			PersonInfo personInfo = personInfoService.getPersonInfoById(user
//					.getUserId());
			Award award = awardService.getAwardById(awardId);
			userAwardMap.setUser(user);
			userAwardMap.setAward(award);
			userAwardMap.setShop(currentShop);
//			userAwardMap.setUserName(personInfo.getName());
//			userAwardMap.setAwardName(award.getAwardName());
			userAwardMap.setPoint(award.getPoint());
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(1);
		}
		return userAwardMap;
	}
	
	
	// 微信获取用户信息api前缀
	private static String urlPrefix;
	// 微信获取用户信息api终极爱你部分
	private static String urlMiddle; 
	// 微信获取用户信息api后缀
	private static String urlSuffix; 
	// 微信回传给的相应添加用户奖品映射信息的url
	private static String exchangeUrl; 	
	
	@Value("${wechat.prefix}")
	public void setUrlPrefix(String urlPrefix) {
		MyAwardController.urlPrefix = urlPrefix;
	}
	
	@Value("${wechat.middle}")
	public void setUrlMiddle(String urlMiddle) {
		MyAwardController.urlMiddle = urlMiddle;
	}
	
	@Value("${wechat.suffix}")
	public void setUrlSuffix(String urlSuffix) {
		MyAwardController.urlSuffix = urlSuffix;
	}
	
	@Value("${wechat.exchange.url}")
	public void setExchangeUrl(String exchangeUrl) {
		MyAwardController.exchangeUrl = exchangeUrl;
	}	
	
	/**
	 * 生成奖品的领取二维码，供操作员扫描，证明已领取
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/generateqrcode4award", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4Award(HttpServletRequest request, 
															HttpServletResponse response) {
		// 获取前端传过来的用户奖品映射Id
		long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
		// 根据Id获取顾客奖品映射实体类对象
		UserAwardMap userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
		// 从Session中获取当前顾客的信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 空值判断
		if (userAwardMap != null && user != null && user.getUserId() != null
				&& userAwardMap.getUser().getUserId() == user.getUserId()) {
			// 获取当前时间戳，精确到毫秒，用以保证二维码时间有效性
			long timpStamp = System.currentTimeMillis();
			// 将商品id，顾客id和timpStamp传入content，赋值到state中，便于微信获取信息
			// 加上aaa是为了一会的在添加信息的方法里替换这些信息使用
			String content = "{aaauserAwardIdaaa:" + userAwardId + ",aaacustomerIdaaa:"
					+ user.getUserId() + ",aaacreateTimeaaa:" + timpStamp + "}";
			try {
				// 将content的信息先进行base64编码以避免特殊字符干扰，拼接URL
				String longUrl = urlPrefix + exchangeUrl + urlMiddle
										+ URLEncoder.encode(content, "UTF-8") + urlSuffix;
				// 将URL由长变短
//				String shortUrl = ShortNetAddressUtil.getShortURL(longUrl);
				String shortUrl = "https://dwz.cn/Oq9T5yjh";
				// 用短URL生成二维码
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
				// 将二维码以图片流的形式输出到前端
				MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
	}
	

}
