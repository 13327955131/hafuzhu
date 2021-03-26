/**
 * 轮播图
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
        elem: '#LAY-swiper-list',
        id: 'LAY-swiper-list',
        url: layui.setter.apiUrl + '/hfz/swiper/list',
        title: '轮播列表',
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
        toolbar: '<script type="text/html">' + addBtn("swiper:add") + batchDelBtn("swiper:delete") + '</script>',
        defaultToolbar: ['filter', 'exports'],
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left',
                }, {
                field: 'title',
                title: '标题',
                align: 'center',
                }, {
                field: 'content',
                title: ' 内容',
                align: 'center',
            }, {
                field: 'img',
                title: '图片地址',
                align: 'center',
            }, {
                field: 'url',
                title: '链接地址',
                align: 'center',
            }, {
                fixed: 'right',
                width: 200,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return editBtn("swiper:update") + delBtn("swiper:delete");
                }
            }
            ]
        ]
    });


    //监听头工具栏事件
    table.on('toolbar(LAY-swiper-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '新增福卡',
                    area: ['600px', '500px'],
                    success: function (layero, index) {
                        view(this.id).render('goods/swiper/detail', data).done(function () {
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

                            form.on('submit(LAY-swiper-submit)', function (obj) {
                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/swiper/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload("LAY-swiper-list")
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
                            url: layui.setter.apiUrl + '/hfz/swiper/delete',
                            type: 'post',
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
                                    table.reload("LAY-swiper-list")
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
    table.on('tool(LAY-swiper-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '福卡编辑',
                area: ['600px', '500px'],
                success: function (layero, index) {
                    view(this.id).render('goods/swiper/detail', data).done(function () {
                        //更新

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



                        form.on('submit(LAY-swiper-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/swiper/update',
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
                                        table.reload("LAY-swiper-list")
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
                    url: layui.setter.apiUrl + '/hfz/swiper/delete',
                    type: 'post',
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
                            table.reload("LAY-swiper-list")
                        });
                    }
                });
            });
        }
    });








    exports('swiper', {});
});