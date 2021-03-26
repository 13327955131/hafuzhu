/**
 * 用户管理--小金库
 * loo
 * 2018-05-30
 */
layui.define(['view', 'laydate', 'table', 'util', 'form', 'selectLink', 'common', 'upload'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        setter = layui.setter,
        selectLink = layui.selectLink,
        common = layui.common,
        upload = layui.upload,
        form = layui.form,
        laydate = layui.laydate;
//常规用法
    laydate.render({
        elem: '#times1'
    });
    //常规用法
    laydate.render({
        elem: '#times2'
    });

    form.render();

    table.render({
        elem: '#LAY-pay-list',
        id: 'LAY-pay-list',
        url: layui.setter.apiUrl + '/hfz/pay/list',
        title: '金库',
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
        totalRow: true,
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    fixed: 'left',
                    totalRowText: '合计:',
                }, {
                field: 'amount',
                title: '金额(元)',
                align: 'center',
                sort: true,
                totalRow: true,
                templet:function (d) {
                    return d.amount/100
                }
            }, {
                field: 'dayTime',
                title: '日期',
                align: 'center',
            }
            ]
        ]
    });

    //搜索
    form.on('submit(LAY-pay-search)',function (data) {
        var field=data.field;

        var startTime= $.trim($("#times1").val());
        var endTime= $.trim($("#times2").val());



        table.reload('LAY-pay-list',{
            where:field,
        })

    })










    exports('payDay', {});
});