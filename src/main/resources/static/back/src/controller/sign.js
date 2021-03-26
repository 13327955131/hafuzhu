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
        elem: '#LAY-sign-list',
        id: 'LAY-sign-list',
        url: layui.setter.apiUrl + '/hfz/sign/list',
        title: '商品列表',
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
                field: 'continueSignMonth',
                title: '当月累计签到天数',
                align: 'center',
            }, {
                field: 'signMonth',
                title: '签到月分',
                align: 'center',
            }, {
                field: 'lastSignTime',
                title: '最后签到时间',
                align: 'center',
            }, {
                field: 'integralReceive',
                title: '签到积分是否领取签到积分领取'  ,// // 0 未获取资格 1 否 2 是',
                align: 'center',
                templet: function (d) {
                    if(d.integralReceive==0){
                        return "<span style='color:red'>未获取资格</span>"
                    }else if(d.integralReceive==1){
                        return "<span style='color:blue'>否</span>"
                    }else if(d.integralReceive==2){
                        return '<span style=\'color:purple\'>是</span>';
                    }else{
                        return "未知"
                    }
                }
            }, {
                field: 'drawReceive',
                title: '抽奖领取'  ,// // 0 未获取资格 1 否 2 是',
                align: 'center',
                templet: function (d) {
                    if(d.drawReceive==0){
                        return "未获取资格"
                    }else if(d.drawReceive==1){
                        return "否"
                    }else if(d.drawReceive==2){
                        return '是';
                    }else{
                        return "未知"
                    }
                }
            }, {
                field: 'redPacketReceive',
                title: '现金红包领取 '  ,// // 0 未获取资格 1 否 2 是',
                align: 'center',
                templet: function (d) {
                    if(d.redPacketReceive==0){
                        return "未获取资格"
                    }else if(d.redPacketReceive==1){
                        return "否"
                    }else if(d.redPacketReceive==2){
                        return '是';
                    }else{
                        return "未知"
                    }
                }
            }
            ]
        ]
    });



//监听搜索
    form.on('submit(LAY-sign-search)', function(data) {
        var field = data.field;


        //执行重载
        table.reload('LAY-sign-list', {
            where: field
        });
    });




    exports('sign', {});
});