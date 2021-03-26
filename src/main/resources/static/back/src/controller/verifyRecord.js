/**
 * 用户管理--收益记录
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'selectLink', 'common', 'upload','echarts'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        table = layui.table,
        view = layui.view,
        setter = layui.setter,
        echarts = layui.echarts,
        selectLink = layui.selectLink,
        common = layui.common,
        upload = layui.upload,
        form = layui.form;


   var id= sessionStorage.getItem("verifyUserId");
   var phone= sessionStorage.getItem("verifyUserPhone");
    table.render({
        elem: '#LAY-verifyRecord-list',
        id: 'LAY-verifyRecord-list',
        where:{
            userId:id,
        },
        url: layui.setter.apiUrl + '/hfz/record/list',
        title: '收益记录',
        height: 'full-340', //高度最大化减去差值
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            ,
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
            ,
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
                title: '用户手机号',
                align: 'center',
                templet: function (d) {
                    return phone
                }

            }, {
                field: 'amount',
                title: '红包金额',
                align: 'center',
                templet:function (d) {
                    return d.amount/100
                }
            }, {
                title: '获取方式',
                align: 'center',
                templet:function (d) {      //方式(1，抽奖；2，收益卡, 3 签到、4 合成 5 实名 6 收徒）
                    if (d.getWay==1){
                        return '抽奖'
                    } else if(d.getWay==2){
                        return '收益卡'
                    } else if(d.getWay==3){
                        return '签到'
                    } else if(d.getWay==4){
                        return '合成'
                    } else if(d.getWay==5){
                        return '实名'
                    } else if(d.getWay==6){
                        return '收徒'
                    }
            }
            }, {
                field: 'getTime',
                title: '获取时间',
                align: 'center',
            }
            ]
        ]
    });



    $(".verifyYes").click(function () {

        layer.prompt({title: '审核结果备注回复！', formType: 2}, function(text, index){
            layer.close(index);
            var updateId=sessionStorage.getItem("verifyID");
            var userId=sessionStorage.getItem("verifyUserId");
            admin.req({
                url: layui.setter.apiUrl + '/hfz/profitPickRecord/updateSH',
                type: 'post',
                data: {
                    id:updateId,
                    verifyStatus:2,
                    userId:userId,
                    msg:text
                },
                success: function (res) {
                    layer.msg("审核成功", {
                        offset: '15px',
                        icon: 1,
                        time: 500
                    }, function () {
                        layer.closeAll();
                        table.reload("LAY-profitPickRecord-list")
                    });
                }
            });


        })



    })

    $(".verifyNo").click(function () {
        //prompt层
            layer.prompt({title: '审核结果备注回复！', formType: 2}, function(text, index){
                layer.close(index);
                var updateId=sessionStorage.getItem("verifyID");
                var userId=sessionStorage.getItem("verifyUserId");
                admin.req({
                    url: layui.setter.apiUrl + '/hfz/profitPickRecord/updateSH',
                    type: 'post',
                    data: {
                        id:updateId,
                        verifyStatus:3,
                        userId:userId,
                        msg:text
                    },
                    success: function (res) {
                        layer.msg("审核失败", {
                            offset: '15px',
                            icon: 1,
                            time: 500
                        }, function () {
                            layer.closeAll();
                            table.reload("LAY-profitPickRecord-list")
                        });
                    }
                });
            });

    })
    $(".dataOn").click(function () {
        var updateId=sessionStorage.getItem("verifyUserId");

        admin.popup({
            id: 'LAY-admin2-detail',
            area: ['650px', '520px'],
            success: function (layero, index) {
                view(this.id).render('record/profitPickRecord/userEchars').done(function () {


                    admin.req({
                        url: layui.setter.apiUrl + '/hfz/record/listAll',
                        type: 'post',
                        data: {
                            userId:updateId,
                        },
                        success: function (res) {
                            var data1=new Array()
                            var data2=new Array()
                            $.each(res.data, function(i,item){
                                data1[i]=item.getTime;
                                data2[i]=item.amount;

                            })
                            // 基于准备好的dom，初始化echarts实例
                            var myChart = echarts.init(document.getElementById('main'));
                            // 指定图表的配置项和数据
                            var option = {
                                xAxis: {
                                    type: 'category',
                                    data: data1
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                dataZoom: [
                                    {
                                        type: 'slider',
                                        xAxisIndex: 0,
                                        filterMode: 'empty'
                                    },
                                    {
                                        type: 'slider',
                                        yAxisIndex: 0,
                                        filterMode: 'empty'
                                    },
                                    {
                                        type: 'inside',
                                        xAxisIndex: 0,
                                        filterMode: 'empty'
                                    },
                                    {
                                        type: 'inside',
                                        yAxisIndex: 0,
                                        filterMode: 'empty'
                                    }
                                ],
                                yAxis: {
                                    type: 'value'
                                },
                                series: [{
                                    data: data2,
                                    type: 'line',
                                    smooth: true
                                }]
                            };
                            // 使用刚指定的配置项和数据显示图表。
                            myChart.setOption(option);
                        }
                    });

                });
            }
        });
    })



    exports('verifyRecord', {});
});