/**
 * 任务中心-任务劫持管理
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
        elem: '#LAY-rob-list',
        id: 'LAY-rob-list',
        url: layui.setter.apiUrl + '/cms/rob/list',
        title: '链接',
        page: true,
        limit: 15,
        height: 'full-180', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("rob:add") + batchDelBtn("rob:delete") + '</script>',
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
                    type: 'checkbox',
                }, {
                field: 'no',
                title: '劫持任务编号',
                align: 'center',
                width: 120,
            }, {
                field: 'num',
                title: '总劫持数',
                align: 'center',
                width: 100,
            }, {
                field: 'currentCount',
                title: '已劫持数',
                align: 'center',
                width: 100,
            }, {
                field: 'url',
                title: '跳转链接',
                align: 'center',

            }, {
                field: 'createTime',
                title: '创建时间',
                width: 200,
                align: 'center'
            }, {
                field: 'status',
                title: '状态',
                align: 'center',
                width: 100,
                templet: "#statusTpl"
            }, {
                align: 'center',
                width: 200,
                templet: function (d) {
                    return editBtn("rob:update") + delBtn("rob:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-rob-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-rob-detail',
                    title: '劫持新增',
                    area: ['600px', '450px'],
                    success: function (layero, index) {
                        view(this.id).render('task/rob/detail', data).done(function () {
                            form.render();
                            form.on('submit(LAY-rob-submit)', function (obj) {
                                if (obj.field.status == "on") {
                                    obj.field.status = 1;
                                } else {
                                    obj.field.status = -1;
                                }
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/rob/add',
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
                            url: layui.setter.apiUrl + '/cms/rob/delete',
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
    table.on('tool(LAY-rob-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-rob-detail',
                title: '劫持编辑',
                area: ['600px', '450px'],
                success: function (layero, index) {
                    view(this.id).render('task/rob/detail', data).done(function () {
                        form.render();
                        form.on('submit(LAY-rob-submit)', function (obj) {
                            if (obj.field.status === "on") {
                                obj.field.status = 1;
                            } else {
                                obj.field.status = -1;
                            }
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/cms/rob/update',
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
                ids.push(data.id)
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/cms/rob/delete',
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
        }
    });

    exports('rob', {});
});