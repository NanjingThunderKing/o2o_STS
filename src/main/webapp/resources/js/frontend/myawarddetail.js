$(function() {
	// 从地址栏URL获取userAwardId参数
	var userAwardId = getQueryString('userAwardId');
	// 获取商品信息的URL
	var awardUrl = '/o2o/frontend/getawardbyuserawardid?userAwardId='
			+ userAwardId;
	// 访问后台获取商品信息并渲染
	$
			.getJSON(
					awardUrl,
					function(data) {
						if (data.success) {
							// 获取奖品信息
							var award = data.award;
							// 给奖品信息相关HTML控件赋值
							
							// 商品缩略图
							$('#award-img').attr('src', getContextPath() + award.awardImg);
							$('#create-time').text(
									new Date(data.userAwardMap.createTime)
											.Format("yyyy-MM-dd"));
//							if(product.point != undefined) {
//								$('#product-point').text('购买可得' + product.point + 'jifen');
//							}
							// 奖品名称
							$('#award-name').text(award.award-name);
							// 商品简介
							$('#award-desc').text(award.awardDesc);
//							$('#product-normalPrice').text(product.normalPrice);
//							$('#product-promotionPrice').text(product.promotionPrice);
							var imgListHtml = '';
//							product.productImgList.map(function(item, index) {
//								imgListHtml += '<div> <img src="'
//									+ getContextPath() + item.imgAddr
//									+ '" width="100%"/></div>';
//							});
							// 若未去实体店兑换实体奖品，生成兑换奖品的二维码供商家扫描
							if(data.usedStatus == 0) {
								// 若顾客已登录，生成购买商品的二维码供商家扫描
								imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4award?userAwardId='
									+ userAwardId
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
