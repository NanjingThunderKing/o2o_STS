$(function() {
	// 从URL中获取awardId参数
	var awardId = getQueryString('awardId');
//	var shopId = 1;
	// 通过awardId获取奖品信息的URL
	var infoUrl = '/o2o/shopadmin/getawardbyid?awardId=' + awardId;
	// 更新奖品信息URL
	var awardPostUrl = '/o2o/shopadmin/modifyaward';
	// 由于奖品添加和编辑使用的是同一个页面
	// 该标识用来表明是添加还是编辑操作
	var isEdit = false;
	if (awardId) {
		// 若有awardId则为编辑操作
		getInfo(awardId);
		isEdit = true;
	} else {
		awardPostUrl = '/o2o/shopadmin/addaward';
	}
	
//	$("#pass-date").calendar({
//	    value: ['2017-12-31']
//	});
	
	// 获取需要编辑的奖品信息，并赋值给表单
	function getInfo(id) {
		$.getJSON(infoUrl, function(data) {
			if (data.success) {
				// 从返回的JSON中获取award对象的信息，并赋值给表单
				var award = data.award;
				$('#award-name').val(award.awardName);
				$('#priority').val(award.priority);
				$('#award-desc').val(award.awardDesc);
				$('#point').val(award.point);
			}
		});
	}

	// 提交按钮的事件响应，分别对应奖品添加和编辑功能
	$('#submit').click(function() {
		// 创建商品json对象，并从表单中获取相应的属性值
		var award = {};
		award.awardName = $('#award-name').val();
		award.priority = $('#priority').val();
		award.awardDesc = $('#award-desc').val();
		award.point = $('#point').val();
		award.awardId = awardId ? awardId : '';
//		award.expireTime = $('#pass-date').val();
//		console.log(award.expireTime);
		// 获取缩略图文件流
		var thumbnail = $('#small-img')[0].files[0];
		var formData = new FormData();
		formData.append('thumbnail', thumbnail);
		formData.append('awardStr', JSON.stringify(award));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : awardPostUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					$('#captcha_img').click();
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

});