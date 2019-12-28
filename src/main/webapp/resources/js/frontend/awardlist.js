$(function() {
	var loading = false;
	var maxItems = 999;
	var pageSize = 10;
	// 获取奖品列表的URL
	var listUrl = '/o2o/frontend/listawardsbyshop';
	// 兑换奖品的URL
	var exchangeUrl = '/o2o/frontend/adduserawardmap';
	var pageNum = 1;
	// 从地址栏URL里获取shopId
	var shopId = getQueryString('shopId');
	var awardName = '';
	var canProceed = false;
	var totalPoint = 0;
	// 预先加载20条
	addItems(pageSize, pageNum);

//	function getSearchDivData() {
//		var url = searchDivUrl + '?' + 'parentId=' + parentId;
//		$
//				.getJSON(
//						url,
//						function(data) {
//							if (data.success) {
//								var shopCategoryList = data.shopCategoryList;
//								var html = '';
//								html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
//								shopCategoryList
//										.map(function(item, index) {
//											html += '<a href="#" class="button" data-category-id='
//													+ item.shopCategoryId
//													+ '>'
//													+ item.shopCategoryName
//													+ '</a>';
//										});
//								$('#shoplist-search-div').html(html);
//								var selectOptions = '<option value="">全部街道</option>';
//								var areaList = data.areaList;
//								areaList.map(function(item, index) {
//									selectOptions += '<option value="'
//											+ item.areaId + '">'
//											+ item.areaName + '</option>';
//								});
//								$('#area-search').html(selectOptions);
//							}
//						});
//	}
//	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&shopId=' + shopId + '&awardName=' + awardName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取总数
				maxItems = data.count;
				var html = '';
				data.awardList.map(function(item, index) {
					html += '' + '<div class="card" data-award-id="'
							+ item.awardId + '" data-point="' + item.point
							+ '">' + '<div class="card-header">'
							+ item.awardName + '<span class="pull-right">需要积分'
							+ item.point + '</span> </div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath() + item.awardImg
							+ '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.awardDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>';
					if(data.totalPoint != undefined) {
						// 若用户有积分，则显示领取按钮
						html += '<span>点击领取</span> </div></div>';
					} else {
						html += '</div></div>';
					}
				});
				$('.list-div').append(html);
				if(data.totalPoint != undefined) {
					// 若用户在该店铺有积分，则显示
					html += '<span>点击领取</span> </div></div>';
					canProceed = true;
					$("#title").text('当前积分' + data.totalPoint);
					totalPoint = data.totalPoint;
				}
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 隐藏提示符
					$('.infinite-scroll-preloader').remove();
					return;
				} 
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
	// 预先加载20条
//	addItems(pageSize, pageNum);

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	$('.award-list').on('click', '.card', function(e) {
		// 若用户在该店铺所持有积分大于兑换奖品所需积分
		if(canProceed
				&& (totalPoint >= e.currentTarget.dataset.point)) {
			// 弹出确认操作框
			$.confirm('需要消耗' + e.currentTarget.dataset.point + '积分，确定操作么？'  , function() {
				$.ajax({
					url : exchangeUrl,
					type : 'POST',
					data : {
						awardId : e.currentTarget.dataset.awardId
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$.toast('操作成功！');
							totalPoint = totalPoint - e.currentTarget.dataset.point;
							$("#title").text('当前积分' + totalPoint);
						} else {
							$.toast('操作失败！');
						}
					}
				});
			});
		} else {
			$.toast('积分不足或无权限操作！');
		}
	});

//	$('#shoplist-search-div').on(
//			'click',
//			'.button',
//			function(e) {
//				if (parentId) {// 如果传递过来的是一个父类下的子类
//					shopCategoryId = e.target.dataset.categoryId;
//					if ($(e.target).hasClass('button-fill')) {
//						$(e.target).removeClass('button-fill');
//						shopCategoryId = '';
//					} else {
//						$(e.target).addClass('button-fill').siblings()
//								.removeClass('button-fill');
//					}
//					$('.list-div').empty();
//					pageNum = 1;
//					addItems(pageSize, pageNum);
//				} else {// 如果传递过来的父类为空，则按照父类查询
//					parentId = e.target.dataset.categoryId;
//					if ($(e.target).hasClass('button-fill')) {
//						$(e.target).removeClass('button-fill');
//						parentId = '';
//					} else {
//						$(e.target).addClass('button-fill').siblings()
//								.removeClass('button-fill');
//					}
//					$('.list-div').empty();
//					pageNum = 1;
//					addItems(pageSize, pageNum);
//					parentId = '';
//				}
//
//			});

	$('#search').on('change', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

//	$('#area-search').on('change', function() {
//		areaId = $('#area-search').val();
//		$('.list-div').empty();
//		pageNum = 1;
//		addItems(pageSize, pageNum);
//	});

	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	$.init();
});
