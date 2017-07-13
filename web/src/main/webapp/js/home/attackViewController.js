'use strict';

function AttackStatusTransform(val) {
    var status = "";
    switch (val) {
        case 1:
            status = "攻击中";
            break;
        case 2:
            status = "结束";
            break;
        default:
            status = "攻击中";
            break;
    }

    return status;
}

function BytesUnitTransForm(val) {
    var result;

    if (val <= 1024) {
        result = val + "B";
    } else if (val > 1024 && val <= 1024 * 1024) {
        result = parseInt(val / 1024) + "KB";
    } else if (val > 1024 * 1024 && val <= 1024 * 1024 * 1024){
        result = parseInt(val / (1024 * 1024)) + "MB";
    } else if (val > 1024 * 1024 * 1024) {
        result = parseFloat(val / (1024 * 1024 * 1024)).toFixed(2) + "GB";
    }

    return result;
}

function checkTime(i)
{
    if (i<10)
    {
        i="0" + i;
    }
    return i
}

var spCode = {
    1 : "攻击中",
    2 : "攻击结束"
};

var attackTypeStr = {
    0:  "Unknown",
    1:  "TCP Abnormal",
    2:  "TCP SYN",
    3:  "TCP SYN ACK",
    4:  "TCP ACK",
    5:  "TCP FIN RST",
    6:  "TCP FLAG",
    7:  "ICMP Flood",
    8:  "PING of Death",
    9:  "ICMP Huge",
    10: "ICMP Redirect",
    11: "UDP Flood",
    12: "DNS Abnormal",
    13: "DNS Request",
    14: "DNS Reply",
    15: "HTTP Get",/*即HTTP FLOOD*/
    16: "HTTP Slow Head",
    17: "HTTP Slow Post",
    18: "HTTPS Flood",
    19: "HTTPS Thc SSL",
    254: "Mix Attack" /*混合攻击，即多种攻击一起*/
};

var attackTypeDistribution = {
    0:  "UNKNOWN",
    1:  "TCP ABNORMAL",
    2:  "TCP SYN",
    3:  "TCP SYN ACK",
    4:  "TCP ACK",
    5:  "TCP FIN RST",
    6:  "TCP FLAG",
    7:  "ICMP FLOOD",
    8:  "PING OF DEATH",
    9:  "ICMP HUGE",
    10: "ICMP REDIRECT",
    11: "UDP FLOOD",
    12: "DNS ABNORMAL",
    13: "DNS REQUEST",
    14: "DNS REPLY",
    15: "HTTP GET",/*即HTTP FLOOD*/
    16: "HTTP SLOW HEAD",
    17: "HTTP SLOW POST",
    18: "HTTPS FLOOD",
    19: "HTTPS THC SSL",
    254: "MIX ATTACK" /*混合攻击，即多种攻击一起*/
};

