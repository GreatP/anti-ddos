/**
 * Created by lb on 2015/10/8.
 */
function checkTime(i)
{
    if (i<10)
    {
        i="0" + i;
    }
    return i
}

var protocolCode = {
    0 : "OTHER",
    1 : "ICMP",
    6 : "TCP",
    17: "UDP"
};

'use strict';
app.controller('dataOverviewController', ['$scope', '$http','$localStorage', '$location', '$timeout', '$animate', '$translate', '$state', function ($scope, $http,$alerts, $location, $timeout, $animate, $translate, $state) {
//app.controller('overViewController', ['$scope', '$http','$localStorage', '$timeout', '$animate','$stateParams', function ($scope, $http,$alerts, $timeout, $animate,$stateParams) {

    var contrastApi;
    var now;
    var hour;
    var day;
    var year;
    var mon;
    var minute;
    var second;
    var time = "";

    $scope.contrastOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        legend: {
            y: 'top',
            data: [$translate.instant('dataOverView.INPUT_FLOW'),
                $translate.instant('dataOverView.OUTPUT_FLOW'),
                $translate.instant('dataOverView.ATTACK_FLOW')]
        },
        xAxis: [
            {
                type: 'category',
                axisLabel:{
                    interval:1,
                    rotate:30,
                    margin:2,
                    textStyle:{
                        color:"#222"
                    }
                },
                name: $translate.instant('dataOverView.TIME'),
                boundaryGap: false,

                //data: ['1','2','3','4','5','6','7','8','9','10','11','12']
                data: [0]//xAxis中的data字段必须赋值为data:[0]，否则图像无法显示
            }
        ],

        //grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name: $translate.instant('dataOverView.TRAFFIC')
            }
        ],
        series: [
            {
                "name": $translate.instant('dataOverView.INPUT_FLOW'),
                //"type":"bar",
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        color: '#00fa9a'
                    }
                },
                //data:[1010,1,4,51,150,35,300,6,17,67,34,32]
               data: []
            },
            {
                "name": $translate.instant('dataOverView.OUTPUT_FLOW'),
                //"type":"bar",
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        color: '#ff0000'
                    }
                },
               // data:[1,2,3,4,5,6,7,8,9,10,11,12]
                data: []
            },
            {
                "name": $translate.instant('dataOverView.ATTACK_FLOW'),
                //"type":"bar",
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        color: '#4000ff'
                    }
                },
                data: []
            }
        ]
    };

    $scope.inputFlowPieOption = {
        version : 0,
        legend: {
            y: 'bottom',
            //data: [$translate.instant('dataOverView.PROTOCOL_TCP'),
            //       $translate.instant('dataOverView.PROTOCOL_UDP'),
            //       $translate.instant('dataOverView.PROTOCOL_ICMP'),
            //       $translate.instant('dataOverView.PROTOCOL_OTHER')]
            data:[]
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c} B/s  ({d}%)"
        },
        toolbox: {
            show : false
        },
        calculable : true,
        series : [
            {
                type:'pie',
                smooth: true,
                radius:'65%',
                itemStyle:{
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                center: ['50%', '40%'],
                data:[]
            }
        ]
    };


    /* 柱状图 */
    $scope.poFlowOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            y: 'bottom',
            data:[]
        },
        calculable : true,
        xAxis: [
            {
                type: 'category',
                axisLabel:{
                    interval:0,
                    rotate:30,
                    margin:2,
                    textStyle:{
                        color:"#222"
                    }
                },
                name: $translate.instant('dataOverView.PO'),
                data:[0]
            }
        ],
        yAxis: [
            {
                type: 'value',
                name:$translate.instant('dataOverView.TRAFFIC')
                //min: 0,
                //max: 250,
                //interval: 50
            }
        ],
        series: [
            {
                name:$translate.instant('dataOverView.TRAFFIC'),
                itemStyle:{
                    normal: {
                        //color: function (value){
                        //    return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6);
                        //}
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                                '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                            ];
                            return colorList[params.dataIndex]
                        }
                    }
                },
                type:'bar',
               data:[]
            }
        ]
    };

    /* 柱状图 */
    $scope.ipFlowOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            y: 'bottom',
            data:[]
        },
        calculable : true,
        xAxis: [
            {
                type : 'category',
                axisLabel:{
                    interval:0,
                    rotate:30,
                    margin:2,
                    textStyle:{
                        color:"#222"
                    }
                },
                name: $translate.instant('dataOverView.IP'),
                data:[0]
            }
        ],
        yAxis: [
            {
                type: 'value',
                name:$translate.instant('dataOverView.TRAFFIC')
            }
        ],
        series: [
            {
                name:$translate.instant('dataOverView.TRAFFIC'),
                itemStyle: {
                    normal: {
                        color: function(param) {
                            var colorList = [
                                '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0',
                                '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B'
                            ];
                            return colorList[param.dataIndex]
                        }
                    }
                },
                type:'bar',
                data:[]
            }
        ]
    };

    $scope.getDataOverViewSuccess = function (response) {
        console.log(response);

        /* flow contrast chart data */
        var flowContrastInfo = response[0];
        var traffic_type;
        var rate_bps;

        if(flowContrastInfo.length != 0) {
            for (var i = 0; i < $scope.contrastOption.series.length; i++) {
                $scope.contrastOption.series[i].data = [];
            }

            $scope.contrastOption.xAxis[0].data = [];
            if (flowContrastInfo.length <= 12) {
                $scope.contrastOption.xAxis[0].axisLabel.interval = 0;
            } else if (flowContrastInfo.length >=13 && flowContrastInfo.length <= 24){
                $scope.contrastOption.xAxis[0].axisLabel.interval = 1;
            } else if (flowContrastInfo.length >=25 && flowContrastInfo.length <= 36){
                $scope.contrastOption.xAxis[0].axisLabel.interval = 2;
            } else {
                $scope.contrastOption.xAxis[0].axisLabel.interval = parseInt(flowContrastInfo.length/12, 10);
            }

            var g = flowContrastInfo.length;
            for (var i = 0; i < g; i++) {

                now = new Date(flowContrastInfo[i].timestamp);
                year = now.getFullYear();
                mon = now.getMonth() + 1;//1-12月
                day = now.getDate();
                hour = now.getHours();//0-23
                minute = now.getMinutes();//0-59
                second = now.getSeconds();
                //time = year + "-" + checkTime(mon) + "-" + checkTime(day) + " " + checkTime(hour) + ":" + checkTime(minute);
                time = checkTime(mon) + "-" + checkTime(day) + " " + checkTime(hour) + ":" + checkTime(minute) + ":" + checkTime(second);
                $scope.contrastOption.xAxis[0].data.push(time);

                $scope.contrastOption.series[0].data.push(flowContrastInfo[i].input_traffic_bps);
                $scope.contrastOption.series[1].data.push(flowContrastInfo[i].output_traffic_bps);
                $scope.contrastOption.series[2].data.push(flowContrastInfo[i].attack_traffic_bps);
            }
            $scope.contrastOption.version++;
        }

        /* input flow pie chart*/
        var inputFlowInfo = response[1];

        $scope.inputFlowPieOption.series[0].data = [];
        var proname = "unkown";
        $scope.inputFlowPieOption.legend.data = [0];
        if (inputFlowInfo.length != 0) {
            var j = inputFlowInfo.length;
            for (var i =0;i<j;i++) {
                switch (inputFlowInfo[i]['protocol']) {
                    case 0:
                        proname = "OTHER";
                        break;
                    case 1:
                        proname = "ICMP";
                        break;
                    case 6:
                        proname = "TCP";
                        break;
                    case 17:
                        proname = "UDP";
                        break;
                    default :
                        proname = "OTHER";
                        break;
                }

                $scope.inputFlowPieOption.series[0].data.push({value:inputFlowInfo[i]['rate_bps'],name:proname});
                $scope.inputFlowPieOption.legend.data[i] = proname;
            }
            //$scope.inputFlowPieOption.series[0].data.push({value:inputFlowInfo['udp_bps'],name:"UDP"});
            //$scope.inputFlowPieOption.series[0].data.push({value:inputFlowInfo['tcp_bps'],name:"TCP"});
            //$scope.inputFlowPieOption.series[0].data.push({value:inputFlowInfo['other_bps'],name:"OTHER"});
        }

        $scope.inputFlowPieOption.version++;


            /* po flow Top 10 chart data */
        var poFlowlInfo = response[2];

       $scope.poFlowOption.series[0].data = [];
       $scope.poFlowOption.legend.data = [];
       $scope.poFlowOption.xAxis[0].data = [];
        var k = poFlowlInfo.length;
       for (var i=0;i<k;i++) {
            $scope.poFlowOption.series[0].data.push(poFlowlInfo[i].poTrafficInfo.rate_bps);
            $scope.poFlowOption.xAxis[0].data.push(poFlowlInfo[i].poName);
       }

        $scope.poFlowOption.version++;


        /* IP flow Top 10 chart data */
        var ipFlowlInfo = response[3];
        $scope.ipFlowOption.series[0].data = [];
        $scope.ipFlowOption.xAxis[0].data = [];
        $scope.ipFlowOption.legend.data = [];
        var h = ipFlowlInfo.length;
        for (var i=0;i<h;i++) {
            $scope.ipFlowOption.series[0].data.push(ipFlowlInfo[i].newPeak);
            $scope.ipFlowOption.xAxis[0].data.push(ipFlowlInfo[i].ip);
        }
        $scope.ipFlowOption.version++;
    }

    $scope.getDataOverViewError = function (response, status) {
        console.log("getDataOverViewError " + response);
        console.log(status);
    }

    $scope.dataOverViewInfo = function () {
        var requestUrl = getUrl($location, 'rest/dataOverview/dataDisplay');
        httpGet($http, requestUrl, null, $scope.getDataOverViewSuccess, $scope.getDataOverViewError);

        var timer = $timeout(function () {
            $scope.dataOverViewInfo();
        }, 5000);

        $scope.$on(
            "$destroy",
            function (event) {
                $timeout.cancel(timer);
            }
        );

    }

    $scope.dataOverViewInfo();
}]);
