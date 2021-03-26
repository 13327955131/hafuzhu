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
        selectLink = layui.selectLink,
        common = layui.common,
        upload = layui.upload,
        form = layui.form;

    table.render({
        elem: '#LAY-balance-list',
        id: 'LAY-balance-list',
        url: layui.setter.apiUrl + '/hfz/userBalance/list',
        title: '金库',
        height: 'full-270', //高度最大化减去差值
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
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                    field: 'userName',
                    title: '昵称',
                    align: 'center'
                }, {
                    field: 'userPhone',
                    title: '用户手机号',
                    align: 'center'
                }, {
                    field: 'integral',
                    title: '积分',
                    align: 'center',
                    sort: true,
                }, {
                    field: 'profit',
                    title: '收益余额',
                    align: 'center',
                    sort: true,
                    templet: function (d) {
                        return  d.profit/100;
                    }
            }, {
                field: 'firstPickStatus',
                title: '第一次提现',
                align: 'center',
                templet: function (d) {
                    if (d.firstPickStatus == 2) {
                        return '已提现';
                    } else {
                        return '未提现';
                    }
                }
            }, {
                field: 'realNameStatus',
                title: '实名状态',
                align: 'center',
                templet: function (d) {
                    if (d.realNameStatus == 2) {
                        return '已实名';
                    } else {
                        return '未实名';
                    }
                }
            }, {
                field: 'dayTaskStatus',
                title: '每日必做任务完成状态',
                align: 'center',
                templet: function (d) {
                    if (d.dayTaskStatus == 2) {
                        return '已完成';
                    } else {
                        return '未完成';
                    }
                }
            }, {
                field: 'wxBindStatus',
                title: '微信绑定状态',
                align: 'center',
                templet: function (d) {
                    if (d.wxBindStatus == 2) {
                        return '已绑定';
                    } else {
                        return '未绑定';
                    }
                }
            }, {
                    fixed: 'right',
                    width: 160,
                    title: '操作',
                    align: 'center',
                    templet: function (d) {
                        return "<a class=\"layui-btn  layui-btn-warm layui-btn-xs event-btn\"  lay-event=\"useAll\"><i class=\"layui-icon layui-icon-radio\"></i>详情</a>" + editBtn("balance:update");
                    }
                }
            ]
        ]
    });


    //监听行工具事件
    table.on('tool(LAY-balance-list)', function (obj) {
        var data = obj.data,
            layEvent = obj.event; //获得 lay-event 对应的值

        //用户ID
        sessionStorage.setItem("userId", data.userId);
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-balance-detail',
                title: '用户编辑',
                area: ['400px', '300px'],
                success: function (layero, index) {
                    view(this.id).render('use/userBalance/detail', data).done(function () {
                        form.render();
                        //更新
                        form.on('submit(LAY-balance-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/userBalance/update',
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
                                        table.reload("LAY-balance-list")
                                    });
                                }
                            });
                        });
                    });
                }
            });

        }else if (layEvent === 'useAll') {  //用户全部信息
            admin.popup({
                id: 'LAY-admin-detail',
                title: '用户详细信息',
                area: ['1000px', '780px'],
                success: function (layero, index) {
                    view(this.id).render('use/user/userDetails', data).done(function () {

                    });
                }
            });
        }
    })

//监听搜索
    form.on('submit(LAY-userBalance-search)', function(data) {
        var field = data.field;


        //执行重载
        table.reload('LAY-balance-list', {
            where: field
        });
    });



    exports('userBalance', {});
});