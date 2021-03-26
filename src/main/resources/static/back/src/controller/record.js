/**
 * 用户管理--收益记录
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
        elem: '#LAY-record-list',
        id: 'LAY-record-list',
        url: layui.setter.apiUrl + '/hfz/record/list',
        title: '收益记录',
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
                field: 'amount',
                title: '红包金额(元)',
                align: 'center',
                templet:function (d) {
                    return d.amount/100
                }
            }, {
                title: '获取方式',
                align: 'center',
                templet:function (d) {
                    if (d.getWay==1){
                        return '抽奖'
                    } else if(d.getWay==2){
                        return '收益卡'
                    } else if(d.getWay==3){
                        return '签到'
                    } else if(d.getWay==4){
                        return '合成'
                    } else if(d.getWay==5){
                        return '实名'
                    } else if(d.getWay==6){
                        return '徒弟贡献'
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
   /* table.on('tool(LAY-record-list)', function (obj) {
        var data = obj.data ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-record-detail',
                title: '用户编辑',
                area: ['400px', '300px'],
                success: function (layero, index) {
                    view(this.id).render('use/userBalance/detail', data).done(function () {
                        form.render();
                        //更新
                        form.on('submit(LAY-record-submit)', function (obj) {
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
                                        table.reload("LAY-record-list")
                                    });
                                }
                            });
                        });
                    });
                }
            });

        }

    })*/






//监听搜索
    form.on('submit(LAY-record-search)', function(data) {
        var field = data.field;

        if(field.nickName==""){
            field.nickName=null;
        }
        //执行重载
        table.reload('LAY-record-list', {
            where: field
        });
    });




    exports('record', {});
});