/**
 * 任务中心-任务管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'laydate'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        form = layui.form,
        laydate = layui.laydate,
        setter = layui.setter;

    //日期范围
    laydate.render({
        elem: '#test6'
        , max: 0
        , range: true
    });

    table.render({
        elem: '#LAY-wx-pay-list',
        id: 'LAY-wx-pay-list',
        url: layui.setter.apiUrl + '/cms/wx/pay/list',
        title: '链接',
        limit: 15,
        page: true,
        height: 'full-250', //高度最大化减去差值
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
        cols: [[
            {
                field: 'no',
                title: '编号',
                align: 'center',
                width: 120
            }, {
                field: 'nickName',
                title: '昵称',
                align: 'center',
                width: 200
            }, {
                field: 'openId',
                title: '用户标识',
                align: 'center',
                width: 350
            }, {
                field: 'amount',
                title: '金额(元)',
                align: 'center',
                width: 120,
                templet: function (d) {
                    return d.amount / 100;
                }
            }, {
                field: 'partnerTradeNo',
                title: '订单号',
                align: 'center',
                width: 250
            }, {
                field: 'status',
                title: '支付状态',
                align: 'center',
                width: 120,
                templet: function (d) {
                    if (d.status === 1) {
                        return "<b style='color: blue'>待支付</b>";
                    } else if (d.status === 2) {
                        return "<b style='color: green'>成功</b>";
                    } else if (d.status === 3) {
                        return "<b style='color: red'>失败</b>";
                    }
                }
            }, {
                field: 'createTime',
                title: '时间',
                align: 'center',
                width: 150
            }, {
                field: 'describe',
                title: '描述',
                align: 'center'
            }
        ]]
    });

    form.on('submit(LAY-wx-pay-search)', function (data) {
        console.log(data);
        var time = data.field.time;
        var startTime, endTime;
        if (!isEmpty(time)) {
            startTime = time.split(" - ")[0].trim();
            endTime = time.split(" - ")[1].trim();
        }
        table.reload('LAY-wx-pay-list', {
            url: '/cms/wx/pay/list'
            , where: {
                startTime: startTime,
                endTime: endTime,
                status: data.field.status
            }
        });
        getData(startTime, endTime);

    });

    var getData = function (startTime, endTime) {
        admin.req({
            url: layui.setter.apiUrl + '/cms/wx/pay/data',
            type: 'get',
            data: {
                startTime: startTime,
                endTime: endTime
            },
            success: function (res) {
                $("#todaySum").text(res.data.todaySum / 100);
                $("#totalSum").text(res.data.totalSum / 100);
            }
        });
    };
    getData();
    exports('wxPay', {});
});