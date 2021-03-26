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
        elem: '#LAY-wx-link-list',
        id: 'LAY-wx-link-list',
        url: layui.setter.apiUrl + '/cms/wx/link/list',
        title: '链接',
        limit: 15,
        page: true,
        height: 'full-180', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("link:wx:add") + batchDelBtn("link:wx:delete") +  wxPushBtn("link:wx:wxPush")+'</script>',
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
                field: 'totalCount',
                title: '总浏览数',
                align: 'center',
                width: 100,
            }, {
                field: 'todayCount',
                title: '今日浏览数',
                align: 'center',
                width: 100,
            }, {
                field: 'url',
                title: '链接',
                align: 'center',
                minWidth: 250,
            }, {
                field: 'sspAccount',
                title: 'SSP账号',
                align: 'center',
                width: 180,
            }, {
                field: 'createTime',
                title: '创建时间',
                width: 180,
                align: 'center'
            }, {
                align: 'center',
                width: 280,
                templet: function (d) {
                    return seeBtn("link:wx:see") + editBtn("link:wx:update") + delBtn("link:wx:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-wx-link-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    title: '链接新增',
                    area: ['650px', '450px'],
                    success: function (layero, index) {
                        view(this.id).render('wx/link/detail', data).done(function () {
                            form.render();
                            form.on('submit(LAY-link-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/wx/link/add',
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
                            url: layui.setter.apiUrl + '/cms/wx/link/delete',
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
            case 'wxPush':

                admin.popup({
                    title: '模板消息推送',
                    area: ['650px', '550px'],
                    success: function (layero, index) {
                        view(this.id).render('wx/link/push', data).done(function () {
                            form.render();
                            form.on('submit(LAY-push-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/wx/push/wxSend/',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            layer.msg("发送成功了！");
                                        });
                                    }
                                });
                            });
                        });
                    }
                });
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(LAY-wx-link-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                title: '链接编辑',
                area: ['650px', '450px'],
                success: function (layero, index) {
                    view(this.id).render('wx/link/detail', data).done(function () {
                        form.render();
                        form.on('submit(LAY-link-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/cms/wx/link/update',
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
                    url: layui.setter.apiUrl + '/cms/wx/link/delete',
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
                    view(this.id).render('wx/link/data', data).done(function () {
                        table.render({
                            elem: '#LAY-link-data-list',
                            id: 'LAY-link-data-list',
                            url: layui.setter.apiUrl + '/cms/wx/link/data/list',
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

    exports('wxLink', {});
});