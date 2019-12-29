package com.imooc.o2o.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.CacheService;
import com.imooc.o2o.service.HeadLineService;

@Controller
public class CacheController {
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
//	@Autowired
//	private HeadLineService headLineService;
	
	/**
	 * 当区域信息更新时，手动清除Redis缓存并将新信息更新
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clearcache4area", method = RequestMethod.GET)
	private String clearCache4Area() {
		cacheService.removeFromCache(areaService.AREALISTKEY);
		return "shop/operationsuccess";
	}
	
	

}
