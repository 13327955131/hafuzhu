/**
 * 问题反馈
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
        elem: '#LAY-feedback-list',
        id: 'LAY-feedback-list',
        url: layui.setter.apiUrl + '/hfz/feedback/list',
        title: '反馈列表',
        height: 'full-180', //高度最大化减去差值
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
                field: 'type',
                title: '类型',
                align: 'center',
            }, {
                field: 'content',
                title: '内容',
                align: 'center',
            }, {
                field: 'contact',
                title: '联系方式',
                align: 'center',
            }, {
                field: 'createTime',
                title: '时间',
                align: 'center',
            }
            ]
        ]
    });



    exports('feedback', {});
});