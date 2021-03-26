/**
 * 任务中心-任务管理
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
        elem: '#LAY-link-default-list',
        id: 'LAY-link-default-list',
        url: layui.setter.apiUrl + '/cms/link/default/list',
        title: '链接',
        limit: 15,
        page: true,
        height: 'full-180', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("link:default:add") + batchDelBtn("link:default:delete") + '</script>',
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
                title: '编号',
                align: 'center',
                width: 100,
            }, {
                field: 'type',
                title: '类型',
                align: 'center',
                width: 100,
                templet: function (d) {
                    if (d.type === 1) {
                        return "微信";
                    } else if (d.type === 2) {
                        return "验证码";
                    } else if (d.type === 3) {
                        return "APP";
                    }
                }
            }, {
                field: 'expectNum',
                title: '预计完成数',
                align: 'center',
                width: 100,
            }, {
                field: 'clickNum',
                title: '最大点击',
                align: 'center',
                width: 100,
            }, {
                field: 'todayCount',
                title: '今日完成数',
                align: 'center',
                width: 100,
            }, {
                title: '访问链接',
                align: 'center',
                width: 300,
                templet: function (d) {
                    if (d.type === 1) {
                        return "https://hoostec.com/wx?no=" + d.no;
                    } else if (d.type === 2) {
                        return "https://hoostec.com/yz?no=" + d.no;
                    } else {
                        return "";
                    }
                }
            }, {
                field: 'url',
                title: '链接',
                align: 'center',
                minWidth: 200,
            }, {
                field: 'createTime',
                title: '创建时间',
                width: 180,
                align: 'center'
            }, {
                align: 'center',
                width: 280,
                templet: function (d) {
                    return seeBtn("link:default:see") + editBtn("link:default:update") + delBtn("link:default:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-link-default-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-link-default-detail',
                    title: '链接新增',
                    area: ['650px', '650px'],
                    success: function (layero, index) {
                        view(this.id).render('task/link/default/detail', data).done(function () {
                            form.render();
                            form.on('submit(LAY-link-default-submit)', function (obj) {
                                if (obj.field.status === "on") {
                                    obj.field.status = 1;
                                } else {
                                    obj.field.status = -1;
                                }
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/link/default/add',
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
                            url: layui.setter.apiUrl + '/cms/link/default/delete',
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
    table.on('tool(LAY-link-default-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-link-default-detail',
                title: '链接编辑',
                area: ['650px', '650px'],
                success: function (layero, index) {
                    view(this.id).render('task/link/default/detail', data).done(function () {
                        form.render();
                        form.on('submit(LAY-link-default-submit)', function (obj) {
                            if (obj.field.status === "on") {
                                obj.field.status = 1;
                            } else {
                                obj.field.status = -1;
                            }
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/cms/link/default/update',
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
                    url: layui.setter.apiUrl + '/cms/link/default/delete',
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
        } else if (layEvent === 'see') {
            admin.popup({
                id: 'LAY-pay-default-detail',
                title: '每日数据',
                area: ['800px', '600px'],
                success: function (layero, index) {
                    view(this.id).render('task/link/default/data', data).done(function () {
                        table.render({
                            elem: '#LAY-link-default-data-list',
                            id: 'LAY-link-default-data-list',
                            url: layui.setter.apiUrl + '/cms/link/default/data/list',
                            where: {no: data.no},
                            page: false,
                            totalRow: true,
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
                                        type: 'numbers',
                                        title: '序号',
                                        width: 200,
                                        totalRowText: '合计:'
                                    }, {
                                    field: 'num',
                                    title: '完成数量',
                                    align: 'center',
                                    totalRow: true
                                }, {
                                    field: 'curDate',
                                    title: '日期',
                                    align: 'center'
                                }
                                ]
                            ]
                        });
                    });
                }
            });
        }
    });

    exports('linkDefault', {});
});