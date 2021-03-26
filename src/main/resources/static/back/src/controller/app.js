/**
 * APP管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        form = layui.form,
        setter = layui.setter;

    table.render({
        elem: '#LAY-app-list',
        id: 'LAY-app-list',
        url: layui.setter.apiUrl + '/cms/app/list',
        title: 'APP',
        limit: 12,
        page: true,
        height: 'full-260', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("app:add") + batchDelBtn("app:delete") + '</script>',
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
            [{
                type: 'checkbox',
            }, {
                field: 'appName',
                title: '名称',
                align: 'center',
                width: 120
            }, {
                title: '类型',
                align: 'center',
                width: 100,
                templet: function (d) {
                    if (d.appType === 1) {
                        return "改之理";
                    } else if (d.appType === 2) {
                        return "打包";
                    }
                }
            }, {
                field: 'appPackageName',
                title: '包名',
                align: 'center',
                width: 200,
            }, {
                field: 'marketUrl',
                title: '市场地址',
                align: 'center',
                width: 250,
            }, {
                field: 'marketAccount',
                title: '市场账号',
                sort: true,
                align: 'center',
                width: 120,
            }, {
                title: '使用人',
                align: 'center',
                width: 120,
                templet: function (d) {
                    if (d.nickName === null) {
                        return "无";
                    } else {
                        return d.nickName;
                    }
                }
            }, {
                title: '使用账号',
                align: 'center',
                width: 120,
                templet: function (d) {
                    if (d.usedAccount === null) {
                        return "无";
                    } else {
                        return d.usedAccount;
                    }
                }
            }, {
                title: '状态',
                field: 'isPaid',
                align: 'center',
                width: 100,
                sort: true,
                templet: function (d) {
                    if (d.isPaid === 1) {
                        return "<span style='color:green'>正常</span>";
                    } else if (d.isPaid === 2) {
                        return "<span style='color:red'>已下线</span>";
                    }
                }
            }, {
                field: 'isUsed',
                title: '使用状态',
                width: 100,
                align: 'center',
                sort: true,
                templet: function (d) {
                    if (d.isUsed === 1) {
                        return "<span style='color:green'>未使用</span>";
                    } else if (d.isUsed === 2) {
                        return "<span style='color:red'>已使用</span>";
                    }
                }
            }, {
                align: 'center',
                width: 350,
                templet: function (d) {
                    var btn = "";
                    if (d.isUsed === 1 && d.isPaid === 1) {
                        btn += useBtn("app:use");
                    }

                    if (d.isPaid === 1) {
                        btn += offBtn("app:off");
                    }

                    if (d.isUsed === 2) {
                        btn += certBtn("app:cert");
                    }

                    return btn + editBtn("app:update") + delBtn("app:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-app-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-app-detail',
                    title: 'APP新增',
                    area: ['700px', '650px'],
                    success: function (layero, index) {
                        view(this.id).render('app/detail', data).done(function () {
                            form.render();
                            form.on('submit(LAY-app-submit)', function (obj) {

                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/app/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            admin.events.refresh();
                                        });
                                    }
                                });
                            });
                        });
                    }
                });
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除行么', function (index) {
                        var ids = [];
                        layui.each(data, function (index, item) {
                            ids.push(item.id)
                        })
                        //向服务端发送删除指令
                        admin.req({
                            url: layui.setter.apiUrl + '/cms/app/delete',
                            type: 'get',
                            data: {
                                ids: ids
                            },
                            success: function (res) {
                                layer.msg(res.msg, {
                                    offset: '15px',
                                    icon: 1,
                                    time: 500
                                }, function () {
                                    layer.close(index);
                                    admin.events.refresh();
                                });
                            }
                        });
                    });
                }
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(LAY-app-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-app-detail',
                title: 'APP编辑',
                area: ['700px', '650px'],
                success: function (layero, index) {
                    view(this.id).render('app/detail', data).done(function () {
                        form.render();
                        form.on('submit(LAY-app-submit)', function (obj) {
                            if (obj.field.status === "on") {
                                obj.field.status = 1;
                            } else {
                                obj.field.status = -1;
                            }
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/cms/app/update',
                                type: 'post',
                                data: obj.field,
                                success: function (res) {
                                    layer.close(index);
                                    layer.msg(res.msg, {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1500
                                    }, function () {
                                        admin.events.refresh();
                                    });
                                }
                            });
                        });
                    });
                }
            });
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var ids = [];
                ids.push(data.id);
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/cms/app/delete',
                    type: 'get',
                    data: {
                        ids: ids
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            admin.events.refresh();
                        });
                    }
                });
            });
        } else if (layEvent === 'use') {
            admin.popup({
                id: 'LAY-app-detail',
                title: 'APP使用',
                area: ['500px', '500px'],
                success: function (layero, index) {
                    view(this.id).render('app/use', data).done(function () {
                        form.render();
                        form.on('submit(LAY-app-submit)', function (obj) {
                            obj.field.id = data.id;
                            obj.field.isUsed = 2;
                            admin.req({
                                url: layui.setter.apiUrl + '/cms/app/use',
                                type: 'post',
                                data: obj.field,
                                success: function (res) {
                                    layer.close(index);
                                    layer.msg(res.msg, {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1500
                                    }, function () {
                                        admin.events.refresh();
                                    });
                                }
                            });
                        });
                    });
                }
            });
        } else if (layEvent === 'off') {
            layer.confirm('真的下线该应用吗？', function (index) {
                //向服务端发送指令
                admin.req({
                    url: layui.setter.apiUrl + '/cms/app/off',
                    type: 'post',
                    data: {
                        id: data.id,
                        isPaid: 2
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            admin.events.refresh();
                        });
                    }
                });
            });
        } else if (layEvent === 'cert') {
            window.open("http://cdn.loocms.com/loo.jks");
        }
    });

    //监听搜索
    form.on('submit(LAY-app-search)', function (data) {
        var field = data.field;
        //执行重载
        table.reload('LAY-app-list', {
            where: field
        });
    });

    exports('app', {});
});