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
        elem: '#LAY-luckDrawRecord-list',
        id: 'LAY-luckDrawRecord-list',
        url: layui.setter.apiUrl + '/hfz/luckDrawRecord/list',
        title: '列表',
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



//监听搜索
    form.on('submit(LAY-luckDrawRecord-search)', function(data) {
        var field = data.field;


        //执行重载
        table.reload('LAY-luckDrawRecord-list', {
            where: field
        });
    });




    exports('luckDrawRecord', {});
});