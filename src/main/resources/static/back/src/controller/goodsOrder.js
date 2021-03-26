/**
 * 用户管理--小金库
 * loo
 * 2018-05-30
 */
layui.define(['view', 'table', 'util', 'form', 'selectLink', 'common', 'upload', 'echarts'], function (exports) {
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
var listGoodOrder;
    form.render();
    table.render({
        elem: '#LAY-goodsOrder-list',
        id: 'LAY-goodsOrder-list',
        url: layui.setter.apiUrl + '/hfz/goodsOrder/list',
        title: '订单列表',
        height: 'full-215', //高度最大化减去差值
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
        toolbar:true,
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }],
        cols: [
            [ //表头
                {
                    type: 'numbers',
                    title: '序号',
                    fixed: 'left',
                }, {
                field: 'userName',
                title: '用户昵称',
                align: 'center',
            },{
                field: 'userPhone',
                title: '用户手机号',
                align: 'center',
            }, {
                field: 'goodsName',
                title: '商品',
                align: 'center',

            }, {
                field: 'num',
                title: '兑换数量',
                align: 'center',
                style:"color:blue"
            }, {
                title: '订单状态',
                align: 'center',
                templet: function (d) {
                    var html="";
                    if(d.orderStatus==1){
                        html="<span style='color: red'>未发货</span>";
                    }else if (d.orderStatus==2){
                        html="<span style='color: blue'>已发货</span>";
                    } else if (d.orderStatus==3){
                        html="<span style='color: deepskyblue'>已完成</span>";
                    } else if(d.orderStatus==4){
                        html="<span style='color: chartreuse'>订单异常</span>";
                    }else{
                        html="<span style='color: yellow'>未定义</span>";
                    }
                    return html;
                }
            }, {
                field: 'orderCode',
                title: '物流号',
                align: 'center',
                style:"color:red"
            }, {
                field: 'orderNo',
                title: '订单号',
                align: 'center',
            }, {
                field: 'remarks',
                title: '备注',
                align: 'center',
            }, {
                field: 'exchangeTime',
                title: '兑换时间',
                align: 'center',
            }, {
                fixed: 'right',
                width: 200,
                title: '操作',
                align: 'center',
                templet: function (d) {
                    return "<a class=\"layui-btn  layui-btn-warm layui-btn-xs event-btn\"  lay-event=\"goodsOrders\"><i class=\"layui-icon layui-icon-radio\"></i>订单处理</a>"
                        + "<a class=\"layui-btn  layui-btn-normal layui-btn-xs event-btn \"  lay-event=\"goodsData\"><i class=\"layui-icon layui-icon-search\"></i>数据</a>"
                       /* +"<a class=\"layui-btn  layui-btn-normal layui-btn-xs event-btn \"  lay-event=\"goodsDataD\"><i class=\"layui-icon layui-icon-search\"></i>数据-</a>"*/;
                }
            }
            ]
        ],
        done: function(res, curr, count){
            //获取全部数据
            listGoodOrder=res.data;
           // alert(JSON.stringify(listGoodOrder))


        }
    });

    //修改json  key值

    /**/
    //旧key到新key的映射
