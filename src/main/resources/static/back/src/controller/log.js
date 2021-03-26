/**
 * 日志中心
 * loo
 * 2018-12-27
 */
layui.define(['view', 'table', 'util', 'form', 'common'], function(exports) {
	var $ = layui.$,
		admin = layui.admin,
		table = layui.table,
		view = layui.view,
		setter = layui.setter,
		common = layui.common,
		form = layui.form;

	table.render({
		elem: '#LAY-log-handle-list',
		id: 'LAY-log-handle-list',
		url: layui.setter.apiUrl + '/cms/log/handle/list',
		title: '日志',
		height: 'full-180', //高度最大化减去差值
		request: {
			pageName: 'currentPage' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		page: true,
		limit: 15,
		headers: {
			access_token: layui.data(setter.tableName)[setter.request.tokenName]
		},
		response: {
			statusCode: 200
		},
		cols: [
			[{
				title: '序号',
				type: 'numbers',
				align: 'center'
			}, {
				field: 'username',
				title: '用户',
				align: 'center'
			}, {
				field: 'ip',
				title: 'IP',
				align: 'center',
			}, {
				field: 'operation',
				title: '操作',
				align: 'center'
			}, {
				field: 'url',
				title: '地址',
				align: 'center'
			}, {
				field: 'param',
				title: '参数',
				align: 'center'
			}, {
				field: 'createTime',
				title: '时间',
				align: 'center'
			}]
		]
	});

	table.render({
		elem: '#LAY-log-login-list',
		id: 'LAY-log-login-list',
		url: layui.setter.apiUrl + '/cms/log/login/list',
		title: '日志',
		height: 'full-180', //高度最大化减去差值
		request: {
			pageName: 'currentPage' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		page: true,
		limit: 15,
		headers: {
			access_token: layui.data(setter.tableName)[setter.request.tokenName]
		},
		response: {
			statusCode: 200
		},
		cols: [
			[{
				title: '序号',
				type: 'numbers',
				align: 'center'
			}, {
				field: 'username',
				title: '用户',
				align: 'center'
			}, {
				field: 'ip',
				title: 'IP',
				align: 'center',
			}, {
				field: 'type',
				title: '类型',
				align: 'center'
			}, {
				field: 'createTime',
				title: '时间',
				align: 'center'
			}]
		]
	});

	//监听行工具事件
	exports('log', {});
});