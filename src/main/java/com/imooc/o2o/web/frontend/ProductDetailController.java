package com.imooc.o2o.web.frontend;

import java.io.IOException;
import java.net.URLEncoder;
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
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	
	@Autowired
	private ProductService productService;
	
//	private static String URLPREFIX = "https://open.weixin.qq.com/connect/oauth2/authorize?"
//			+ "appid=wxd7f6c5b8899fba83&"
//			+ "redirect_uri=115.28.159.6/myo2o/shop/adduserproductmap&"
//			+ "response_type=code&scope=snsapi_userinfo&state=";
//	private static String URLSUFFIX = "#wechat_redirect";

	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取前台传过来的productId
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		// 空值判断
		if (productId != -1) {
			// 根据productId获取商品信息，包含商品详情图列表
			product = productService.getProductById(productId);
			// 2.0新增
			PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			if (user == null) {
				modelMap.put("needQRCode", false);
			} else {
				modelMap.put("needQRCode", true);
			}
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}
	
	// 微信获取用户信息api前缀
	private static String urlPrefix;
	// 微信获取用户信息api终极爱你部分
	private static String urlMiddle; 
	// 微信获取用户信息api后缀
	private static String urlSuffix; 
	// 微信回传给的相应添加顾客商品映射信息的url
	private static String productMapUrl; 	
	
	@Value("${wechat.prefix}")
	public void setUrlPrefix(String urlPrefix) {
		ProductDetailController.urlPrefix = urlPrefix;
	}
	
	@Value("${wechat.middle}")
	public void setUrlMiddle(String urlMiddle) {
		ProductDetailController.urlMiddle = urlMiddle;
	}
	
	@Value("${wechat.suffix}")
	public void setUrlSuffix(String urlSuffix) {
		ProductDetailController.urlSuffix = urlSuffix;
	}
	
	@Value("${wechat.productmap.url}")
	public void setProductmapUrl(String productmapUrl) {
		ProductDetailController.productMapUrl = productmapUrl;
	}

	/**
	 * 生成商品的消费凭证二维码，供操作员扫描，证明已消费
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/generateqrcode4product", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4Product(HttpServletRequest request, 
															HttpServletResponse response) {
		// 获取前端传过来的商品Id
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		// 从Session中获取当前顾客的信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 空值判断
		if (productId != -1 && user != null && user.getUserId() != null) {
			// 获取当前时间戳，精确到毫秒，用以保证二维码时间有效性
			long timpStamp = System.currentTimeMillis();
			// 将商品id，顾客id和timpStamp传入content，赋值到state中，便于微信获取信息
			// 加上aaa是为了一会的在添加信息的方法里替换这些信息使用
			String content = "{aaaproductIdaaa:" + productId + ",aaacustomerIdaaa:"
					+ user.getUserId() + ",aaacreateTimeaaa:" + timpStamp + "}";
			try {
				// 将content的信息先进行base64编码以避免特殊字符干扰，拼接URL
				String longUrl = urlPrefix + productMapUrl + urlMiddle
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
