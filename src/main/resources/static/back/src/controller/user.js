/**
 * 权限中心-用户管理
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
        elem: '#LAY-admin-list',
        id: 'LAY-admin-list',
        url: layui.setter.apiUrl + '/cms/admin/list',
        title: '用户',
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
        toolbar: '<script type="text/html">' + addBtn("admin:add") + batchDelBtn("admin:delete") + '</script>',
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                field: 'nickName',
                title: '姓名',
                align: 'center',
            }, {
                field: 'phone',
                title: '电话',
                align: 'center'
            }, {
                field: 'username',
                title: '账号',
                align: 'center'
            }, {
                field: 'roleName',
                title: '角色',
                align: 'center'
            }, {
                field: 'domain',
                title: '域名',
                align: 'center'
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                templet: function (d) {
                    return layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss");
                }
            }, {
                fixed: 'right',
                width: 200,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return editBtn("admin:update") + delBtn("admin:delete");
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-admin-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '管理员新增',
                    area: ['600px', '650px'],
                    success: function (layero, index) {
                        view(this.id).render('permission/user/detail', data).done(function () {
                            form.render();
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

                            form.on('submit(LAY-admin-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/cms/admin/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            admin.events.refresh();
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
                            url: layui.setter.apiUrl + '/cms/admin/delete',
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
                                    admin.events.refresh();
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
    table.on('tool(LAY-admin-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '管理员编辑',
                area: ['600px', '650px'],
                success: function (layero, index) {
                    view(this.id).render('permission/user/detail', data).done(function () {
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
                        form.on('submit(LAY-admin-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/cms/admin/update',
                                type: 'post',
                                data: obj.field,
                                success: function (res) {
                                    layer.close(index);
                                    layer.msg(res.msg, {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1500
                                    }, function () {
                                        admin.events.refresh();
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
                    url: layui.setter.apiUrl + '/cms/admin/delete',
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
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            admin.events.refresh();
                        });
                    }
                });
            });
        }
    });
    exports('user', {});
});