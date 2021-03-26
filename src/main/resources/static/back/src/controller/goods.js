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
        form = layui.form;



    table.render({
        elem: '#LAY-goods-list',
        id: 'LAY-goods-list',
        url: layui.setter.apiUrl + '/hfz/goods/list',
        title: '商品列表',
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
        toolbar: '<script type="text/html">' + addBtn("goods:add") + batchDelBtn("goods:delete") + '</script>',
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left',
                }, {
                field: 'name',
                title: '名称',
                align: 'center',
            }, {
                field: 'integralNum',
                title: '兑换积分数量',
                align: 'center',
            }, {
                field: 'num',
                title: '剩余数量',
                align: 'center',
            }, {
                field: 'typeName',
                title: '商品类型',
                align: 'center',
            }, {
                fixed: 'right',
                width: 200,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return editBtn("goods:update") + delBtn("goods:delete");
                }
            }
            ]
        ]
    });


    //监听头工具栏事件
    table.on('toolbar(LAY-goods-list)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                admin.popup({
                    id: 'LAY-admin-detail',
                    title: '新增商品',
                    area: ['630px', '800px'],
                    success: function (layero, index) {
                        view(this.id).render('goods/goods/detail', data).done(function () {





                            //福卡类型ID
                            var CardId = 0;

                            //判断商城中是否已经存在福卡  如果存在那么就不显示  福卡 类型
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/goods/goodsConut',
                                type: 'post',
                                async: false,
                                success: function (res) {
                                    data.FKStatus = res.data.res;
                                    CardId = res.data.typeId;
                                }
                            })

                            //


                            //如果选择到福卡  那么让它出下拉选   从福卡中挑选
                            form.on('select(typeId)', function (datas) {
                                //自定义属性获取方式
                                var val = datas.value;
                                //如果是卡片  就让它显示下拉选  自由选择
                                if (val == CardId) {
                                    $(".putongC").hide();
                                    $(".selectC").show();
                                    //向服务端发送删除指令
                                    admin.req({
                                        url: layui.setter.apiUrl + '/hfz/cardType/listAll',
                                        type: 'get',
                                        success: function (res) {
                                            $("#contentId").html("");
                                            $.each(res.data, function (i, item) {
                                                if (i == 0) {
                                                    $("#cardIdDis").val(item.id);
                                                    $("#LAY_avatarSrc").val(item.img);
                                                }
                                                var htmls = '<option ids=' + item.id + ' imgs=' + item.img + ' value=' + item.name + '>' + item.name + '</option>';
                                                $("#contentId").append(htmls);
                                            });
                                            form.render('select');
                                        }
                                    });
                                } else {
                                    $(".putongC").show();
                                    $(".selectC").hide();
                                    $("#cardIdDis").html(0)
                                }
                            })
                            //选择卡片的时候  让数量栏目隐藏
                            form.on('select(contentId)', function (data) {
                                var val = data.value;
                                var id = $("#contentId").find("option:selected").attr("ids")
                                var imgs = $("#contentId").find("option:selected").attr("imgs")
                                $("#cardIdDis").val(id);
                                $("#LAY_avatarSrc").val(imgs);
                            })





























                            //上传主图
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

                            //上传轮播图
                            upload.render({
                                url: layui.setter.apiUrl + '/common/fileUpload/',
                                headers: {
                                    access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                                },
                                multiple:true,  //是否支持多文件上传
                                elem: '#LAY_swiper',
                                done: function (res) {
                                    if (res.code == 200) {
                                        //回调地址
                                       // $('#LAY_swiper').before(" <img class=\"imgUp2\" style=\"width: 100px;height: 100px;margin-left: 2px\" src='"+layui.setter.apiUrl + res.data.src+"'>");
                                        $('#LAY_swiper').before('<div style="position:relative;overflow: hidden;width: 100px;float: left;margin-top: 10px"><img class="imgUp2" style="width: 100px;height: 100px;margin-left: 2px"  src="'+layui.setter.apiUrl + res.data.src+'">' +
                                            '<img class="cha" style="position:absolute;right:1px;top: 1px;width: 15px; " src="src/style/res/cha.png"></div>');
                                    } else {
                                        layer.msg(res.msg, {
                                            icon: 5
                                        });
                                    }
                                }
                            });

                            //点击图片放大
                            $('.layui-input-inline').on('click', '.imgUp2', function () {
                                // 总参数
                                var  data={};
                                var thsImg=$(this).attr("src")
                                var imgs = new Array();
                                $('.swiperParent .imgUp2').each(function(key, value) {
                                    //获取从第几个开始轮播
                                    if(thsImg==$(this).attr("src")){
                                        data.start=key
                                    }
                                    var  json={}
                                    json.src=$(this).attr("src");
                                    imgs[key]=json
                                });
                                data.data=imgs;
                               // console.log(JSON.stringify(data))         start：从第几个开始轮播
                                //{"start":1,"data":[{"src":"/statics/2019/11/14/15737109853251379.jpg"},{"src":"/statics/2019/11/14/15737109879224992.jpg"},{"src":"/statics/2019/11/14/15737109903688503.jpg"}]}
                                layer.photos({
                                    photos: data
                                    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
                                });

                            })

                            //上传内容图片
                            upload.render({
                                url: layui.setter.apiUrl + '/common/fileUpload/',
                                headers: {
                                    access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                                },
                                multiple:true,  //是否支持多文件上传
                                elem: '#LAY_content',
                                done: function (res) {
                                    if (res.code == 200) {
                                        //回调地址
                                        //$('#LAY_content').before(" <img class=\"imgUp3\" style=\"width: 100px;height: 100px;margin-left: 2px\" src='"+layui.setter.apiUrl + res.data.src+"'>");
                                        $('#LAY_content').before('<div style="position:relative;overflow: hidden;width: 100px;float: left;margin-top: 10px"><img class="imgUp3" style="width: 100px;height: 100px;margin-left: 2px"  src="'+layui.setter.apiUrl + res.data.src+'">' +
                                        '<img class="cha" style="position:absolute;right:1px;top: 1px;width: 15px; " src="src/style/res/cha.png"></div>');

                                    } else {
                                        layer.msg(res.msg, {
                                            icon: 5
                                        });
                                    }
                                }
                            });

                            //删除图片
                            $('.layui-input-inline').on('click', '.cha', function () {
                                $(this).parent().remove();
                            })
                            //点击图片放大
                            $('.layui-input-inline').on('click', '.imgUp3', function () {
                                // 总参数
                                var  data={};
                                var thsImg=$(this).attr("src")
                                var imgs = new Array();
                                $('.contentParent .imgUp3').each(function(key, value) {
                                    //获取从第几个开始轮播
                                    if(thsImg==$(this).attr("src")){
                                        data.start=key
                                    }
                                    var  json={}
                                    json.src=$(this).attr("src");
                                    imgs[key]=json
                                });
                                data.data=imgs;
                                layer.photos({
                                    photos: data
                                    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
                                });

                            })

                            //新增商品
                            form.on('submit(LAY-goods-submit)', function (obj) {




                                //获取轮播图列表
                                var swiperImgs="";
                                $('.swiperParent .imgUp2').each(function(key, value) {
                                    swiperImgs+=$(this).attr("src")+",";
                                });
                                swiperImgs=swiperImgs.substring(0,swiperImgs.length-1);
                                //获取内容详情图列表
                                var contentImgs="";
                                $('.contentParent .imgUp3').each(function(key, value) {
                                    contentImgs+=$(this).attr("src")+",";
                                });
                                contentImgs=contentImgs.substring(0,contentImgs.length-1);

                                obj.field.swiper=swiperImgs;
                                obj.field.content=contentImgs;
                                var content = "";
                                var display = $('.selectC').css('display');
                                if (display == 'none') {
                                    content = $(".content1").val();
                                    if (content == '') {
                                        layer.msg("请填写商品名称", {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        })
                                    }
                                } else {
                                    content = $(".content2").val();
                                }

                                obj.field.name = content;
                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/goods/add',
                                    type: 'post',
                                    data: obj.field,
                                    success: function (res) {
                                        layer.close(index);
                                        layer.msg(res.msg, {
                                            offset: '15px',
                                            icon: 1,
                                            time: 1500
                                        }, function () {
                                            table.reload("LAY-goods-list")
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
                            url: layui.setter.apiUrl + '/hfz/goods/delete',
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
                                    table.reload("LAY-goods-list")
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
    table.on('tool(LAY-goods-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '类型编辑',
                area: ['630px', '800px'],
                success: function (layero, index) {



                    var contents=data.content;
                    var swipers=data.swiper;
                    if(contents!=''&&contents!=null){
                        data.content=contents.split(',');
                    }
                    if(swipers!=''&&swipers!=null){
                        data.swiper=swipers.split(',');
                    }




                    view(this.id).render('goods/goods/detail', data).done(function () {
                        var CardId = 0;
                        //判断商城中是否已经存在福卡  如果存在  那么不能选择福卡类型新增了
                        admin.req({
                            url: layui.setter.apiUrl + '/hfz/goods/goodsConut',
                            type: 'post',
                            async: false,
                            data: obj.field,
                            success: function (res) {
                                CardId = res.data.typeId;

                                //福卡类型ID
                                if (data.typeId != res.data.res) {
                                    data.FKStatus = res.data.res;

                                } else {
                                    data.FKStatus = '';
                                    $(".putongC").hide();
                                    $(".selectC").show();


                                    admin.req({
                                        url: layui.setter.apiUrl + '/hfz/cardType/listAll',
                                        type: 'get',
                                        success: function (res2) {
                                            $("#contentId").html("");
                                            $.each(res2.data, function (i, item) {
                                                var htmls;
                                                if (data.cardId == item.cardId) {
                                                    htmls = '<option selected ids=' + item.id + ' imgs=' + item.img + ' value=' + item.name + '>' + item.name + '</option>';
                                                    $("#cardIdDis").val(item.id);
                                                } else {
                                                    htmls = '<option ids=' + item.id + ' imgs=' + item.img + ' value=' + item.name + '>' + item.name + '</option>';
                                                }

                                                $("#contentId").append(htmls);
                                            });
                                            form.render('select');
                                        }
                                    });

                                }
                            }
                        })


                        //如果选择到福卡  那么让它出下拉选   从福卡中挑选
                        form.on('select(typeId)', function (datas) {
                            //自定义属性获取方式
                            var val = datas.value;

                            //如果是卡片  就让它显示下拉选  自由选择
                            if (val == CardId) {
                                $(".putongC").hide();
                                $(".selectC").show();
                                admin.req({
                                    url: layui.setter.apiUrl + '/hfz/cardType/listAll',
                                    type: 'get',
                                    success: function (res) {
                                        $("#contentId").html("");
                                        $.each(res.data, function (i, item) {
                                            if (i == 0) {
                                                $("#cardIdDis").val(item.id);
                                                $("#LAY_avatarSrc").val(item.img);
                                            }
                                            var htmls = '<option ids=' + item.id + ' imgs=' + item.img + ' value=' + item.name + '>' + item.name + '</option>';
                                            $("#contentId").append(htmls);
                                        });
                                        form.render('select');
                                    }
                                });
                            } else {
                                $(".putongC").show();
                                $(".selectC").hide();
                                $("#goodsNameId").val("");
                                $("#LAY_avatarSrc").val("");
                                $("#cardIdDis").val(0);

                            }
                        })
                        //选择卡片的时候  让数量栏目隐藏
                        form.on('select(contentId)', function (data) {
                            var val = data.value;
                            var id = $("#contentId").find("option:selected").attr("ids")
                            $("#cardIdDis").val(id);
                            var imgs = $("#contentId").find("option:selected").attr("imgs")
                            $("#LAY_avatarSrc").val(imgs);
                        })






                        //上传主图
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

                        //上传轮播图
                        upload.render({
                            url: layui.setter.apiUrl + '/common/fileUpload/',
                            headers: {
                                access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                            },
                            multiple:true,  //是否支持多文件上传
                            elem: '#LAY_swiper',
                            done: function (res) {
                                if (res.code == 200) {
                                    //回调地址
                                    // $('#LAY_swiper').before(" <img class=\"imgUp2\" style=\"width: 100px;height: 100px;margin-left: 2px\" src='"+layui.setter.apiUrl + res.data.src+"'>");
                                    $('#LAY_swiper').before('<div style="position:relative;overflow: hidden;width: 100px;float: left;margin-top: 10px"><img class="imgUp2" style="width: 100px;height: 100px;margin-left: 2px"  src="'+layui.setter.apiUrl + res.data.src+'">' +
                                        '<img class="cha" style="position:absolute;right:1px;top: 1px;width: 15px; " src="src/style/res/cha.png"></div>');
                                } else {
                                    layer.msg(res.msg, {
                                        icon: 5
                                    });
                                }
                            }
                        });

                        //点击图片放大
                        $('.layui-input-inline').on('click', '.imgUp2', function () {
                            // 总参数
                            var  data={};
                            var thsImg=$(this).attr("src")
                            var imgs = new Array();
                            $('.swiperParent .imgUp2').each(function(key, value) {
                                //获取从第几个开始轮播
                                if(thsImg==$(this).attr("src")){
                                    data.start=key
                                }
                                var  json={}
                                json.src=$(this).attr("src");
                                imgs[key]=json
                            });
                            data.data=imgs;
                            layer.photos({
                                photos: data
                                ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
                            });

                        })

                        //上传内容图片
                        upload.render({
                            url: layui.setter.apiUrl + '/common/fileUpload/',
                            headers: {
                                access_token: layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                            },
                            multiple:true,  //是否支持多文件上传
                            elem: '#LAY_content',
                            done: function (res) {
                                if (res.code == 200) {
                                    //回调地址
                                    //$('#LAY_content').before(" <img class=\"imgUp3\" style=\"width: 100px;height: 100px;margin-left: 2px\" src='"+layui.setter.apiUrl + res.data.src+"'>");
                                    $('#LAY_content').before('<div style="position:relative;overflow: hidden;width: 100px;float: left;margin-top: 10px"><img class="imgUp3" style="width: 100px;height: 100px;margin-left: 2px"  src="'+layui.setter.apiUrl + res.data.src+'">' +
                                        '<img class="cha" style="position:absolute;right:1px;top: 1px;width: 15px; " src="src/style/res/cha.png"></div>');

                                } else {
                                    layer.msg(res.msg, {
                                        icon: 5
                                    });
                                }
                            }
                        });

                        //删除图片
                        $('.layui-input-inline').on('click', '.cha', function () {
                            $(this).parent().remove();
                        })
                        //点击图片放大
                        $('.layui-input-inline').on('click', '.imgUp3', function () {
                            // 总参数
                            var  data={};
                            var thsImg=$(this).attr("src")
                            var imgs = new Array();
                            $('.contentParent .imgUp3').each(function(key, value) {
                                //获取从第几个开始轮播
                                if(thsImg==$(this).attr("src")){
                                    data.start=key
                                }
                                var  json={}
                                json.src=$(this).attr("src");
                                imgs[key]=json
                            });
                            data.data=imgs;
                            layer.photos({
                                photos: data
                                ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
                            });

                        })



                        //更新
                        form.on('submit(LAY-goods-submit)', function (obj) {
                            //获取轮播图列表
                            var swiperImgs="";
                            $('.swiperParent .imgUp2').each(function(key, value) {
                                swiperImgs+=$(this).attr("src")+",";
                            });
                            swiperImgs=swiperImgs.substring(0,swiperImgs.length-1);
                            //获取内容详情图列表
                            var contentImgs="";
                            $('.contentParent .imgUp3').each(function(key, value) {
                                contentImgs+=$(this).attr("src")+",";
                            });
                            contentImgs=contentImgs.substring(0,contentImgs.length-1);

                            obj.field.swiper=swiperImgs;
                            obj.field.content=contentImgs;

                            obj.field.id = data.id;


                            var content = "";
                            var display = $('.selectC').css('display');
                            if (display == 'none') {
                                content = $(".content1").val();
                                if (content == '') {
                                    layer.msg("请填写商品名称", {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1500
                                    })
                                }
                            } else {
                                content = $(".content2").val();
                            }

                            obj.field.name = content;



                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/goods/update',
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
                                        table.reload("LAY-goods-list")
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
                    url: layui.setter.apiUrl + '/hfz/goods/delete',
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
                            table.reload("LAY-goods-list")
                        });
                    }
                });
            });
        }
    });


//监听搜索
    form.on('submit(LAY-goods-search)', function(data) {
        var field = data.field;

        //执行重载
        table.reload('LAY-goods-list', {
            where: field
        });
    });




    exports('goods', {});
});