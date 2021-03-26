/**
 *

 @Name：layuiAdmin Echarts集成
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：GPL-2

 */


layui.define(function (exports) {
    layui.use(['echarts','element'], function () {
        var $ = layui.$,
            admin = layui.admin
            , echarts = layui.echarts
            ,element = layui.element; element.init();


/*      页面加载完成后触发
        $(document).ready(function(){
            var userName="xiaoming";

            alert(userName);
            });*/








            //循环定时任务 刷新页面
        setTimeout(function () {
            admin.events.refresh();
        }, 600000);-


//今日新增----查询当日注册的用户
        admin.req({
            url: layui.setter.apiUrl + '/hfz/console/userInsert',
            type: 'post',
            async: false,
            success: function (res) {
                $("#userThs").html(res.data.userThs);
                $("#userAll").html(res.data.userAll);


                //今日视频下载数/今日视频查看数量
                $("#taskVideoThs").html(res.data.taskVideoDownloadThs + "/" + res.data.taskVideoThs);

                //全部视频下载数/全部视频查看数
                $("#taskVideoAll").html(res.data.taskVideoDownloadAll + "/" + res.data.taskVideoAll);

                //今日浏览下载数/今日浏览查看数量
                $("#taskBrowseThs").html(res.data.taskBrowseDownloadThs + "/" + res.data.taskBrowseThs);

                //全部浏览下载数/全部浏览查看数
                $("#taskBrowseAll").html(res.data.taskBrowseDownloadAll + "/" + res.data.taskBrowseAll);

            }
        });


        //地图    用户来源地
        $.getScript("https://gallery.echartsjs.com/dep/echarts/map/js/china.js", function () {

            var myChart = echarts.init(document.getElementById('map_div'));
            var chinaGeoCoordMap = {
                '新疆': [86.61, 40.79],
                '新疆维吾尔自治区': [86.61, 40.79],
                '西藏': [89.13, 30.66],
                '西藏自治区': [89.13, 30.66],
                '黑龙江': [128.34, 47.05],
                '吉林': [126.32, 43.38],
                '辽宁': [123.42, 41.29],
                '内蒙古': [112.17, 42.81],
                '内蒙古自治区': [112.17, 42.81],
                '北京': [116.40, 40.40],
                '宁夏': [106.27, 36.76],
                '宁夏回族自治区': [106.27, 36.76],
                '山西': [111.95, 37.65],
                '河北': [115.21, 38.44],
                '天津': [117.04, 39.52],
                '青海': [97.07, 35.62],
                '甘肃': [103.82, 36.05],
                '山东': [118.01, 36.37],
                '陕西': [108.94, 34.46],
                '河南': [113.46, 34.25],
                '安徽': [117.28, 31.86],
                '江苏': [120.26, 32.54],
                '上海': [121.46, 31.28],
                '上海市': [121.46, 31.28],
                '四川': [103.36, 30.65],
                '湖北': [112.29, 30.98],
                '浙江': [120.15, 29.28],
                '重庆': [107.51, 29.63],
                '湖南': [112.08, 27.79],
                '江西': [115.89, 27.97],
                '贵州': [106.91, 26.67],
                '福建': [118.31, 26.07],
                '云南': [101.71, 24.84],
                '台湾': [121.01, 23.54],
                '广西': [108.67, 23.68],
                '广东': [113.98, 22.82],
                '海南': [110.03, 19.33],
                '澳门': [113.54, 22.19],
                '澳门特别行政区': [113.54, 22.19],
                '香港': [114.17, 22.32],
                '广西壮族自治区':[108.67, 23.68],
                '北京市':[116.67, 40.68],
                '重庆市':[106.55, 29.57],
                '天津市':[117.2 , 39.13],

                'XX': [69.65346,44.755958],
            };

            //获取数据
            //向服务端发送删除指令
            var chinaDatas = new Array()
            admin.req({
                url: layui.setter.apiUrl + '/hfz/console/lastLoginIp',
                type: 'post',
                async: false,
                success: function (res) {
                    $.each(res.data, function (i, item) {
                        var json = {};
                        var arr = new Array()
                        json.name = item.name;
                        json.value = item.number / 1000;
                        arr[0] = json;
                        chinaDatas[i] = arr;
                    })
                }
            });


            var convertData = function (data) {
                var res = [];
                for (var i = 0; i < data.length; i++) {
                    var dataItem = data[i];

                    console.log(111111111111)
                    console.log(dataItem)
                    var fromCoord = chinaGeoCoordMap[dataItem[0].name];
                    var toCoord = [120.26, 32.54];
                    if (fromCoord && toCoord) {
                        res.push([{
                            coord: fromCoord,
                            value: dataItem[0].value
                        }, {
                            coord: toCoord,
                        }]);
                    }
                }
                return res;
            };
            var series = [];
            [['江苏', chinaDatas]].forEach(function (item, i) {
                series.push({
                        type: 'lines',
                        zlevel: 2,
                        effect: {
                            show: true,
                            period: 4, //箭头指向速度，值越小速度越快
                            trailLength: 0.02, //特效尾迹长度[0,1]值越大，尾迹越长重
                            symbol: 'arrow', //箭头图标
                            symbolSize: 5, //图标大小
                        },
                        lineStyle: {
                            normal: {
                                width: 1, //尾迹线条宽度
                                opacity: 1, //尾迹线条透明度
                                curveness: .3 //尾迹线条曲直度
                            }
                        },
                        data: convertData(item[1])
                    }, {
                        type: 'effectScatter',
                        coordinateSystem: 'geo',
                        zlevel: 2,
                        rippleEffect: { //涟漪特效
                            period: 4, //动画时间，值越小速度越快
                            brushType: 'stroke', //波纹绘制方式 stroke, fill
                            scale: 4 //波纹圆环最大限制，值越大波纹越大
                        },
                        label: {
                            normal: {
                                show: true,
                                position: 'right', //显示位置
                                offset: [5, 0], //偏移设置
                                formatter: function (params) {//圆环显示文字
                                    return params.data.name;
                                },
                                fontSize: 13
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        symbol: 'circle',
                        symbolSize: function (val) {
                            return 5 + val[2] * 5; //圆环大小
                        },
                        itemStyle: {
                            normal: {
                                show: false,
                                color: '#f00'
                            }
                        },
                        data: item[1].map(function (dataItem) {
                            console.log(33333333);
                            console.log(dataItem[0].name);
                            console.log(chinaGeoCoordMap[dataItem[0].name]);
                            return {
                                name: dataItem[0].name,
                                value: chinaGeoCoordMap[dataItem[0].name].concat([dataItem[0].value])
                            };
                        }),
                    },
                    //被攻击点
                    {
                        type: 'scatter',
                        coordinateSystem: 'geo',
                        zlevel: 2,
                        rippleEffect: {
                            period: 4,
                            brushType: 'stroke',
                            scale: 4
                        },
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                //offset:[5, 0],
                                color: '#0f0',
                                formatter: '{b}',
                                textStyle: {
                                    color: "#0f0"
                                }
                            },
                            emphasis: {
                                show: true,
                                color: "#f60"
                            }
                        },
                        symbol: 'pin',
                        symbolSize: 50,
                        data: [{
                            name: item[0],
                            value: chinaGeoCoordMap[item[0]].concat([10]),
                        }],
                    }
                );
            });

            option = {
                tooltip: {
                    trigger: 'item',
                    backgroundColor: 'rgba(166, 200, 76, 0.82)',
                    borderColor: '#FFFFCC',
                    showDelay: 0,
                    hideDelay: 0,
                    enterable: true,
                    transitionDuration: 0,
                    extraCssText: 'z-index:100',
                    formatter: function (params, ticket, callback) {
                        //根据业务自己拓展要显示的内容
                        var res = "";
                        var name = params.name;
                        var value = params.value;
                        res = "<span style='color:#fff;'>" + name + "</span><br/>人数(千人)：" + value;
                        return res;
                    }
                },
                backgroundColor: "#013954",
                visualMap: { //图例值控制
                    min: 0,
                    max: 100,
                    calculable: true,
                    show: true,
                    color: ['#f44336', '#fc9700', '#ffde00', '#ffde00', '#00eaff'],
                    textStyle: {
                        color: '#fff'
                    }
                },
                geo: {
                    map: 'china',
                    zoom: 1.2,
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    roam: true, //是否允许缩放
                    itemStyle: {
                        normal: {
                            color: 'rgba(51, 69, 89, .5)', //地图背景色
                            borderColor: '#516a89', //省市边界线00fcff 516a89
                            borderWidth: 1
                        },
                        emphasis: {
                            color: 'rgba(37, 43, 61, .5)' //悬浮背景
                        }
                    }
                },
                series: series
            };

            //初始化地图
            myChart.setOption(option);
            //随界面变动div 大小
            window.onresize = myChart.resize;
        });
        //财务审核信息
        active();

        function active() {
            var myChart = echarts.init(document.getElementById('char1'));
            var data1 = new Array();
            var json = [];
            admin.req({
                url: layui.setter.apiUrl + '/hfz/console/profitPickRecordCount',
                type: 'post',
                async: false,
                success: function (res) {
                    var x = 0;
                    for (var key in res.data) {
                        data1[x] = key;
                        var row = {};
                        row.value = res.data[key];
                        row.name = key;
                        json.push(row)
                        x++
                    }
                }
            })

            option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: data1
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: json,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            myChart.on("click", function (param) {
                /*alert(param.name);
                alert(param.value);*/

                //   location.href="index.html#/record/profitPickRecord/index.html?verifyStatus="+param.value;
                location.hash = '/record/profitPickRecord/type=' + param.value + "/";


            });


            //初始化地图
            myChart.setOption(option);


        }

        //当日预计收入与支出
        income();

        function income() {
            var myChart = echarts.init(document.getElementById('char2'));


            var data1 = new Array();
            var data2 = new Array();
            admin.req({
                url: layui.setter.apiUrl + '/hfz/console/payDay',
                type: 'post',
                async: false,
                success: function (res) {

                    $.each(res.data.reverse(), function (i, item) {
                        data1[i] = item.dayTime;
                        data2[i] = item.amount/100;
                    })
                }
            })
            option = {
                color: ['#3398DB'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: data1,
                        axisTick: {
                            alignWithLabel: true
                        },
                        //设置轴线的属性
                        axisLine: {
                            lineStyle: {
                                color: '#FDA500',
                                width: 8,//这里是为了突出显示加上的
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        //设置轴线的属性
                        axisLine: {
                            lineStyle: {
                                color: '#FDA500',
                                width: 8,//这里是为了突出显示加上的
                            }
                        }
                    }
                ],
                series: [
                    {
                        name: '支出金额',
                        type: 'bar',
                        barWidth: '60%',
                        data: data2
                    }
                ]
            };
            //初始化地图
            myChart.setOption(option);
        }


        //集合根据某字段排序
        function compare(property) {
            return function (a, b) {
                var value1 = a[property];
                var value2 = b[property];
                return value2 - value1;
            }
        }

//排名
        admin.req({
            url: layui.setter.apiUrl + '/hfz/console/userIntegralRecord',
            type: 'post',
            async: false,
            success: function (res) {


                //全部福币排行
                var groupSortAll = res.data.groupSortAll.sort(compare('num'));
                $("#groupSortAll").html("");
                for (var x = 0; x < groupSortAll.length; x++) {
                    $("#groupSortAll").append("<tr><td>" + parseInt(x + 1) + "</td><td>" + groupSortAll[x].userName + "</td><td>" + groupSortAll[x].num + "</td></tr>")
                }

                //今日福币排行
                var groupSortThs = res.data.groupSortThs.sort(compare('num'));
                $("#groupSortThs").html("");
                for (var x = 0; x < groupSortThs.length; x++) {
                    $("#groupSortThs").append("<tr><td>" + parseInt(x + 1) + "</td><td>" + groupSortThs[x].userName + "</td><td>" + groupSortThs[x].num + "</td></tr>")
                }

            }
        });
        // 收益
        admin.req({
            url: layui.setter.apiUrl + '/hfz/console/profitRecordSort',
            type: 'post',
            async: false,
            success: function (res) {
                //全部收益排行
                var groupSortAll = res.data.groupSortAll.sort(compare('amount'));
                $("#groupSortAllMoney").html("");
                for (var x = 0; x < groupSortAll.length; x++) {
                    $("#groupSortAllMoney").append("<tr><td>" + parseInt(x + 1) + "</td><td>" + groupSortAll[x].userName + "</td><td>" + groupSortAll[x].amount / 100 + "</td></tr>")
                }

                //今日收益排行
                var groupSortThs = res.data.groupSortThs.sort(compare('amount'));
                $("#groupSortThsMoney").html("");
                for (var x = 0; x < groupSortThs.length; x++) {
                    $("#groupSortThsMoney").append("<tr><td>" + parseInt(x + 1) + "</td><td>" + groupSortThs[x].userName + "</td><td>" + groupSortThs[x].amount / 100 + "</td></tr>")
                }

            }
        });


        //收徒
        // 收益
        admin.req({
            url: layui.setter.apiUrl + '/hfz/console/userParentCount',
            type: 'post',
            async: false,
            success: function (res) {
                //收徒排行
                var groupSortThs = res.data.sort(compare('parentId'));
                $("#userParent").html("");
                for (var x = 0; x < groupSortThs.length; x++) {
                    $("#userParent").append("<tr><td>" + parseInt(x + 1) + "</td><td>" + groupSortThs[x].nickName + "</td><td>" + groupSortThs[x].parentId + "</td></tr>")
                }
            }
        });


        exports('senior', {})
    });
});