/*
    var keyMap = {
        "id" : "value",
        "name" : "label"
    };
*/

    function arraySort(array,keyMap){

        for(var i = 0;i < array.length;i++){
            var obj = array[i];
            for(var key in obj){
                var newKey = keyMap[key];
                if(newKey){
                    obj[newKey] = obj[key];
                    delete obj[key];
                }
            }
        }
    }



   table.on('toolbar(LAY-goodsOrder-list)', function (obj) {
       /* var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        alert(data.length)
        alert(JSON.stringify(data))*/
/*

    console.log(JSON.stringify(listGoodOrder))

        //转换key 条件
       var keyMap = {
           "province" : "1",
           "city" : "2",
           "county":"3",
       };
       for(var i=0;i<listGoodOrder.length;i++) {
            delete listGoodOrder[i].userId;
            delete listGoodOrder[i].goodsId;
            delete listGoodOrder[i].delStatus;
            delete listGoodOrder[i].id;
            delete listGoodOrder[i].name;
            if(listGoodOrder[i].orderStatus==1){
                listGoodOrder[i].orderStatus="未发货";
            }else if(listGoodOrder[i].orderStatus==2){
                listGoodOrder[i].orderStatus="已发货";
            }else if(listGoodOrder[i].orderStatus==3){
                listGoodOrder[i].orderStatus="已完成";
            }else if(listGoodOrder[i].orderStatus==4){
                listGoodOrder[i].orderStatus="订单异常";
           }



         /!*  var ordered = {};
           Object.keys(listGoodOrder[i]).sort().forEach(function(key) {
               ordered[key] = listGoodOrder[i][key];
           });

           listGoodOrder[i]=ordered;*!/


           arraySort(listGoodOrder[i],keyMap)



       }
       console.log(listGoodOrder)
*/




        switch (obj.event) {
            case 'LAYTABLE_TIPS':

                admin.req({
                    url: layui.setter.apiUrl + '/hfz/goodsOrder/lists',
                    type: 'get',
                    success: function (res) {

                        console.log((res))



                        var table="<p style='text-align: center;font-weight: bold;'>订单详情</p>"
                        table+="<table border='1' cellspacing='0' style='width: 100%'>";//这个字符串第一位为# 颜色的格式
                        table+="<tr><th width='40px'>序号</th><th>地址</th><th>手机号</th><th width='70px'>姓名</th><th>商品名称</th><th width='50px'>数量</th><th width='110px'>兑换时间</th><th>备注</th></tr>"
                        for(var i=0;i<res.data.length;i++) {

                            table += "<tr><td>"+parseInt(i+1)+"</td>";
                            for (var key in res.data[i]){

                                table = table+"<td>"+res.data[i][key]+"</td>";
                            }
                            table+="</tr>";
                        }
                        table+="</table>";



                        var wind = window.open("",'newwindow', 'height=300, width=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                        wind.document.body.innerHTML = table;
                        wind.print();
                    }
                })




        }
        ;
    });



    //监听行工具事件
    table.on('tool(LAY-goodsOrder-list)', function (obj) {
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'goodsOrders') {
            admin.popup({
                id: 'LAY-goodsOrder-detail',
                title: '订单处理',
                area: ['600px', '400px'],
                success: function (layero, index) {
                    view(this.id).render('goods/goodsOrder/detail', data).done(function () {
                        form.render()

                        //更新
                        form.on('submit(LAY-goodsOrder-submit)', function (obj) {
                            obj.field.id = data.id;
                            admin.req({
                                url: layui.setter.apiUrl + '/hfz/goodsOrder/update',
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
                                        table.reload("LAY-goodsOrder-list")
                                    });
                                }
                            });
                        });

                    });
                }
            });
        }else  if (layEvent === 'goodsData') {  //新增
            var updateId=data.userId;
            admin.popup({
                id: 'LAY-admin2-detail',
                area: ['650px', '520px'],
                success: function (layero, index) {
                    view(this.id).render('record/profitPickRecord/userEchars').done(function () {
                        admin.req({
                            url: layui.setter.apiUrl + '/hfz/userIntegralRecord/listAll',
                            type: 'post',
                            data: {
                                userId:updateId,
                            },
                            success: function (res) {
                                var data1=new Array()
                                var data2=new Array()
                                var data3=new Array()

                                $.each(res.data, function(i,item){
                                    data1[i]=item.useTime;
                                    if(item.type==1){
                                        data2[i]=item.num;
                                    }else if(item.type==2){
                                        data3[i]=item.num;
                                    }
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
                                        name:'新增',
                                        data: data2,
                                        type: 'line',
                                    },
                                    {
                                        name:'减少',
                                        type:'line',
                                        data:data3
                                    }]
                                };
                                // 使用刚指定的配置项和数据显示图表。
                                myChart.setOption(option);
                            }
                        });

                    });
                }
            });
        }/*/!*else  if (layEvent === 'goodsDataD') {  //减少
            admin.popup({
                id: 'LAY-admin2-detail',
                area: ['650px', '520px'],
                success: function (layero, index) {
                    view(this.id).render('record/profitPickRecord/userEchars').done(function () {
                        admin.req({
                            url: layui.setter.apiUrl + '/hfz/userIntegralRecord/listAll',
                            type: 'post',
                            data: {
                                userId:updateId,
                            },
                            success: function (res) {
                                var data1=new Array()
                                var data2=new Array()
                                var x=0;
                                $.each(res.data, function(i,item){
                                    if(item.type==2){
                                        data1[x]=item.useTime;
                                        data2[x]=item.num;
                                        x++;
                                    }
                                })

                                alert(res.data.length)
                                alert(data1.length)
                                // 基于准备好的dom，初始化echarts实例
                                var myChart = echarts.init(document.getElementById('main'));
                                // 指定图表的配置项和数据
                                var option = {
                                    xAxis: {
                                        type: 'category',
                                        data: data1
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

*!/


        }*/
    });




//监听搜索
    form.on('submit(LAY-goodsOrder-search)', function(data) {
        var field = data.field;


        //执行重载
        table.reload('LAY-goodsOrder-list', {
            where: field
        });
    });


    exports('goodsOrder', {});
});