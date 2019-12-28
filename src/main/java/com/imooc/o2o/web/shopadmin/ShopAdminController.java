package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/shopadmin", method={RequestMethod.GET})
public class ShopAdminController {
	
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
	
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	
	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	
	@RequestMapping(value = "/productcategorymanagement", method={RequestMethod.GET})
	public String productCategoryManage() {
		return "shop/productcategorymanagement";
	}
	
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		return "shop/productoperation";
	}
	
	@RequestMapping(value = "/productmanagement")
	public String productManagement() {
		return "shop/productmanagement";
	}
	
	@RequestMapping(value = "/shopauthmanagement")
	public String shopAuthManagement() {
		return "shop/shopauthmanagement";
	}
	
	@RequestMapping(value = "/shopauthedit")
	public String shopAuthEdit() {
		return "shop/shopauthedit";
	}
	
	@RequestMapping(value = "/operationsuccess")
	public String operationSuccess() {
		return "shop/operationsuccess";
	}
	
	@RequestMapping(value = "/operationfail")
	public String operationFail() {
		return "shop/operationfail";
	}
	
	@RequestMapping(value = "/productbuycheck")
	public String productBuyCheck() {
		return "shop/productbuycheck";
	}
	
	@RequestMapping(value = "/awardmanagement")
	public String awardManagement() {
		return "shop/awardmanagement";
	}
	
	@RequestMapping(value = "/awardoperation")
	public String awardOperation() {
		return "shop/awardoperation";
	}
	
	@RequestMapping(value = "/usershopcheck")
	public String userShopCheck() {
		// 店铺用户积分统计路由
		return "shop/usershopcheck";
	}
	
	@RequestMapping(value = "/awarddelivercheck")
	public String awardDeliverCheck() {
		// 店铺用户积分兑换路由
		return "shop/awarddelivercheck";
	}
	
}
