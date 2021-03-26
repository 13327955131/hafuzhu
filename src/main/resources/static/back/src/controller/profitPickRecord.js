/**
 * 财务统计--收益提现记录
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
        elem: '#LAY-profitPickRecord-list',
        id: 'LAY-profitPickRecord-list',
        url: layui.setter.apiUrl + '/hfz/profitPickRecord/list',
        title: '金库',
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
                    type: 'numbers',
                    title: '序号',
                    fixed: 'left'
                }, {
                field: 'userName',
                title: '用户昵称',
                align: 'center',
            }, {
                field: 'userPhone',
                title: '用户手机号',
                align: 'center',
            }, {
                field: 'amount',
                title: '金额(元)',
                align: 'center',
                templet:function(d){
                    return d.amount/100;
                }
            }, {
                field: 'applyTime',
                title: '提现申请时间',
                align: 'center',
            }, {
                field: 'verifyStatus', //审核状态（1、待审核  2、审核成功 3、审核失败）
                title: '审核状态',
                align: 'center',
                sort: true,
                templet: function (d) {
                   if(d.verifyStatus==2){
                       return "<a style='color: blue'>审核成功</a>"
                   }else if(d.verifyStatus==3){
                       return "<a style='color: greenyellow'>审核失败</a>"
                   }else{
                       return "<a lay-event=\"verify\" style='color: red;cursor:pointer;'>待审核</a>"
                   }
                }
            }, {
                field: 'payStatus', //支付状态（1、待支付 2、支付成功 3、支付失败）
                title: '支付状态',
                align: 'center',
                sort: true,
                templet: function (d) {
                    if(d.payStatus==2){
                        return "<a style='color: blue'>支付成功</a>"
                    }else if(d.payStatus==3){
                        return "<a style='color: greenyellow'>支付失败</a>"
                    }else{
                        return "<a style='color: red'>待支付</a>"
                    }
                }
            }, {
                field: 'operateTime',
                title: '最后操作时间',
                align: 'center',
            }, {
                field: 'msg',
                title: '备注',
                align: 'center',
            }
            ]
        ]
    });
    //监听搜索
    form.on('submit(LAY-profitPickRecord-search)', function(data) {
        var field = data.field;

        if(field.nickName==""){
            field.nickName=null;
        }
        //执行重载
        table.reload('LAY-profitPickRecord-list', {
            where: field
        });
    });

    table.on('tool(LAY-profitPickRecord-list)', function (obj) {
        var data = obj.data, //获得当前行数据
             layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'verify') {
            admin.popup({
                id: 'LAY-admin-detail',
                title: '提现审核',
                area: ['850px', '700px'],
                success: function (layero, index) {
                    view(this.id).render('record/profitPickRecord/verify', data).done(function () {
                        //传值
                        //申请用户ID
                        sessionStorage.setItem("verifyUserId", data.userId);
                        //申请用户手机号
                        sessionStorage.setItem("verifyUserPhone", data.userPhone);
                        //申请数据ID
                        sessionStorage.setItem("verifyID", data.id);
                    });
                }
            });


        }


    })







    exports('profitPickRecord', {});
});