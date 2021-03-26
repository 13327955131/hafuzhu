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
        elem: '#LAY-keyword-list',
        id: 'LAY-keyword-list',
        url: layui.setter.apiUrl + '/cms/keyword/list',
        title: 'APP',
        limit: 15,
        page: true,
        height: 'full-180', //高度最大化减去差值
        toolbar: '<script type="text/html">' + addBtn("keyword:add") + batchDelBtn("keyword:delete") + '</script>',
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
                field: 'keyword',
                title: '关键词',
                align: 'center',
            }, {
                align: 'center',
                width: 300,
                templet: function (d) {
                    return editBtn("keyword:update") + delBtn("keyword:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-keyword-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.prompt({
                    formType: 2,
                    title: '请输关键词',
                    area: ['150px', '200px'] //自定义文本域宽高
                }, function (value, index, elem) {
                    console.log(value);
                    admin.req({
                        url: layui.setter.apiUrl + '/cms/keyword/add',
                        type: 'post',
                        data: {
                            keyword:value
                        },
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
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除行么', function (index) {
                        var ids = [];
                        layui.each(data, function (index, item) {
                            ids.push(item.id)
                        });
                        //向服务端发送删除指令
                        admin.req({
                            url: layui.setter.apiUrl + '/cms/keyword/delete',
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
    table.on('tool(LAY-keyword-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            layer.prompt({
                formType: 2,
                title: '请输入值',
                value: data.keyword,
                area: ['150px', '200px'] //自定义文本域宽高
            }, function (value, index, elem) {
                obj.field.keyword = value;
                admin.req({
                    url: layui.setter.apiUrl + '/cms/keyword/add',
                    type: 'post',
                    data: {
                        keyword:value
                    },
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
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var ids = [];
                ids.push(data.id)
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/cms/keyword/delete',
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

    exports('keyword', {});
});