app.controller('attackViewController', ['$scope', '$http','$localStorage', '$location', '$timeout', '$animate', '$translate', '$state', function ($scope, $http,$alerts, $location, $timeout, $animate, $translate, $state) {

    var now;
    var endTime;
    var start;
    var interval = (6 * 3600 * 1000 * 24);
    var hour;

    var beginDay;
    var year;
    var mon;
    var day;

    var beginMon;
    var endYear;
    var endMon;
    var endDay;
    var endHour;
    var endMinute;
    var minute;
    var second;
    var time = "";

    $scope.attackNumber = 0;
    $scope.defenseNumber = 0;
    $scope.cleanTraffic = 0;
    $scope.attackSrcNumber = 0;

    /*$scope.legend = [$translate.instant('attackView.SYN_FLOOD'),
                    $translate.instant('attackView.ACK_FLOOD'),
                    $translate.instant('attackView.SYN_ACK_FLOOD'),
                    $translate.instant('attackView.FIN_RST_FLOOD'),
                    $translate.instant('attackView.UDP_FLOOD'),
                    $translate.instant('attackView.ICMP_FLOOD'),
                    $translate.instant('attackView.DNS_FLOOD'),
                    $translate.instant('attackView.CC_ATTACK')];*/

    $scope.attackTypeOption = pipeChart();

   /* var cf = 1;
    $scope.attackTypeOption.series[0].data = [];
    $scope.attackTypeOption.series[0].data.push({value:9,name:$scope.legend[0]});
    $scope.attackTypeOption.series[0].data.push({value:8,name:$scope.legend[1]});
    $scope.attackTypeOption.series[0].data.push({value:7,name:$scope.legend[2]});
    $scope.attackTypeOption.series[0].data.push({value:6,name:$scope.legend[3]});
    $scope.attackTypeOption.series[0].data.push({value:5,name:$scope.legend[4]});
    $scope.attackTypeOption.series[0].data.push({value:4,name:$scope.legend[5]});
    $scope.attackTypeOption.series[0].data.push({value:3,name:$scope.legend[6]});
    $scope.attackTypeOption.series[0].data.push({value:4,name:$scope.legend[7]});
    $scope.attackTypeOption.version++;*/




    /* 柱状图 */
    $scope.attackDefenseOption = {
        version:1,
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show : false
            //feature : {
            //    mark : {show: true},
            //    dataView : {show: true, readOnly: false},
            //    magicType: {show: true, type: ['line', 'bar']},
            //    restore : {show: true},
            //    saveAsImage : {show: true}
            //}
        },
        calculable : true,
        legend: {
            data:['防御次数','最大攻击流量（B/s）']
        },
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
                //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                data:[0]//xAxis中的data必须初始化为data:[0]的形式
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '防御次数(次)',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                name: '流量(B/s)',
                axisLabel: {
                formatter: '{value}'
            }
            }

        ],
        series: [
            {
                name:'最大攻击流量（B/s）',
                type:'bar',
                yAxisIndex: 1,
                //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 270.3, 23.4, 23.0, 16.5, 12.0, 6.2]
                data:[]
            },
            {
                name:'防御次数',
                type:'line',
                smooth:true,
                //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
                data:[]
            }
        ]
    };



    $scope.getAttackViewSuccess = function(response) {
       // console.log(response);

        /* attack data */
        var attackInfo = response[0];
        var attackType = "";
        if (attackInfo != null) {
            $scope.attackNumber = attackInfo['attackCount'];
            $scope.defenseNumber = attackInfo['defenseCount'];
            $scope.cleanTraffic = BytesUnitTransForm(attackInfo['cleanTraffic']);
            $scope.attackSrcNumber = attackInfo['attackSrcNum'];
        }


        /* attack type pie data */
        var attackTypeInfo = response[1];
        $scope.attackTypeOption.series[0].data = [];
        //后续需要改为下面这种方式动态显示有多少种攻击类型
        $scope.attackTypeOption.legend.data = [];
        var otherType = 0;
        for (var i = 0;i< attackTypeInfo.length; i++) {
            if (i < 9) {
                if(attackTypeDistribution[attackTypeInfo[i]['attack_type']] == null) {
                    attackType = "unknown";
                } else {
                    attackType = attackTypeDistribution[attackTypeInfo[i]['attack_type']];
                }
                $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo[i]['count'],name: attackType});
                $scope.attackTypeOption.legend.data[i] = attackTypeDistribution[attackTypeInfo[i]['attack_type']];
            } else if (i == (attackTypeInfo.length - 1)){
                $scope.attackTypeOption.series[0].data.push({value:otherType,name: "OTHER"});
                $scope.attackTypeOption.legend.data[9] = "OTHER";
            } else {
                otherType +=  attackTypeInfo[i]['count'];
            }

        }
        /*$scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['synFloodCount'],name:$scope.legend[0]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['ackFloodCount'],name:$scope.legend[1]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['synAckFloodCount'],name:$scope.legend[2]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['finRstFloodCount'],name:$scope.legend[3]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['udpFloodCount'],name:$scope.legend[4]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['icmpFloodCount'],name:$scope.legend[5]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['dnsFloodCount'],name:$scope.legend[6]});
        $scope.attackTypeOption.series[0].data.push({value:attackTypeInfo['ccFloodCount'],name:$scope.legend[7]});*/

        $scope.attackTypeOption.version++;

        /* attack defense option */
        var attackDefenseInfo = response[2];

        $scope.attackDefenseOption.series[0].data = [];
        $scope.attackDefenseOption.series[1].data = [];
        $scope.attackDefenseOption.xAxis[0].data = [];

        /* 需要完善时间间隔显示 */
        for (var i=0;i<attackDefenseInfo.length;i++) {
            now = new Date(attackDefenseInfo[i].saveTime);
            /*endTime = new Date(attackDefenseInfo[i].saveTime + 3600 * 1000 * 2);
            endYear = endTime.getFullYear();
            endMon = endTime.getMonth() + 1;
            endDay = endTime.getDate();
            endHour = endTime.getHours();
            endMinute = endTime.getMinutes();*/
            year = now.getFullYear();
            mon = now.getMonth()+1;//1-12月
            day = now.getDate();
            hour = now.getHours();//0-23
            minute = now.getMinutes();//0-59
            second = now.getSeconds();
            /*start = new Date(now.getTime() - interval);
            year = now.getFullYear();
            endMon = now.getMonth()+1;//1-12月
            beginMon = start.getMonth() + 1;
            endDay = now.getDate();
            beginDay = start.getDate();
            hour = now.getHours();//0-23
            minute = now.getMinutes();//0-59
            second = now.getSeconds();
            time = checkTime(beginMon) + "." + checkTime(beginDay) + "-" + checkTime(endMon) +"." + checkTime(endDay);*/
             time = year + "-" + checkTime(mon) + "-" + checkTime(day) + " " + checkTime(hour) + ":" + checkTime(minute);
           // time = checkTime(hour) + ":" + checkTime(minute) + "-" + checkTime(endTime.getHours()) + ":" + checkTime(endTime.getMinutes());
           // time = checkTime(mon) + "-" + checkTime(day) + " " + checkTime(hour) +"-" + checkTime(endTime.getHours());

            $scope.attackDefenseOption.series[0].data.push(attackDefenseInfo[i].maxBps);
            $scope.attackDefenseOption.series[1].data.push(attackDefenseInfo[i].defenseCount);
            $scope.attackDefenseOption.xAxis[0].data.push(time);
        }
        $scope.attackDefenseOption.version++;




        /*
        //攻击详细列表
        var statInfo = response[3];


        $scope.tableJson = "";
        $scope.tableLength = 0;
        $scope.tableloading = true;

        for (var i = 0; i < statInfo.stats.length; i++) {
            if (statInfo.stats[i]["status"] != null) {
                if (statInfo.stats[i]["status"] == 1) {
                    statInfo.stats[i]['endTime'] = "----";
                }
                statInfo.stats[i]["status"] = spCode[statInfo.stats[i]["status"]];
            }

            if (statInfo.stats[i]["attackType"] != null) {
                statInfo.stats[i]["attackType"] = attackTypeStr[statInfo.stats[i]["attackType"]];
            }

            if (statInfo.stats[i]["totalBytes"] != null) {
                statInfo.stats[i]["totalBytes"] = BytesUnitTransForm(statInfo.stats[i]["totalBytes"]);
            }

            if (statInfo.stats[i]["peak"] != null) {
                statInfo.stats[i]["peak"] = BytesUnitTransForm(statInfo.stats[i]["peak"]) + "/s";
            }
        }

        $scope.tableJson = statInfo.stats;
        $scope.tableLength = statInfo.limit;
        $scope.tableloading = false;

        console.log(statInfo.total);
        console.log($scope.page);
        pageUpdate(statInfo.total, $scope.page);*/
    }

    $scope.getAttackViewError = function(response, status) {
        console.log("getAttackViewError "+response);
        console.log(status);
    }

    $scope.attackViewInfo = function(){
        var requestUrl = getUrl($location, 'rest/attackAnalyse?page=' + $scope.page);
        httpGet($http, requestUrl, null, $scope.getAttackViewSuccess, $scope.getAttackViewError);

        var timer = $timeout(function(){
            $scope.attackViewInfo();
        },10000);

        $scope.$on(
            "$destroy",
            function(event) {
                $timeout.cancel(timer);
            }
        );
    }

    $scope.getTableListSuccess = function(response){
        console.log(response);

        var res = response;

        for (var i = 0; i < res.stats.length; i++) {
            if (res.stats[i]["status"] != null) {
                if (res.stats[i]["status"] == 1) {
                    res.stats[i]['endTime'] = "----";
                }
                res.stats[i]["status"] = spCode[res.stats[i]["status"]];
            }

            if (res.stats[i]["attackType"] != null) {
                res.stats[i]["attackType"] = attackTypeStr[res.stats[i]["attackType"]];
            }

            if (res.stats[i]["totalBytes"] != null) {
                res.stats[i]["totalBytes"] = BytesUnitTransForm(res.stats[i]["totalBytes"]);
            }

            if (res.stats[i]["peak"] != null) {
                res.stats[i]["peak"] = BytesUnitTransForm(res.stats[i]["peak"])+"/s";
            }
        }
        $scope.tableJson = res.stats;
        $scope.tableLength = res.limit;
        $scope.tableloading = false;

        pageUpdate(res.total, $scope.page);
    }

    $scope.getTableListError = function(response, status) {
        console.log("getTableListError "+response);
        console.log(status);
    }

    $scope.getTablerList = function(page){
        console.log("into getTablerList "+page);

        var requestUrl = getUrl($location, '/rest/attackAnalyse/attackDetailTable' + '?page=' + page);
        console.log(requestUrl);
        httpGet($http, requestUrl, null, $scope.getTableListSuccess, $scope.getTableListError);

        var timer = $timeout(function(){
            $scope.getTablerList($scope.page);
        },5000);

        $scope.$on(
            "$destroy",
            function(event) {
                $timeout.cancel(timer);
            }
        );

    }

    $scope.pageIndexGetTablerList = function(page){
        console.log("call pageIndexGetTablerList，current page："+page);

        var requestUrl = getUrl($location, '/rest/attackAnalyse/attackDetailTable' + '?page=' + page);
        console.log(requestUrl);
        httpGet($http, requestUrl, null, $scope.getTableListSuccess, $scope.getTableListError);
    }

    $scope.pageGetTablerList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.pageIndexGetTablerList($scope.page);
    }


    $scope.$on('changeLangEvent', function(){
        //document.location.reload();
        $state.go($state.current, {}, {reload:true});
    });

    $scope.page = 1;
    $scope.attackViewInfo();
    pageInit($scope.pageGetTablerList);
    $scope.getTablerList($scope.page);
}]);