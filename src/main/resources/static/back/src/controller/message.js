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
        elem: '#LAY-message-list',
        id: 'LAY-message-list',
        url: layui.setter.apiUrl + '/hfz/message/list',
        title: '收获地址信息',
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
                    title: '序号',
                    type: 'numbers',
                    fixed: 'left'
                }, {
                    field: 'userName',
                    title: '用户',
                    align: 'center',
                }, {
                    field: 'title',
                    title: '标题',
                    align: 'center',
                }, {
                    field: 'content',
                    title: '内容',
                    align: 'center'
                }, {
                    field: 'readStatus',
                    title: '阅读状态',
                    align: 'center',
                    templet: function (d) {
                        if(d.readStatus==1){
                            return "未读"
                        }else{
                            return "已读"
                        }
                    }
                }, {
                    field: 'createTime',
                    title: '发送时间',
                    align: 'center',
                }
            ]
        ]
    });


    //监听行工具事件
    /* table.on('tool(LAY-message-list)', function (obj) {
         var data = obj.data ,
             layEvent = obj.event; //获得 lay-event 对应的值
         if (layEvent === 'edit') {
             admin.popup({
                 id: 'LAY-message-detail',
                 title: '用户编辑',
                 area: ['400px', '300px'],
                 success: function (layero, index) {
                     view(this.id).render('use/userBalance/detail', data).done(function () {
                         form.render();
                         //更新
                         form.on('submit(LAY-message-submit)', function (obj) {
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
                                         table.reload("LAY-message-list")
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
    form.on('submit(LAY-message-search)', function (data) {
        var field = data.field;

        //执行重载
        table.reload('LAY-message-list', {
            where: field
        });
    });


    exports('message', {});
});