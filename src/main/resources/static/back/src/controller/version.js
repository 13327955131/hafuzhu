/**
 * 版本更新
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
        elem: '#LAY-version-list',
        id: 'LAY-version-list',
        url: layui.setter.apiUrl + '/hfz/version/list',
        title: '类型列表',
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
        toolbar: '<script type="text/html">' + addBtn("version:add") + batchDelBtn("version:delete") + '</script>',
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left',
                }, {
                field: 'version',
                title: '版本',
                align: 'center',
           }, {
                field: 'content',
                title: '版本内容',
                align: 'center',
            }, {
                field: 'url',
                title: '下载地址',
                align: 'center',
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
            }, {
                fixed: 'right',
                width: 200,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return editBtn("version:update") + delBtn("version:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-version-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '新增版本信息',
                    area: ['600px', '340px'],
                    success: function (layero, index) {
                        view(this.id).render('sys/version/detail', data).done(function () {


                            //上传安装包
                            var avatarSrc = $('#LAY_avatarSrc');
                            upload.render({
                                url: layui.setter.apiUrl + '/common/fileUpload/',
                                headers: {
                                    access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                                },
                                accept:'file',
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


                            form.on('submit(LAY-version-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/version/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload("LAY-version-list")
                                        });
                                    }
                                });
                            });
                        });
                    }
                });
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除行么', function (index) {
                        var ids = [];
                        layui.each(data, function (index, item) {
                            ids.push(item.id)
                        })
                        //向服务端发送删除指令
                        admin.req({
                            url: layui.setter.apiUrl + '/hfz/version/delete',
                            type: 'get',
                            data: {
                                ids: ids
                            },
                            success: function (res) {
                                layer.msg(res.msg, {
                                    offset: '15px',
                                    icon: 1,
                                    time: 500
                                }, function () {
                                    layer.close(index);
                                    table.reload("LAY-version-list")
                                });
                            }
                        });
                    });
                }
                break;
        }
        ;
    });



    //监听行工具事件
    table.on('tool(LAY-version-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '类型编辑',
                area: ['600px', '340px'],
                success: function (layero, index) {
                    view(this.id).render('sys/version/detail', data).done(function () {
                        //上传安装包
                        var avatarSrc = $('#LAY_avatarSrc');
                        upload.render({
                            url: layui.setter.apiUrl + '/common/fileUpload/',
                            headers: {
                                access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                            },
                            accept:'file',
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


                        //更新
                        form.on('submit(LAY-version-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/version/update',
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
                                        table.reload("LAY-version-list")
                                    });
                                }
                            });
                        });

                    });
                }
            });
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var ids = [];
                ids.push(data.id)
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/version/delete',
                    type: 'get',
                    data: {
                        ids: ids
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            layer.close(index);
                            table.reload("LAY-version-list")
                        });
                    }
                });
            });
        }
    });








    exports('version', {});
});