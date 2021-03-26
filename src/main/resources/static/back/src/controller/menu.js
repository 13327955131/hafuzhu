/**
 * 权限中心-菜单管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'iconPicker', 'treeGrid'], function(exports) {
	var $ = layui.$,
		admin = layui.admin,
		table = layui.table,
		view = layui.view,
		setter = layui.setter,
		form = layui.form,
		iconPicker = layui.iconPicker,
		treeGrid = layui.treeGrid;
	treeGrid.render({
		id: 'LAY-menu-list',
		elem: '#LAY-menu-list',
		url: layui.setter.apiUrl + '/cms/menu/list',
		idField: 'id', //必須字段
		height: 'full-180', //高度最大化减去差值
		treeId: 'id', //树形id字段名称
		treeUpId: 'parentId', //树形父id字段名称
		treeShowName: 'title', //以树形式显示的字段
		isFilter: false,
		iconOpen: false, //是否显示图标【默认显示】
		isOpenDefault: false, //节点默认是展开还是折叠【默认展开】
		loading: true,
		toolbar: '<script type="text/html">' + addBtn("menu:add") + '</script>',
		method: 'get',
		isPage: false,
		heightRemove: [".dHead", 230], //不计算的高度,表格设定的是固定高度，此项不生效
		height: '80%',
		headers: {
			access_token: layui.data(setter.tableName)[setter.request.tokenName]
		},
		request: {
			pageName: 'currentPage' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		response: {
			statusCode: 200 //规定成功的状态码，默认：0
		},
		cols: [
			[{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					field: 'title',
					title: '名称'
				}, {
					field: 'icon',
					title: '图标',
					align: 'center',
					templet: function(d) {
						return '<i class="layui-icon ' + d.icon + '"></i>  ';
					}
				}, {
					field: 'jump',
					title: '跳转',
					align: 'center'
				}, {
					field: 'route',
					title: '权限',
					align: 'center'
				}, {
					field: 'type',
					title: '类型',
					align: 'center',
					templet: function(d) {
						return d.type == 1 ? "<span class='layui-btn layui-btn-xs layui-btn-danger'>操作</span>" : "<span class='layui-btn layui-btn-xs  layui-btn-warm'>按钮</span>";
					}
				}, {
					field: 'sequence',
					title: '排序(点击修改)',
					edit: 'text',
					align: 'center'
				}, {
					title: '操作',
					align: 'center',
					templet: function(d) {
						return editBtn("menu:update") + delBtn("menu:delete");
					}
				}
			]
		],
		parseData: function(res) { //数据加载后回调
				return res;
			}
			// 单击
			,
		onClickRow: function(index, o) {}
			// 双击
			,
		onDblClickRow: function(index, o) {}
	});

	//监听行工具事件
	treeGrid.on('tool(LAY-menu-list)', function(obj) {
		var data = obj.data //获得当前行数据
			,
			layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'detail') {

		} else if(layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				var ids = [];
				ids.push(data.id)

				if(data.children) {
					layui.each(data.children, function(index, item) {
						ids.push(item.id)
					})
				}
				//向服务端发送删除指令
				admin.req({
					url: layui.setter.apiUrl + '/cms/menu/delete',
					type: 'get',
					data: {
						ids: ids
					},
					success: function(res) {
						layer.msg(res.msg, {
							offset: '15px',
							icon: 1,
							time: 1000
						}, function() {
							obj.del(); //删除对应行（tr）的DOM结构
							layer.close(index);
							admin.events.refresh();
						});

					}
				});
			});
		} else if(layEvent === 'edit') {
			admin.popup({
				id: 'LAY-menu-detail',
				area: ['600px', '550px'],
				success: function(layero, index) {
					view(this.id).render('permission/menu/detail', data).done(function() {
						form.render();
						iconPicker.render({
							// 选择器，推荐使用input
							elem: '#iconPicker',
							page: true,
							// 数据类型：fontClass/unicode，推荐使用fontClass
							type: 'fontClass',
							// 是否开启搜索：true/false
							search: true
						});
						// 设置选中图标
						iconPicker.checkIcon('iconPicker', data.icon ? data.icon : 'layui-icon-add-1');
						form.render();
						admin.req({
							url: layui.setter.apiUrl + '/cms/menu/list',
							data: {
								type: 1
							},
							success: function(res) {
								layui.each(res.data, function(index, item) {
									$("#parentMenu").append(new Option(item.title, item.id));
								});
								$("#parentMenu").val(data.parentId);
								form.render('select');
							}
						});

						//监听提交
						form.on('submit(LAY-menu-add)', function(data) {
							data.field.id = obj.data.id;
							// 使用状态
							if(data.field.isUse == "on") {
								data.field.isUse = 1;
							} else {
								data.field.isUse = -1;
							}
							// 展开状态
							if(data.field.spread == "on") {
								data.field.spread = 1;
							} else {
								data.field.spread = -1;
							}

							//提交修改
							admin.req({
								url: layui.setter.apiUrl + '/cms/menu/update',
								type: 'post',
								data: data.field,
								success: function(res) {
									layer.msg(res.msg, {
										offset: '15px',
										icon: 1,
										time: 1000
									}, function() {
										layer.close(index); //执行关闭
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

	//事件
	var active = {
		add: function() {
			admin.popup({
				id: 'LAY-menu-detail',
				area: ['600px', '550px'],
				success: function(layero, index) {
					view(this.id).render('permission/menu/detail').done(function() {
						form.render();
						iconPicker.render({
							// 选择器，推荐使用input
							elem: '#iconPicker',
							page: true,
							// 数据类型：fontClass/unicode，推荐使用fontClass
							type: 'fontClass',
							// 是否开启搜索：true/false
							search: true
						});
						// 设置选中图标
						iconPicker.checkIcon('iconPicker', 'layui-icon-add-1');
						form.render();
						admin.req({
							url: layui.setter.apiUrl + '/cms/menu/list',
							success: function(res) {
								layui.each(res.data, function(index, item) {
									$("#parentMenu").append(new Option(item.title, item.id));
								});
								form.render('select');
							}
						});

						//监听提交
						form.on('submit(LAY-menu-add)', function(data) {
							// 使用状态
							if(data.field.isUse == "on") {
								data.field.isUse = 1;
							} else {
								data.field.isUse = -1;
							}
							// 展开状态
							if(data.field.spread == "on") {
								data.field.spread = 1;
							} else {
								data.field.spread = -1;
							}

							//提交修改
							admin.req({
								url: layui.setter.apiUrl + '/cms/menu/add',
								type: 'post',
								data: data.field,
								success: function(res) {
									layer.msg(res.msg, {
										offset: '15px',
										icon: 1,
										time: 1000
									}, function() {
										layer.close(index); //执行关闭
										admin.events.refresh();
									});
								}
							});
						});
					});
				}
			});
		}
	};

	$('.event-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
	// 单元格编辑
	treeGrid.on('edit(LAY-menu-list)', function(obj) {
		var param = {
			sequence: obj.value,
			id: obj.data.id
		}
		admin.req({
			url: layui.setter.apiUrl + '/cms/menu/update',
			type: 'post',
			data: param,
			isLoad: true,
			success: function(res) {
				layer.msg(res.msg, {
					offset: '15px',
					icon: 1,
					time: 1000
				});
			}
		});
	});

	exports('menu', {});
});