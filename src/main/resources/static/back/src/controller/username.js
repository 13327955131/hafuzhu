/**
 * 任务中心-任务管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form','layedit','laydate'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        form = layui.form,
        setter = layui.setter,
       layedit = layui.layedit,
        laydate = layui.laydate;

//年月选择器
    laydate.render({
        elem: '#dateMonth'
        ,type: 'month'
    });
    form.render();
    table.render({
        elem: '#LAY-username-list',
        id: 'LAY-username-list',
        url: layui.setter.apiUrl + '/cms/username/list',
        title: '链接',
        limit: 15,
        page: true,
        totalRow: true,
        height: 'full-250', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("username:add") + batchDelBtn("username:delete") + '</script>',
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
                field: 'companyName',
                align: 'center',
                title: '公司名称',
                totalRowText: '合计:'
           }, {
                field: 'userName',
                align: 'center',
                title: '账号',
            }, {
                title: '账号状态',
                align: 'center',
                templet: function (d) {
                    if (d.status === 1) {
                        return "正常";
                    } else if (d.status === 2) {
                        return "封APP";
                    } else if (d.status === 3) {
                        return "封号";
                    }
                }
            }, {
                title: '公司状态',
                align: 'center',
                templet: function (d) {
                    if (d.companyStatus === 1) {
                        return "正常";
                    } else if (d.companyStatus === 2) {
                        return "营业执照未办理";
                    } else if (d.companyStatus === 3) {
                        return "已办理未开户";
                    }else{
                        return "error";
                    }
                }
             }, {
                title: 'APP状态',
                align: 'center',
                templet: function (d) {
                    if (d.appStatus === 1) {
                        return "待上传";
                    } else if (d.appStatus === 2) {
                        return "审核中";
                    } else if (d.appStatus === 3) {
                        return "已上传";
                    } else if (d.appStatus === 4) {
                        return "已下线";
                    }
                }}, {
                title: '开票状态',
                align: 'center',
                templet: function (d) {
                    if (d.lnvoiceStatus === 1) {
                        return "未开票";
                    } else if (d.lnvoiceStatus === 2) {
                        return "已开票";
                    } else if (d.lnvoiceStatus === 3) {
                        return "已完成";
                    } else if (d.lnvoiceStatus === 4) {
                        return "异常数据";
                    }
                }
             }, {
                field: 'month',
                title: '月份',
                align: 'center'
            }, {
                field: 'money',
                title: '金额',
                align: 'center',
                totalRow: true
            }, {
                align: 'center',
                title: '操作',
                width: 200,
                templet: function (d) {
                    return  seeBtn("username:see") + editBtn("username:update") + delBtn("username:delete");
                }
            }
            ]
        ]
    });
var usernameIds=0;

    //监听头工具栏事件
    table.on('toolbar(LAY-username-list)', function (obj) {
        var objs=obj;
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-comapny-detail',
                    title: '新增账号',
                    area: ['700px', '915px'],
                    success: function (layero, index) {
                        view(this.id).render('username/detail', data).done(function () {
                            form.render();
                            //日期
                            laydate.render({
                                elem: '#dates'
                            });
                            form.on('submit(LAY-username-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/username/insert',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload('LAY-username-list', {
                                                where: objs.field
                                            });
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
                            url: layui.setter.apiUrl + '/cms/username/deleteAll',
                            type: 'post',
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
                                    table.reload('LAY-username-list', {
                                        where: objs.field
                                    });
                                });
                            }
                        });
                    });
                }
                break;
        }
    });



    //监听行工具事件
    table.on('tool(LAY-username-list)', function (obj) {
        var objs=obj;

        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-username-detail',
                title: '公司编辑',
                area: ['700px', '915px'],
                success: function (layero, index) {
                    view(this.id).render('username/detail', data).done(function () {
                        form.render();
                        //日期
                        laydate.render({
                            elem: '#dates'
                        });
                        form.on('submit(LAY-username-submit)', function (obj) {
                            layer.confirm('确定要修改吗？', function (index) {
                                obj.field.id = objs.data.id;
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/username/update',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            layer.closeAll();
                                            table.reload('LAY-username-list', {
                                                where: objs.field
                                            });
                                        });
                                    }
                                });
                            })
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
                    url: layui.setter.apiUrl + '/cms/username/deleteAll',
                    type: 'post',
                    data: {
                        ids: ids
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            table.reload('LAY-username-list', {
                                where: objs.field
                            });
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
                    view(this.id).render('username/data', data).done(function () {
                        usernameIds=data.id;
                        table.render({
                            elem: '#LAY-username-data-list',
                            id: 'LAY-username-data-list',
                            toolbar: '<script type="text/html">' + addBtn("username:add") + '</script>',
                            url: layui.setter.apiUrl + '/cms/username/data/list',
                            where: {userNameId: data.id},
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
                                    field: 'realAmount',
                                    title: '完成数量',
                                    align: 'center',
                                    totalRow: true
                                }, {
                                    field: 'times',
                                    title: '日期',
                                    align: 'center'
                                }, {
                                    field: 'money',
                                    title: '金额',
                                    align: 'center',
                                    totalRow: true
                                },{
                                    align: 'center',
                                    title: '操作',
                                    width: 80,
                                    templet: function (d) {
                                        return   delBtn("username:delete");
                                    }
                                }
                                ]
                            ]
                        });
                    });
                }
            });
        }
    });
    //二级监听头工具栏事件   数量
    table.on('toolbar(LAY-username-data-list)', function (obj) {
        var objs=obj;
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-comapny-data-detail',
                    title: '每日数据',
                    area: ['700px', '500px'],
                    success: function (layero, index) {
                        view(this.id).render('username/pay', data).done(function () {
                            form.render();
                            //日期
                            laydate.render({
                                elem: '#dates'
                            });


                            form.on('submit(LAY-username-submit)', function (obj) {
                                obj.field.userNameId=usernameIds;
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/username/data/insert',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload('LAY-username-data-list', {
                                            });
                                            table.reload('LAY-username-list', {
                                            });
                                        });
                                    }
                                });
                            });
                        });
                    }
                });
                break;

        }
    });
    //二级监听行工具事件
    table.on('tool(LAY-username-data-list)', function (obj) {
        var objs=obj;

        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var ids = [];
                ids.push(data.id)
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/cms/username/data/deleteAll',
                    type: 'post',
                    data: {
                        ids: ids
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            table.reload('LAY-username-data-list', {});

                        });
                    }
                });
            });
        }
    });
//监听搜索
    //监听搜索
    form.on('submit(LAY-username-search)', function(data) {
        var field = data.field;
        if(field.companyName==''){
            field.companyName=null;
        }
        if(field.month==''){
            field.month=null;
        }

        //执行重载LAY-lnvoice-search
        table.reload('LAY-username-list', {
            where: field
        });
    });



    form.render();

    exports('username', {});
});