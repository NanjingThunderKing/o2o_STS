$(function() {
//	var shopId = 1;
	// 获取该店铺下的奖品列表URL
	var listUrl = '/o2o/shopadmin/listawardsbyshop?pageIndex=1&pageSize=9999';
	// 设置奖品的可见状态		
	var changeUrl = '/o2o/shopadmin/modifyaward';
	getList(); 
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var awardList = data.awardList;
				var tempHtml = '';
				awardList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					tempHtml += '' + '<div class="row row-award">'
							+ '<div class="col-33">'
							+ item.awardName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.award-wrap').html(tempHtml);
			}
		});
	}

//	getList();

	function changeItem(awardId, enableStatus) {
		var award = {};
		award.awardId = awardId;
		award.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			$.ajax({
				url : changeUrl,
				type : 'POST',
				data : {
					awardStr : JSON.stringify(award),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}

	$('.award-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/o2o/shopadmin/awardoperation?awardId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							changeItem(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/o2o/frontend/awarddetail?awardId='
									+ e.currentTarget.dataset.id;
						}
					});

	$('#new').click(function() {
		window.location.href = '/o2o/shopadmin/awardoperation';
	});
});