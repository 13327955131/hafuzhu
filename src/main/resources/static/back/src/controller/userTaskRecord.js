/**
 * 问题反馈
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'laydate', 'selectLink', 'common', 'upload'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        setter = layui.setter,
        laydate = layui.laydate,
        selectLink = layui.selectLink,
        common = layui.common,
        upload = layui.upload,
        form = layui.form;


    //执行一个laydate实例
    laydate.render({
        elem: '#test1' //指定元素
        ,format: 'yyyyMMdd'
    });
    form.render();
    table.render({
        elem: '#LAY-userTaskRecord-list',
        id: 'LAY-userTaskRecord-list',
        url: layui.setter.apiUrl + '/hfz/userTaskRecord/list',
        title: '用户完成任务列表',
        height: 'full-218', //高度最大化减去差值
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
                    title: "序号",
                    type: 'numbers',
                    fixed: 'left',
                }, {
                field: 'userName',
                title: '用户昵称',
                align: 'center',
            }, {
                field: 'userPhone',
                title: '用户手机号',
                align: 'center',

            }, {
                field: 'type',
                title: '类型',    //1视频2下载3打开
                align: 'center',
                templet: function (d) {
                    if (d.type == 1) {
                        return "<span style='color: red'>视频</span>"
                    } else if (d.type == 2) {
                        return "<span style='color: blue'>浏览</span>"
                    }
                }
            }, {
                field: 'degree',
                title: '是否必做',
                align: 'center',
                templet: function (d) {
                    if (d.degree == 1) {
                        return "<span style='color: red'>查看</span>"
                    } else {
                        return "<span style='color: blue'>下载</span>"
                    }
                }
                /*  }, {
                      field: 'taskId',
                      title: '任务ID',
                      align: 'center',*/
            }, {
                field: 'completeTime',
                title: '完成时间',
                align: 'center',
            }
            ]
        ]
    });
//监听搜索
    form.on('submit(LAY-userTaskRecord-search)', function (data) {
        var field = data.field;
        //执行重载
        table.reload('LAY-userTaskRecord-list', {
            where: field
        });
    });


    exports('userTaskRecord', {});
});