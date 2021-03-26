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
        upload = layui.upload,
        form = layui.form;
    form.render();
    table.render({
        elem: '#LAY-use-list',
        id: 'LAY-use-list',
        url: layui.setter.apiUrl + '/hfz/use/list',
        title: '用户',
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
      /*  toolbar: '<script type="text/html">' + addBtn("admin:add") + batchDelBtn("admin:delete") + '</script>',*/
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    title: '序号',
                    type: 'numbers',
                    fixed: 'left'
                }, {
                field: 'realName',
                title: '姓名',
                align: 'center',
            }, {
                field: 'nickName',
                title: '昵称',
                align: 'center'
            }, {
                field: 'phone',
                title: '手机号',
                align: 'center'
            }, {
                field: 'userStatus',
                width:100,
                title: '用户状态',
                align: 'center',
                templet: function (d) {
                     if(d.userStatus==2){return '<a lay-event="blacklist" style="color:red">黑名单</a>'}else{return '白名单'};
                }
            }, {
                field: 'registerTime',
                title: '创建时间',
                align: 'center',
                sort: true,
                templet: function (d) {
                    return layui.util.toDateString(d.registerTime, "yyyy-MM-dd HH:mm:ss");
                }
            }, {
                fixed: 'right',
                width: 350,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return "<a class=\"layui-btn  layui-btn-warm layui-btn-xs event-btn\"  lay-event=\"useAll\"><i class=\"layui-icon layui-icon-radio\"></i>详情</a>" + editBtn("use:update") + delBtn("use:delete") + "<a class=\"layui-btn  layui-btn-warm layui-btn-xs event-btn\"  lay-event=\"black\"><i class=\"layui-icon layui-icon-radio\"></i>拉黑</a>"
                        + '<a class="layui-btn layui-btn-normal layui-btn-xs event-btn"  lay-event="messageOn"><i class="layui-icon layui-icon-edit"></i>私信</a>';
                }
            }
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-use-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '用户新增',
                    area: ['600px', '780px'],
                    success: function (layero, index) {
                        view(this.id).render('use/user/detail', data).done(function () {
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

                            form.on('submit(LAY-use-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/use/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload("LAY-use-list")
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
                            url: layui.setter.apiUrl + '/hfz/use/delete',
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
                                    table.reload("LAY-use-list")
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
    table.on('tool(LAY-use-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        //用户ID
        sessionStorage.setItem("userId", data.id);
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '用户编辑',
                area: ['600px', '780px'],
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
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                var ids = [];
                ids.push(data.id)
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/use/delete',
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
                            table.reload("LAY-use-list")
                        });
                    }
                });
            });
        } else if (layEvent === 'black') {
            layer.confirm('真的要拉黑用户吗？', function (index) {

                obj.data.userStatus=2;
                //向服务端发送删除指令
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/use/update',
                    type: 'post',
                    data: {
                        id:obj.data.id,
                        userStatus:2
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            layer.close(index);
                            table.reload("LAY-use-list")
                        });
                    }
                });
            });
        }else if (layEvent === 'useAll') {  //用户全部信息
            admin.popup({
                id: 'LAY-admin-detail',
                title: '用户详细信息',
                area: ['1000px', '780px'],
                success: function (layero, index) {
                    view(this.id).render('use/user/userDetails', data).done(function () {

                    });
                }
            });

        } else if (layEvent === 'blacklist') {
            layer.confirm('真的要恢复用户？？', {
                btn: ['确定','取消'] //按钮
            }, function(index){
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/use/update',
                    type: 'post',
                    data: {
                        id:obj.data.id,
                        userStatus:1
                    },
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            layer.close(index);
                            table.reload("LAY-use-list")
                        });
                    }
                });
            });
        } else if (layEvent === 'messageOn') {
            //私信

            admin.popup({
                id: 'LAY-admin-detail',
                title: '私信',
                area: ['600px', '350px'],
                success: function (layero, index) {

                    view(this.id).render('use/user/message', data).done(function () {
                        //更新
                        form.on('submit(LAY-message-submit)', function (obj) {
                            obj.field.userId = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/message/insert',
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
    form.on('submit(LAY-users-search)', function(data) {
        var field = data.field;

        if(field.nickName==""){
            field.nickName=null;
        }
        //执行重载
        table.reload('LAY-use-list', {
            where: field
        });
    });



    exports('users', {});
});