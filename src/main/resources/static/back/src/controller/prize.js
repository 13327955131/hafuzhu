/**
 * 用户管理--小金库
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
        treeGrid = layui.treeGrid,
        form = layui.form;
    form.render();
    var listPrize = new Array();
    table.render({
        elem: '#LAY-prize-list',
        id: 'LAYPrizeList',
        url: layui.setter.apiUrl + '/hfz/prize/list',
        title: '奖品',
        height: 'full-220', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,            limitName: 'pageSize' //每页数据量的参数名，默认：limit

        },
        page: true,
        limit: 15,
        headers: {
            access_token: layui.data(setter.tableName)[setter.request.tokenName]
        },
        response: {
            statusCode: 200
        },
        toolbar: '<script type="text/html">' + addBtn("prize:add") +'<span style="color:red;margin-left:20px">(抽奖奖品必须是8个;合成奖品：积分、红包、卡片;图片尺寸：240px*240px;)</span>'+'<a style="margin-left: 50px" class="layui-btn layui-btn-danger layui-btn-sm " lay-event="saves"><i class="layui-icon layui-icon-edit"></i>保存</a>'+ '</script>',
        defaultToolbar: ['filter', 'exports'],
        totalRow: true,
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    fixed: 'left',
                    width: 120,
                    title: '序号',
                    totalRowText: '概率合计:',
                }, {
                    field: 'prizeType',
                    title: '抽奖/合成',
                    align: 'center',
                    templet:function (d) {
                        if(d.prizeType==1){          //（1、积分 2、红包 3、卡片）
                            return "抽奖";
                        }else if (d.prizeType==2){
                            return "合成";
                        }
                    }
                }, {
                    field: 'content',
                    title: '礼品内容',
                    align: 'center',
                }, {
                    field: 'img',
                    title: '图片',
                    align: 'center',
                    templet:"#imgTpl"
                }, {
                    field: 'no',
                    title: '数量',
                    align: 'center',
                    edit: 'text',
                    sort: true
                }, {
                    field: 'type',
                    title: '奖品类型',
                    align: 'center',
                    templet:"#typeSelect"//1积分  2红包 3 卡片

                }, {
                    field: 'proportion',
                    title: '概率',
                    align: 'center',
                    edit: 'text',       //单元格编辑  可触发事件
                    totalRow: true,
                    sort: true
                }, {
                    fixed: 'right',
                    width: 200,
                    title: '操作',
                    align: 'center',
                    templet: function (d) {
                        return editBtn("prize:update") + delBtn("prize:delete");
                    }
                }
            ]
        ],
        done: function(res, curr, count){
            //获取全部数据
            listPrize=res.data;
           // alert(JSON.stringify(listPrize))

            form.render(); //先渲染
            $('select').next().each(function(index,item){
                $(item).find('dl').addClass('table-select'); //添加样式
                $(item).find('dl').css('min-width',$(item).width()+'px'); //获取到宽度后再赋值
            });
        }
    });

    //监听头工具栏事件
    table.on('toolbar(LAY-prize-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据

        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '新增奖品（奖品图片大小为：240px*240px）',
                    area: ['600px', '500px'],
                    success: function (layero, index) {
                        view(this.id).render('prize/prize/detail', data).done(function () {
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
                            //判断该取i谁的值
                            form.on('submit(LAY-prize-submit)', function (obj) {
                                var content="";
                                var display =$('.selectC').css('display');
                                if(display == 'none'){
                                    content=$(".content1").val();
                                    if(content==''){
                                        layer.msg("请填写内容信息", {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        })
                                    }
                                }else{
                                    content=$(".content2").val();
                                }

                                obj.field.content=content;

                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/prize/add',
                                    type: 'post',
                                    data: obj.field,
                                    hasLoading:true,    //避免重复提交
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload("LAYPrizeList")
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
                            url: layui.setter.apiUrl + '/hfz/prize/delete',
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
                                    table.reload("LAYPrizeList")
                                });
                            }
                        });
                    });
                }
                break;
                case 'saves':

                    var datas=layui.table.cache.LAYPrizeList;

                    layer.confirm('是否保存', function (index) {
                        //向服务端发送删除指令
                        admin.req({
                            url: layui.setter.apiUrl + '/hfz/prize/updateAll',
                            type: 'get',
                            data: {
                                listPrize:JSON.stringify(datas)
                            },
                            success: function (res) {
                                layer.msg(res.msg, {
                                    offset: '15px',
                                    icon: 1,
                                    time: 500
                                }, function () {
                                    layer.close(index);
                                    table.reload("LAYPrizeList")
                                });
                            }
                        });
                    });

                break;
        }
        ;
    });


/*    form.on('select(typeLay)', function(data) {
        //自定义属性获取方式
        var val=data.value;
        //如果是卡片  就让它显示下拉选  自由选择
        if (val == 3) {
            $(".putongC").hide();
            $(".selectC").show();
            $(".noClass").hide();
            //向服务端发送删除指令
            admin.req({
                url: layui.setter.apiUrl + '/hfz/cardType/listAll',
                type: 'get',
                success: function (res) {
                    $("#contentId").html("");
                    $.each(res.data, function (i, item) {
                        if(i==0){
                           // $("#noId").val(item.id)
                            $("#noId").val(1)

                            $("#LAY_avatarSrc").val(item.img);
                        }
                        var htmls = '<option ids=' + item.id + ' imgs=' + item.img + ' value=' + item.name + '>' + item.name + '</option>';
                        $("#contentId").append(htmls);
                    });
                    form.render('select');
                }
            });
        }else{
            $(".noClass").show();
            $("#noId").val("");
            $(".putongC").show();
            $(".selectC").hide();
        }
    })*/


    //选择卡片的时候  让数量栏目隐藏
    form.on('select(contentId)', function(data) {
        var val=data.value;
        var id=$("#contentId").find("option:selected").attr("ids")
        var imgs=$("#contentId").find("option:selected").attr("imgs")
       // $("#noId").val(id);
        $("#noId").val(1)

        $("#LAY_avatarSrc").val(imgs);

    })


    //监听行工具事件
    table.on('tool(LAY-prize-list)', function (obj) {
        var objMain=obj;
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {


            admin.popup({
                id: 'LAY-admin-detail',
                title: '奖品编辑（奖品图片大小为：240px*240px）',
                area: ['600px', '500px'],
                success: function (layero, index) {
                    view(this.id).render('prize/prize/detail', data).done(function () {
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
                        form.on('submit(LAY-prize-submit)', function (obj) {
                            var content="";
                            var display =$('.selectC').css('display');
                            if(display == 'none'){
                                content=$(".content1").val();
                                if(content==''){
                                    layer.msg("请填写内容信息", {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1500
                                    })
                                }
                            }else{
                                content=$(".content2").val();
                            }

                            obj.field.content=content;



                            obj.field.id = data.id;



                            //开始赋值
                            objMain.update(obj.field);

                            layer.close(index);

                            form.render();


                           /* admin.req({
                                url: layui.setter.apiUrl + '/hfz/prize/update',
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
                                        table.reload("LAYPrizeList")
                                    });
                                }
                            });*/
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
                    url: layui.setter.apiUrl + '/hfz/prize/delete',
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
                            table.reload("LAYPrizeList")
                        });
                    }
                });
            });
        }
    });



//监听搜索
    form.on('submit(LAY-prize-search)', function(data) {
        var field = data.field;
        //执行重载
        table.reload('LAYPrizeList', {
            where: field
        });
    });



    // 单元格编辑
   /* table.on('edit(LAY-prize-list)', function(obj) {
        var param = {
            sequence: obj.value,
            id: obj.data.id
        }

        obj.data.img=11;
        table.reload("LAYPrizeList")
    console.log(JSON.stringify(layui.table.cache.LAYPrizeList))
        /!*admin.req({
            url: layui.setter.apiUrl + '/cms/menu/update',
            type: 'post',
            data: param,
            isLoad: true,
            success: function(res) {
                layer.msg(res.msg, {
                    offset: '15px',
                    icon: 1,
                    time: 1000
                });
            }
        });*!/

       /!* //单元格 赋值
        obj.update({
            sign: value
        });*!/
    });
*/

    exports('prize', {});
});