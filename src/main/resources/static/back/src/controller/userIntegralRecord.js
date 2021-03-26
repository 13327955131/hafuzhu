/**
 * 财务统计--用户积分明细
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
    form.render();
    table.render({
        elem: '#LAY-userIntegralRecord-list',
        id: 'LAY-userIntegralRecord-list',
        url: layui.setter.apiUrl + '/hfz/userIntegralRecord/list',
        title: '用户积分明细',
        height: 'full-240', //高度最大化减去差值
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
        totalRow: true,
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    title: '序号',
                    fixed: 'left',
                    totalRowText: '合计:',
                }, {
                field: 'userName',
                title: '用户昵称',
                align: 'center',
            }, {
                field: 'userPhone',
                title: '用户手机号',
                align: 'center',
            }, {
                field: 'num',
                title: '数量',
                align: 'center',
                totalRow: true

            }, {
                field: 'type',
                title: '积分类型',
                align: 'center',
                sort: true,
                templet: function (d) {
                   if(d.type==1){
                       return "<a style='color: red'>新增</a>"
                   }else{
                       return "<a style='color: blue'>减少</a>"
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





    //监听行工具事件
    table.on('tool(LAY-use-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'shen') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '审核',
                area: ['600px', '800px'],
                success: function (layero, index) {
                    view(this.id).render('use/user/detail', data).done(function () {
                        $("#admin-password").hide();
                        //上传头像
                        var avatarSrc = $('#LAY_avatarSrc');
                        upload.render({
                            url: layui.setter.apiUrl + '/common/fileUpload/',
                            headers: {
                                access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                            },
                            elem: '#LAY_avatarUpload',
                            done: function (res) {
                                if (res.code == 200) {
                                    avatarSrc.val(layui.setter.apiUrl + res.data.src);
                                } else {
                                    layer.msg(res.msg, {
                                        icon: 5
                                    });
                                }
                            }
                        });

                        //查看头像
                        admin.events.avartatPreview = function (othis) {
                            var src = avatarSrc.val();
                            layer.photos({
                                photos: {
                                    "title": "查看头像" //相册标题
                                    ,
                                    "data": [{
                                        "src": src //原图地址
                                    }]
                                },
                                shade: 0.01,
                                closeBtn: 1,
                                anim: 5
                            });
                        };

                        form.render();
                        //更新
                        form.on('submit(LAY-use-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/use/update',
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
                                        table.reload("LAY-use-list")
                                    });
                                }
                            });
                        });

                    });
                }
            });
        }
    });


















    //监听搜索
    form.on('submit(LAY-userIntegralRecord-search)', function(data) {
        var field = data.field;
        //执行重载
        table.reload('LAY-userIntegralRecord-list', {
            where: field
        });
    });


    exports('userIntegralRecord', {});
});