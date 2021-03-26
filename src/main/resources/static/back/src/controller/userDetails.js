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
    //获取用户id
    var id= sessionStorage.getItem("userId");

    table.render({
        elem: '#LAY-userBalance-list',
        id: 'LAY-userBalance-list',
        url: layui.setter.apiUrl + '/hfz/userBalance/list',
        title: '金库',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{
            userId:id,
        },
        page: true,
        limit: 10,
        headers: {
            access_token: layui.data(setter.tableName)[setter.request.tokenName]
        },
        response: {
            statusCode: 200
        },
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [{
                field: 'userName',
                title: '昵称',
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
                    return d.profit/100
                }
            }, {
                field: 'firstPickStatus',
                title: '第一次提现',
                align: 'center',
                templet: function (d) {
                    if (d.firstPickStatus == 2) {
                        return '<span style="color: red">已提现</span>';
                    } else {
                        return '<span style="color: blue">未提现</span>';
                    }
                }
            }, {
                field: 'realNameStatus',
                title: '实名状态',
                align: 'center',
                templet: function (d) {
                    if (d.realNameStatus == 2) {
                        return '<span style="color: red">已实名</span>';
                    } else {
                        return '<span style="color: blue">未实名</span>';
                    }
                }
            }, {
                field: 'dayTaskStatus',
                title: '每日必做任务完成状态',
                align: 'center',
                templet: function (d) {
                    if (d.dayTaskStatus == 2) {
                        return '<span style="color: red">已完成</span>';
                    } else {
                        return '<span style="color: blue">未完成</span>';
                    }
                }
            }, {
                field: 'wxBindStatus',
                title: '微信绑定状态',
                align: 'center',
                templet: function (d) {
                    if (d.wxBindStatus == 2) {
                        return '<span style="color: red">已绑定</span>';
                    } else {
                        return '<span style="color: blue">未绑定</span>';
                    }
                }
            }
            ]
        ]
    });
    //收益记录
    table.render({
        elem: '#LAY-userProfitRecord-list',
        id: 'LAY-userProfitRecord-list',
        url: layui.setter.apiUrl + '/hfz/record/selectProfitRecord',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{
            userId:id,
        },
        page: true,
        limit: 10,
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
                    fixed: 'left'
            }, {
                field: 'amount',
                title: '金额（分）',
                align: 'center',

            }, {
                field: 'status',
                title: '收益类型',
                align: 'center',
                templet: function (d) {

                    if(d.status==1){
                        return "待支付"
                    }else{
                        return "支付成功"
                    }
                }
            }, {
                field: 'type',
                title: '收益类型',
                align: 'center',
                templet: function (d) {

                    if(d.type==2){
                        return "<a style='color: red'>减少</a>"
                    }else{
                        return "<a style='color: blue'>增加</a>"
                    }
                }
            }, {
                field: 'msg',
                title: '收益获取方式',
                align: 'center',
            }, {
                field: 'applyTime',
                title: '提现申请时间',
                align: 'center',

            }
            ]
        ]
    });

    //用户积分明细
    table.render({
        elem: '#LAY-userIntegralRecord-list',
        id: 'LAY-userIntegralRecord-list',
        url: layui.setter.apiUrl + '/hfz/userIntegralRecord/list',
        title: '用户积分明细',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{
            userId:id,
        },
        page: true,
        limit: 10,
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
                    type: 'numbers',
                    title: '序号',
                    fixed: 'left'
                }, {
                field: 'userName',
                title: '用户昵称',
                align: 'center',
            }, {
                field: 'num',
                title: '数量',
                width:100,
                align: 'center',
            }, {
                field: 'type',
                title: '积分类型',
                align: 'center',
                sort: true,
                templet: function (d) {
                    if(d.type==1){
                        return "<a style='color: blue'>新增</a>"
                    }else{
                        return "<a style='color: red'>减少</a>"
                    }
                }
            }, {
                field: 'msg',
                title: '备注',
                align: 'center',
            }, {
                field: 'useTime',
                title: '时间',
                align: 'center',
            }
            ]
        ]
    });
    //卡片明细
    table.render({
        elem: '#LAY-userCardRecord-list',
        id: 'LAY-userCardRecord-list',
        url: layui.setter.apiUrl + '/hfz/userCardRecord/list',
        title: '类型列表',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        where:{
            userId:id,
        },
        page: true,
        limit: 10,
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
                fixed: 'left'
            }, {
                field: 'userName',
                title: '用户',
                align: 'center',
            }, {
                field: 'cardName',
                title: '福卡名称',
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

    // 抽奖记录
    table.render({
        elem: '#LAY-luckDrawRecord-list',
        id: 'LAY-luckDrawRecord-list',
        url: layui.setter.apiUrl + '/hfz/luckDrawRecord/list',
        title: '列表',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },

        page: true,
        limit: 10,
        headers: {
            access_token: layui.data(setter.tableName)[setter.request.tokenName]
        },
        where:{
            userId:id,
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
                field: 'prizeName',
                title: '抽奖结果',
                align: 'center',
            }, {
                field: 'drawTime',
                title: '抽奖时间',
                align: 'center',
            }
            ]
        ]
    });
    table.render({
        elem: '#LAY-userT-list',
        id: 'LAY-userT-list',
        url: layui.setter.apiUrl + '/hfz/use/list',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        page: true,
        limit: 10,
        headers: {
            access_token: layui.data(setter.tableName)[setter.request.tokenName]
        },
        where:{
            parentId:id,
        },
        response: {
            statusCode: 200
        },
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    title: '序号',
                    fixed: 'left'
                }, {
                field: 'realName',
                title: '姓名',
                align: 'center',
            }, {
                field: 'nickName',
                title: '昵称',
                align: 'center'

            }, {
                field: 'userStatus',
                width:100,
                title: '用户状态',
                align: 'center',
                templet: function (d) {
                    if(d.userStatus==2){return '<a style="color:red">黑名单</a>'}else{return '白名单'};
                }
            }, {
                field: 'registerTime',
                title: '创建时间',
                align: 'center',
                sort: true,
                templet: function (d) {
                    return layui.util.toDateString(d.registerTime, "yyyy-MM-dd HH:mm:ss");
                }

            }
            ]
        ]
    });

    table.render({
        elem: '#LAY-address-list',
        id: 'LAY-address-list',
        url: layui.setter.apiUrl + '/hfz/userAddress/list',
        height: 'full-380', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        page: true,
        limit: 10,
        headers: {
            access_token: layui.data(setter.tableName)[setter.request.tokenName]
        },
        where:{
            userId:id,
        },
        response: {
            statusCode: 200
        },
        cols: [
            [ //表头
                {
                    title: '序号',
                    type: 'numbers',
                    fixed: 'left'
                }, {
                field: 'name',
                title: '姓名',
                align: 'center',
            }, {
                field: 'phone',
                title: '手机号',
                align: 'center',
            }, {
                field: 'province',
                title: '省',
                align: 'center'
            }, {
                field: 'city',
                title: '市',
                align: 'center'
            }, {
                field: 'county',
                title: '县',
                align: 'center',
            }, {
                field: 'address',
                title: '地址',
                align: 'center',
            }
            ]
        ]
    });

    exports('userDetails', {});
});