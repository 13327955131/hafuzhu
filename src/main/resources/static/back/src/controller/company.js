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
        elem: '#LAY-company-list',
        id: 'LAY-company-list',
        url: layui.setter.apiUrl + '/cms/company/list',
        title: '链接',
        limit: 15,
        page: true,
        height: 'full-250', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("company:add") + batchDelBtn("company:delete") + '</script>',
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
                field: 'company',
                align: 'center',
                title: '公司名称',
           }, {
                field: 'legalPerson',
                align: 'center',
                title: '法人',
           }, {
                field: 'openingBank',
                title: '开户银行',
                align: 'center'
            }, {
                field: 'address',
                title: '营业地址',
                align: 'center'
             }, {
                field: 'times',
                title: '时间',
                align: 'center'
            }, {
                align: 'center',
                title: '操作',
                width: 150,
                templet: function (d) {
                    return  editBtn("link:update") + delBtn("link:delete");
                }
            }
            ]
        ]
    });


    //监听头工具栏事件
    table.on('toolbar(LAY-company-list)', function (obj) {
        var objs=obj;
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-comapny-detail',
                    title: '公司新增',
                    area: ['700px', '915px'],
                    success: function (layero, index) {
                        view(this.id).render('company/detail', data).done(function () {
                            form.render();
                            //日期
                            laydate.render({
                                elem: '#dates'
                            });
                            form.on('submit(LAY-company-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/company/insert',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload('LAY-company-list', {
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
                            url: layui.setter.apiUrl + '/cms/company/deleteAll',
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
                                    table.reload('LAY-company-list', {
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
    table.on('tool(LAY-company-list)', function (obj) {
        var objs=obj;

        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-company-detail',
                title: '公司编辑',
                area: ['700px', '915px'],
                success: function (layero, index) {
                    view(this.id).render('company/detail', data).done(function () {
                        form.render();
                        //日期
                        laydate.render({
                            elem: '#dates'
                        });

                            form.on('submit(LAY-company-submit)', function (obj) {
                                layer.confirm('确定要修改吗？', function (index) {
                                obj.field.id = objs.data.id;
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/company/update',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            layer.closeAll();
                                            table.reload('LAY-company-list', {
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
                    url: layui.setter.apiUrl + '/cms/company/deleteAll',
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
                            table.reload('LAY-company-list', {
                                where: objs.field
                            });
                        });
                    }
                });
            });
        }
    });


//监听搜索
    form.on('submit(LAY-app-search)', function(data) {
        var field = data.field;
        if(field.company==''){
            field.company=null;
        }
        if(field.times==''){
            field.times=null;
        }

        //执行重载
        table.reload('LAY-company-list', {
            where: field
        });
    });




    form.render();

    exports('company', {});
});