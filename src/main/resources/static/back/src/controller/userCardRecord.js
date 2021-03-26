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
        elem: '#LAY-userCardRecord-list',
        id: 'LAY-userCardRecord-list',
        url: layui.setter.apiUrl + '/hfz/userCardRecord/list',
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
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    title: '序号',
                    fixed: 'left',
                }, {
                field: 'cardName',
                title: '福卡名称',
                align: 'center',
                style:"color:red"
            }, {
                field: 'userName',
                title: '用户',
                align: 'center',
             }, {
                field: 'userPhone',
                title: '用户手机号',
                align: 'center',

            }, {
                field: 'getWay',
                title: '获取方式',
                templet: function (d) {
                    if(d.getWay==1){
                        return "<span style='color: red'>抽奖</span>";
                    }else if(d.getWay==2){
                        return "<span style='color: blue'>商城</span>";
                    }else if(d.getWay==3){
                        return "<span style='color: #FFB800'>合成</span>";
                    }
                }
                }, {
                field: 'type',
                title: '获取方式',
                templet: function (d) {
                    if(d.type==1){
                        return "<span style='color: red'>新增</span>";
                    }else if(d.type==2){
                        return "<span style='color: blue'>减少</span>";
                    }
                }
            }, {
                field: 'getTime',
                title: '获取时间',
                align: 'center',
            }
            ]
        ]
    });





    //监听行工具事件
    table.on('tool(LAY-userCardRecord-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '类型编辑',
                area: ['600px', '500px'],
                success: function (layero, index) {
                    view(this.id).render('card/userCardRecord/detail', data).done(function () {
                        //更新
                        form.on('submit(LAY-userCardRecord-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/userCardRecord/update',
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
                                        table.reload("LAY-userCardRecord-list")
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
                    url: layui.setter.apiUrl + '/hfz/userCardRecord/delete',
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
                            table.reload("LAY-userCardRecord-list")
                        });
                    }
                });
            });
        }
    });


//监听搜索
    form.on('submit(LAY-userCardRecord-search)', function(data) {
        var field = data.field;

        //执行重载
        table.reload('LAY-userCardRecord-list', {
            where: field
        });
    });






    exports('userCardRecord', {});
});