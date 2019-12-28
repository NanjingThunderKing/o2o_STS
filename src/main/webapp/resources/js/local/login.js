$(function() {
	// 登录验证的controller url
	var loginUrl = '/o2o/local/logincheck';
	// 从地址栏的URL里获取usertype
	// usertype = 1 则为customer，其余为shopowner
	var usertype = getQueryString('usertype');
	// 登录次数，累计登录三次失败后自动弹出验证码要求重新输入
	var loginCount = 0;

	$('#submit').click(function() {
		// 获取输入账号
		var userName = $('#username').val();
		// 获取输入密码
		var password = $('#psw').val();
		// 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认false，即不需要
		var needVerify = false;
		// 如果登录三次都失败
		if (loginCount >= 3) {
			// 则重新进行验证码校验
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				// 是否需要验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					if(usertype == 1) {
						window.location.href = '/o2o/frontend/index';
					} else {
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast('登录失败！' + data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						// 登录失败三次，则重新进行验证码校验
						$('#verifyPart').show();
					}
				}
			}
		});
	});

//	$('#register').click(function() {
//		window.location.href = '/myo2o/shop/register';
//	});
});