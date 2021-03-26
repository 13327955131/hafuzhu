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
        elem: '#LAY-warn-list',
        id: 'LAY-warn-list',
        url: layui.setter.apiUrl + '/hfz/sys/warnList',
        title: '警告列表',
        height: 'full-210', //高度最大化减去差值
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
        toolbar: '<script type="text/html">' + addBtn("warn:add") + batchDelBtn("warn:delete") + '</script>',
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    title: '序号',
                    align: 'center',
                }, {
                field: 'userName',
                title: '用户',
                align: 'center',

            }, {
                field: 'msg',
                title: '警告消息',
                align: 'center',
            }, {
                field: 'handleStatus',
                title: '处理状态',  //处理状态 1 未处理 2 未处理
                align: 'center',
                templet: function (d) {
                    if(d.handleStatus==1){
                        return '<span style="color: red">未处理</span>'
                    }else if(d.handleStatus==2){
                        return '拉黑'
                    }else{
                        return '<span style="color: blue">警告</span>'
                    }
                }
            }, {
                field: 'createTime',
                title: '警告时间',
                align: 'center',
            }, {
                align: 'center',
                title: '处理',
                width: 120,
                templet: function (d) {
                    return '<a class="layui-btn  layui-btn-warm layui-btn-xs event-btn"  lay-event="see"><i class="layui-icon layui-icon-search"></i>点击处理</a>';
                }
            }
            ]
        ]
    });

    //监听行工具事件
    table.on('tool(LAY-warn-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'see') {

            var index=layer.confirm('怎么处理这个警告？', {
                btn: ['拉黑','警告'] //按钮
            }, function(){
                //回调

                //拉黑
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/sys/warnUserUpdate',
                    type: 'post',
                    data: data,
                    success: function (res) {
                        layer.close(index);
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 1500
                        }, function () {
                            //渲染   重载表格
                         //   table.reload("LAY-warn-list")
                        });
                    }
                });



            }, function(){
                //发送私信京警告
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/sys/messageInsert',
                    type: 'post',
                    data: data,
                    success: function (res) {
                        layer.close(index);
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 1500
                        }, function () {
                            //渲染   重载表格
                            table.reload("LAY-warn-list")
                        });
                    }
                });
            });

        }
    });



//监听搜索
    form.on('submit(LAY-warn-search)', function(data) {
        var field = data.field;

        //执行重载
        table.reload('LAY-warn-list', {
            where: field
        });
    });


    exports('warn', {});
});