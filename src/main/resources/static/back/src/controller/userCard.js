/**
 * 用户管理--福卡管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'selectLink', 'common', 'upload'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        setter = layui.setter,
        selectLink = layui.selectLink,
        common = layui.common,
        upload = layui.upload,
        form = layui.form;


    table.render({
        elem: '#LAY-userCard-list',
        id: 'LAY-userCard-list',
        url: layui.setter.apiUrl + '/hfz/userCard/list',
        title: '类型列表',
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
        toolbar: '<script type="text/html">' + addBtn("userCard:add") + batchDelBtn("userCard:delete") + '</script>',
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left',

            }, {
                field: 'userName',
                title: '用户',
                align: 'center',
            }, {
                field: 'userPhone',
                title: '用户手机号',
                align: 'center',
            }, {
                field: 'cardName',
                title: '福卡名称',
                align: 'center',
                style:"color:red"
            }, {
                field: 'cardNum',
                title: '福卡数量',
                align: 'center',
            }, {
                fixed: 'right',
                width: 100,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return ('<a class="layui-btn layui-btn-normal layui-btn-xs event-btn"  lay-event="edit"><i class="layui-icon layui-icon-edit"></i>明细</a>');
                }
            }
            ]
        ]
    });





    //监听行工具事件
    table.on('tool(LAY-userCard-list)', function (obj) {

        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        sessionStorage.setItem("cardUserId", data.userId);
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '查看福卡',
                area: ['800px', '650px'],
                success: function (layero, index) {
                    view(this.id).render('card/userCard/verify', data).done(function () {


/*


                        //更新
                        form.on('submit(LAY-userCard-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/userCard/update',
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
                                        table.reload("LAY-userCard-list")
                                    });
                                }
                            });
                        });*/

                    });
                }
            });
        } /*else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var ids = [];
                ids.push(data.id)
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/userCard/delete',
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
                            table.reload("LAY-userCard-list")
                        });
                    }
                });
            });
        }*/
    });


//监听搜索
    form.on('submit(LAY-userCard-search)', function(data) {
        var field = data.field;

        //执行重载
        table.reload('LAY-userCard-list', {
            where: field
        });
    });






    exports('userCard', {});
});