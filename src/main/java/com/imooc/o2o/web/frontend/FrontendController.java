package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
	
	/**
	 * 商品列表路由
	 * @return
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	private String showShopList() {
		return "frontend/shoplist";
	}
	
	/**
	 * 首页路由
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}
	
	/**
	 * 店铺详情路由
	 * @return
	 */
	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
	private String showShopDetail() {
		return "frontend/shopdetail";
	}

	/**
	 * 商品详情路由
	 * @return
	 */
	@RequestMapping(value = "/productdetail", method = RequestMethod.GET)
	private String showProductDetail() {
		return "frontend/productdetail";
	}	
	
	/**
	 * 奖品详情路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/awarddetail", method = RequestMethod.GET)
	private String showAwardDetail() {
		return "frontend/awarddetail";
	}		
	
	/**
	 * 店铺的奖品列表页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/awardlist", method = RequestMethod.GET)
	private String showAwardList() {
		return "frontend/awardlist";
	}	
	
	/**
	 * 奖品兑换列表页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
	private String showPointRecord() {
		return "frontend/pointrecord";
	}	
	
	/**
	 * 我的奖品领取详情路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/myawarddetail", method = RequestMethod.GET)
	private String myAwardDetail() {
		return "frontend/myawarddetail";
	}	

	/**
	 * 消费记录列表页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/myrecord", method = RequestMethod.GET)
	private String myRecord() {
		return "frontend/myrecord";
	}		
	
	/**
	 * 用户各店铺积分信息页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mypoint", method = RequestMethod.GET)
	private String myPoint() {
		return "frontend/mypoint";
	}		
	
}
