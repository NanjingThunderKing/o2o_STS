$(function() {
	// 从地址栏URL获取awardId参数
	var awardId = getQueryString('awardId');
	// 获取奖品信息的URL
	var awardUrl = '/o2o/frontend/listawarddetailpageinfo?awardId='
			+ awardId;
	// 访问后台获取商品信息并渲染
	$
			.getJSON(
					awardUrl,
					function(data) {
						if (data.success) {
							// 获取奖品信息
							var award = data.award;
							// 给奖品信息相关HTML控件赋值
							
							// 奖品缩略图
							$('#award-img').attr('src', getContextPath() + award.awardImg);
							// 奖品更新时间
							$('#award-time').text(
									new Date(award.lastEditTime)
											.Format("yyyy-MM-dd"));
							if(award.point != undefined) {
								$('#award-point').text('兑换需' + award.point + '积分');
							}
							// 奖品名称
							$('#award-name').text(award.awardName);
							// 奖品简介
							$('#award-desc').text(award.awardDesc);
//							$('#product-normalPrice').text(product.normalPrice);
//							$('#product-promotionPrice').text(product.promotionPrice);
//							var imgListHtml = '';
//							product.productImgList.map(function(item, index) {
//								imgListHtml += '<div> <img src="'
//									+ getContextPath() + item.imgAddr
//									+ '" width="100%"/></div>';
//							});
							// 2.0新增
//							if(data.needQRCode) {
//								// 若顾客已登录，生成购买商品的二维码供商家扫描
//								imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
//									+ product.productId
//									+ '" width="100%"/></div>';
//							}
//							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
