/**

 @Name：layuiAdmin 设置
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL
    
 */

layui.define(['form', 'upload'], function(exports) {
	var $ = layui.$,
		layer = layui.layer,
		laytpl = layui.laytpl,
		setter = layui.setter,
		view = layui.view,
		admin = layui.admin,
		form = layui.form,
		upload = layui.upload;

	var $body = $('body');

	form.render();

	//自定义验证
	form.verify({
		nickname: function(value, item) { //value：表单的值、item：表单的DOM对象
				if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
					return '用户名不能有特殊字符';
				}
				if(/(^\_)|(\__)|(\_+$)/.test(value)) {
					return '用户名首尾不能出现下划线\'_\'';
				}
				if(/^\d+\d+\d$/.test(value)) {
					return '用户名不能全为数字';
				}
			}

			//我们既支持上述函数式的方式，也支持下述数组的形式
			//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
			,
		pass: [
				/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
			]

			//确认密码
			,
		repass: function(value) {
			if(value !== $('#LAY_password').val()) {
				return '两次密码输入不一致';
			}
		}
	});

	//设置我的资料
	form.on('submit(setmyinfo)', function(obj) {
		//提交修改
		admin.req({
			url: layui.setter.apiUrl + '/common/user/info/update',
			method: "post",
			data: obj.field,
			success: function(res) {
				if(res.code == 200) {
					layer.msg(res.msg, {
						offset: '15px',
						icon: 1,
						time: 1500
					}, function() {
						admin.events.refresh();
					});
				}

			}
		});
		return false;
	});

	//上传头像
	var avatarSrc = $('#LAY_avatarSrc');
	upload.render({
		url: layui.setter.apiUrl + '/common/fileUpload/',
		headers: {
			access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
		},
		elem: '#LAY_avatarUpload',
		done: function(res) {
			if(res.code == 200) {
				avatarSrc.val(layui.setter.apiUrl + res.data.src);
			} else {
				layer.msg(res.msg, {
					icon: 5
				});
			}
		}
	});

	//查看头像
	admin.events.avartatPreview = function(othis) {
		var src = avatarSrc.val();
		layer.photos({
			photos: {
				"title": "查看头像" //相册标题
					,
				"data": [{
					"src": src //原图地址
				}]
			},
			shade: 0.01,
			closeBtn: 1,
			anim: 5
		});
	};

	//设置密码
	form.on('submit(setmypass)', function(obj) {

		admin.req({
			url: layui.setter.apiUrl + '/common/user/pwd/update',
			method: "post",
			data: obj.field,
			success: function(res) {
				if(res.code == 200) {
					layer.msg(res.msg, {
						offset: '15px',
						icon: 1,
						time: 1500
					}, function() {
						admin.events.refresh();
					});
				}

			}
		});
		return false;
	});

	//对外暴露的接口
	exports('set', {});
});