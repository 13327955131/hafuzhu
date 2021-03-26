/**
 @Name：个人中心
 @Author：loo
 */
layui.define(['form', 'admin'], function (exports) {
	var $ = layui.$,
		setter = layui.setter,
		admin = layui.admin,
		form = layui.form;

	//自定义验证
	form.verify({
		nickname: function (value, item) { //value：表单的值、item：表单的DOM对象
			if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
				return '用户名不能有特殊字符';
			}
			if (/(^\_)|(\__)|(\_+$)/.test(value)) {
				return '用户名首尾不能出现下划线\'_\'';
			}
			if (/^\d+\d+\d$/.test(value)) {
				return '用户名不能全为数字';
			}
		},
		vercode: function (value, item) { //value：表单的值、item：表单的DOM对象
			if (value.toUpperCase() != layui.data(setter.tableName)["vercode"]) {
				return '验证码错误！';
			}
		}
		,
		pass: [
			/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
		],
		// TODO
		userName: function (value, item) {
			admin.req({
				url: ''
				, data: {
					userName: value
				}
				, success: function () {

				}
			});
		}
	});
	//对外暴露的接口
	exports('sys', {});
});