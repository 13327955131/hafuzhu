/**
 * 权限中心-菜单管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'authTree'], function(exports) {
	var $ = layui.$,
		admin = layui.admin,
		table = layui.table,
		view = layui.view,
		form = layui.form,
		setter = layui.setter,
		authTree = layui.authTree;

	table.render({
		elem: '#LAY-role-list',
		id: 'LAY-role-list',
		url: layui.setter.apiUrl + '/cms/role/list',
		title: '角色',
		page: true,
		height: 'full-180', //高度最大化减去差值
		request: {
			pageName: 'currentPage' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		headers: {
			access_token: layui.data(setter.tableName)[setter.request.tokenName]
		},
		response: {
			statusCode: 200
		},
		defaultToolbar: false,
		cols: [
			[ //表头
				{
					field: 'id',
					title: 'ID',
					align: 'center',
					width: 120
				}, {
					field: 'name',
					title: '名称',
					align: 'center',
				}, {
					fixed: 'right',
					align: 'center',
					templet: function(d) {
						return editBtn("role:update");
					}
				}
			]
		]
	});

	//监听行工具事件
	table.on('tool(LAY-role-list)', function(obj) {
		var data = obj.data //获得当前行数据
			,
			layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'edit') {
			admin.popup({
				id: 'LAY-menu-detail',
				area: ['700px', '550px'],
				success: function(layero, index) {
					view(this.id).render('permission/role/detail', data).done(function() {
						admin.req({
							url: layui.setter.apiUrl + '/cms/role/menu/list',
							dataType: 'json',
							data: {
								roleId: data.id
							},
							success: function(res) {
								var trees = authTree.listConvert(res.data.list, {
									primaryKey: 'id',
									startPid: 0,
									parentKey: 'parentId',
									nameKey: 'title',
									valueKey: 'id',
									checkedKey: res.data.checkedId
								});

								// 渲染时传入渲染目标ID，树形结构数据（具体结构看样例，checked表示默认选中），以及input表单的名字
								authTree.render('#LAY-auth-tree-index', trees, {
									layfilter: 'lay-check-auth',
									openchecked: true,
									dblshow: true,
									autowidth: true
								});
							}
						});
						form.on('submit(LAY-auth-tree-submit)', function(obj) {
							obj.field.id = data.id;
							var authIds = authTree.getChecked('#LAY-auth-tree-index');

							if(authIds.length <= 0) {
								layer.msg("请选择权限！", {
									icon: 2,
									time: 1500
								});
								return false;
							} else {
								obj.field.authIds = authIds;
							}

							admin.req({
								url: layui.setter.apiUrl + '/cms/role/update',
								type: 'post',
								data: obj.field,
								success: function(res) {
									layer.msg(res.msg, {
										offset: '15px',
										icon: 1,
										time: 1500
									}, function() {
										layer.close(index);
										admin.events.refresh();
									});
								}
							});
						});

					});
				}
			});
		}
	});

	exports('role', {});
});