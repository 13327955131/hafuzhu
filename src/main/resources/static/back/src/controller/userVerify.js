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


   var id= sessionStorage.getItem("cardUserId");
    table.render({
        elem: '#LAY-userVerify-list',
        id: 'LAY-userVerify-list',
        where:{
            userId:id,
        },
        url: layui.setter.apiUrl + '/hfz/userCardRecord/list',
        title: '收益记录',
        height: 'full-340', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
            ,
        },

        page: true,
        limit: 15,
        response: {
            statusCode: 200
        },
        cols: [
            [ //表头
                {
                    type: 'checkbox',
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
                field: 'getWay',
                title: '获取方式',
                templet: function (d) {
                    if(d.getWay==1){
                        return "抽奖";
                    }else{
                        return "商城";
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


    exports('userVerify', {});
});