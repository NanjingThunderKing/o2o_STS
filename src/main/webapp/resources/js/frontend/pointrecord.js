$(function() {
	
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;
	// 获取该用户奖品领取记录的URL
	var listUrl = '/o2o/frontend/listuserawardmapsbycustomer';

	var pageNum = 1;
	var awardName = '';
	addItems(pageSize, pageNum);
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?pageIndex=' + pageIndex
				+ '&pageSize=' + pageSize + '&awardName=' + awardName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取总数
				maxItems = data.count;
				var html = '';
				data.userAwardMapList.map(function(item, index) {
					var status = '';
					// 根据usedStatus显示是否已在实体店领取过奖品
					if(item.usedStatus == 0) {
						status = "未领取";
					} else if (item.usedStatus == 1) {
						status = "已经成功领取";
					}
					html += '' + '<div class="card" data-award-id='
							+ item.userAwardId + '>'
							+ '<div class="card-header">' + item.shop.shopName
							+ '<span class="pull-right">' + status
							+ '</span> </div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.award.awardName
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.createTime).Format("yyyy-MM-dd")
							+ '</p>' + '<span>消费积分:' + item.point + '</span>'
							+ '</div>' + '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').remove();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
	
	$('.list-div')
	.on(
			'click',
			'.card',
			function(e) {
//				var userAwardId = e.currentTarget.dataset.userAwardId;
				window.location.href = '/o2o/frontend/myawarddetail?userAwardId='
						+ e.currentTarget.dataset.userAwardId;
			});

//	addItems(pageSize, pageNum);

	// 无极滚动
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	$('#search').on('change', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
