/**
 * 任务中心-任务管理
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        form = layui.form,
        setter = layui.setter;

//查看凭据
    admin.events.seeProof = function (othis) {
        var src = $(othis).attr("data");
        layer.photos({
            photos: {
                "title": "查看凭据",
                "data": [{
                    "src": src //原图地址
                }]
            },
            shade: 0.8,
            closeBtn: 1,
            anim: 5
        });
    };

    admin.events.agree = function (othis) {
        var resultId = $(othis).attr("data");
        var ids = [];
        ids.push(resultId);
        admin.req({
            url: layui.setter.apiUrl + '/cms/wx/link/result/agree',
            type: 'post',
            traditional: true, //默认false
            data: {
                ids: ids,
                msg: "审核通过",
                status: 4
            },
            success: function (res) {
                layer.msg(res.msg, {
                    offset: '15px',
                    icon: 1,
                    time: 500
                }, function () {
                    admin.events.refresh();
                });
            }
        });
    };

    admin.events.refuse = function (othis) {
        var resultId = $(othis).attr("data");
        layer.prompt({title: '请输入拒绝原因', formType: 3, value: "上传的图片不匹配"}, function (text, index) {
            var ids = [];
            ids.push(resultId);
            admin.req({
                url: layui.setter.apiUrl + '/cms/wx/link/result/refuse',
                type: 'post',
                traditional: true, //默认false
                data: {
                    ids: ids,
                    msg: text,
                    status: 3
                },
                success: function (res) {
                    layer.msg(res.msg, {
                        offset: '15px',
                        icon: 1,
                        time: 500
                    }, function () {
                        admin.events.refresh();
                    });
                }
            });
        });
    };

    // 全选
    admin.events.checkAll = function (othis) {
        $("input[name='proofCheck']").attr("checked", true);
        form.render(); //渲染该模板下的动态表单
    };

    // 批量 通过
    admin.events.batchAgree = function (othis) {
        var ids = [];
        $("input[name='proofCheck']:checked").each(function (i) {
            ids.push($(this).val());
        });

        if (ids.length === 0) {
            layer.msg("请选择");
            return false;
        }
        admin.req({
            url: layui.setter.apiUrl + '/cms/wx/link/result/agree',
            type: 'post',
            traditional: true, //默认false
            data: {
                ids: ids,
                msg: "审核通过",
            },
            success: function (res) {
                layer.msg(res.msg, {
                    offset: '15px',
                    icon: 1,
                    time: 500
                }, function () {
                    admin.events.refresh();
                });
            }
        });


    };
    // 批量 拒绝
    admin.events.batchRefuse = function (othis) {
        var ids = [];
        $("input[name='proofCheck']:checked").each(function (i) {
            ids.push($(this).val());
        });
        if (ids.length === 0) {
            layer.msg("请选择");
            return false;
        }
        layer.prompt({title: '请输入拒绝原因', formType: 3, value: "上传的图片不匹配"}, function (text, index) {
            admin.req({
                url: layui.setter.apiUrl + '/cms/wx/link/result/refuse',
                type: 'post',
                traditional: true, //默认false
                data: {
                    ids: ids,
                    msg: text,
                },
                success: function (res) {
                    layer.msg(res.msg, {
                        offset: '15px',
                        icon: 1,
                        time: 500
                    }, function () {
                        admin.events.refresh();
                    });
                }
            });
        });
    };


    var getData = function (no) {
        admin.req({
            url: layui.setter.apiUrl + '/cms/wx/link/result/list',
            type: 'get',
            data: {
                no: no
            },
            success: function (res) {
                var sb = "";
                if(res.count ===0){
                    sb = '<p style="text-align: center">无数据</p>';
                }
                layui.each(res.data, function (index, item) {
                    sb += '<div class="layui-col-md2 layui-col-sm4">';
                    sb += '<div class="cmdlist-container layui-form">';
                    sb += '<img src="' + item.imgUrl + '" layadmin-event="seeProof" data="' + item.imgUrl + '">';
                    sb += '<div class="cmdlist-text">';
                    sb += '<p class="info">任务编号：' + item.no + '</p>';
                    sb += '<p class="info">用户昵称：' + item.nickName + '-' + item.userId + '</p>';
                    sb += '<p class="info">上传时间：' + item.createTime + '</p>';
                    sb += '<p class="info">';
                    sb += '<input type="checkbox" name="proofCheck" lay-skin="primary" value="' + item.id + '">';
                    sb += '<button type="button" class="layui-btn layui-btn-sm layui-btn-primary" layadmin-event="seeProof" data="' + item.imgUrl + '">查看</button>';
                    sb += '<button type="button" class="layui-btn layui-btn-sm layui-btn-normal" layadmin-event="agree" data="' + item.id + '">通过</button>';
                    sb += '<button type="button" class="layui-btn layui-btn-sm layui-btn-danger" layadmin-event="refuse" data="' + item.id + '">拒绝</button></p></div></div></div>';
                });
                $("#linkResultList").html(sb);
                form.render(); //渲染该模板下的动态表单
            }
        });
    };
    getData(null);
    form.on('select(linkSelect)', function (data) {
        getData(data.value);
    });

    exports('linkResult', {});
});