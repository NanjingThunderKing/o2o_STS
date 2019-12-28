$(function() {
	// 从地址栏URL获取productId参数
	var productId = getQueryString('productId');
	// 获取商品信息的URL
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
			+ productId;
	// 访问后台获取商品信息并渲染
	$
			.getJSON(
					productUrl,
					function(data) {
						if (data.success) {
							// 获取商品信息
							var product = data.product;
							// 给商品信息相关HTML控件赋值
							
							// 商品缩略图
							$('#product-img').attr('src', getContextPath() + product.imgAddr);
							// 商品更新时间
							$('#product-time').text(
									new Date(product.lastEditTime)
											.Format("yyyy-MM-dd"));
							if(product.point != undefined) {
								$('#product-point').text('购买可得' + product.point + 'jifen');
							}
							// 商品名称
							$('#product-name').text(product.productName);
							// 商品简介
							$('#product-desc').text(product.productDesc);
							$('#product-normalPrice').text(product.normalPrice);
							$('#product-promotionPrice').text(product.promotionPrice);
							var imgListHtml = '';
							product.productImgList.map(function(item, index) {
								imgListHtml += '<div> <img src="'
									+ getContextPath() + item.imgAddr
									+ '" width="100%"/></div>';
							});
							// 2.0新增
							if(data.needQRCode) {
								// 若顾客已登录，生成购买商品的二维码供商家扫描
								imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
									+ product.productId
									+ '" width="100%"/></div>';
							}
							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
