/**
 * 用户管理--小金库
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'selectLink', 'common', 'upload'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        setter = layui.setter,
        common = layui.common,
        upload = layui.upload,
        form = layui.form;
    form.render();
    table.render({
        elem: '#LAY-task-list',
        id: 'LAY-task-list',
        url: layui.setter.apiUrl + '/hfz/task/list',
        title: '列表',
        height: 'full-220', //高度最大化减去差值
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
        toolbar: '<script type="text/html">' + addBtn("task:add")  + '</script>',
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    title: '序号',
                    type: 'numbers',
                    fixed: 'left',
                }, {
                field: 'type1',
                title: '必做/选做',
                align: 'center',
                templet: function (d) {
                    if(d.type1==1){
                        return "必做"
                    }else{
                        return "选做"
                    }
                }
            }, {
                field: 'taskNum',
                title: '数量',
                align: 'center',
            }, {
                field: 'type2',
                title: '任务类型',
                align: 'center',
                templet: function (d) {
                    if(d.type2==1){
                        return "视频"
                    }else{
                        return "浏览"
                    }
                }
            }, {
                field: 'num',
                title: '每日任务次数',
                align: 'center',
            }, {
                field: 'integral',
                title: '赠送积分',
                align: 'center',
            }, {
                field: 'url',
                title: '任务链接',
                align: 'center',
            }, {
                fixed: 'right',
                width: 200,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return editBtn("task:update") + delBtn("task:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-task-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '新增任务',
                    area: ['600px', '500px'],
                    success: function (layero, index) {
                        view(this.id).render('sys/task/detail', data).done(function () {
                            form.render();
                            form.on('submit(LAY-task-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/task/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload("LAY-task-list")
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
                            url: layui.setter.apiUrl + '/hfz/task/delete',
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
                                    table.reload("LAY-task-list")
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
    table.on('tool(LAY-task-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '类型编辑',
                area: ['600px', '500px'],
                success: function (layero, index) {
                    view(this.id).render('sys/task/detail', data).done(function () {
                        form.render();
                        //更新
                        form.on('submit(LAY-task-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/task/update',
                                type: 'post',
                                data: obj.field,
                                success: function (res) {
                                    layer.close(index);
                                    layer.msg(res.msg, {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1500
                                    }, function () {
                                        //渲染   重载表格
                                        table.reload("LAY-task-list")
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
                    url: layui.setter.apiUrl + '/hfz/task/delete',
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
                            table.reload("LAY-task-list")
                        });
                    }
                });
            });
        }
    });

//监听搜索
    form.on('submit(LAY-task-search)', function(data) {
        var field = data.field;


        //执行重载
        table.reload('LAY-task-list', {
            where: field
        });
    });




    exports('task', {});
});