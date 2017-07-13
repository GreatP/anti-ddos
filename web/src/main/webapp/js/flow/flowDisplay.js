/**
 * Created by lb on 2015/8/20.
 */
//'use strict';
//var $localhost="http://172.16.12.208:9200";
//var $postfix="/_all/_search?pretty";
//var $requestUrl=$localhost+$postfix;

//var $localhost="http://10.111.121.15:8080";
//var $date="2015.*";

//var $requestUrl=$localhost+"/sys/admin?callback=JSON_CALLBACK";
var $suffix = 'flowDisplay';
app.controller('flowDisplayController', ['$scope', '$http', '$location', '$timeout', 'suffix', '$translate', '$state',function($scope, $http, $location, $timeout,  $suffix, $translate, $state) {
    'use strict';
    var hourOptionApi, dayOptionApi, weekOptionApi, monthApi,yearApi;
    //var KPS = '(KB/s)';
    var KPS = '(bps)';
    $scope.hourOption = {
        version: 1,
        /*title: {
            text: "一小时流量图"
        },*/
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data: ['cetc']
         },*/
        xAxis: [
            {
                type: 'category',
                name : $translate.instant('flowDisplay.TIME'),
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:5,
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data: [0]
            }
        ],
        //grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name : $translate.instant('flowDisplay.SPEED') + KPS
            }
        ],
        series: [
            {
                "name":$translate.instant('flowDisplay.SPEED') + KPS,
                type:'line',
                smooth:true,
                data:[0]
            }
        ],
        onRegisterApi: function (chartApi) {
            hourOptionApi = chartApi;
        }
    };
    $scope.dayOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data: ['cetc']
         },*/
        xAxis: [
            {
                type: 'category',
                name : $translate.instant('flowDisplay.TIME'),
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:5,
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data: [0]
            }
        ],
        //grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name : $translate.instant('flowDisplay.SPEED') + KPS
            }
        ],
        series: [
            {
                "name": $translate.instant('flowDisplay.SPEED') + KPS,
                //"type":"bar",
                type:'line',
                smooth:true,
                itemStyle: {
                    normal: {
                        color: '#da70d6'
                    }
                },
                data:[0]
            }
        ],
        onRegisterApi: function (chartApi) {
            dayOptionApi = chartApi;
        }
    };
    $scope.weekOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data: ['cetc']
         },*/
        xAxis: [
            {
                type: 'category',
                name : $translate.instant('flowDisplay.TIME'),
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:5,
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data: [0]
            }
        ],
        //grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name : $translate.instant('flowDisplay.SPEED') + KPS
            }
        ],
        series: [
            {
                "name":$translate.instant('flowDisplay.SPEED') + KPS,
                //"type":"bar",
                type:'line',
                smooth:true,
                itemStyle: {
                    normal: {
                        color: 'yellowgreen'
                    }
                },
                data:[0]
            }
        ],
        onRegisterApi: function (chartApi) {
            weekOptionApi = chartApi;
        }
    };
    $scope.monthOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data: ['cetc']
         },*/
        xAxis: [
            {
                type: 'category',
                name : $translate.instant('flowDisplay.TIME'),
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:5,
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data: [0]
            }
        ],
        grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name : $translate.instant('flowDisplay.SPEED') + KPS
            }
        ],
        series: [
            {
                "name":$translate.instant('flowDisplay.SPEED') + KPS,
                //"type":"bar",
                type:'line',
                smooth:true,
                itemStyle: {
                    normal: {
                        color: '#87cefa'
                    }
                },
                data:[0]
            }
        ],
        onRegisterApi: function (chartApi) {
            monthApi = chartApi;
        }
    };

    $scope.yearOption = {
        version: 1,
        tooltip: {
            trigger: 'axis'
        },
        /*legend: {
         data: ['cetc']
         },*/
        xAxis: [
            {
                type: 'category',
                name : $translate.instant('flowDisplay.TIME'),
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:5,//赋值为'auto'不行，显示会出现重合
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data: [0]
            }
        ],
        grid:[10,10,20,20],
        yAxis: [
            {
                type: 'value',
                name :$translate.instant('flowDisplay.SPEED') + KPS
            }
        ],
        series: [
            {
                "name":$translate.instant('flowDisplay.SPEED') + KPS,
                //"type":"bar",
                type:'line',
                smooth:true,
                itemStyle: {
                    normal: {
                        color: '#00fa9a'
                    }
                },
                data:[0]
            }
        ],
        onRegisterApi: function (chartApi) {
            yearApi = chartApi;
        }
    };

    $scope.getPoSuccess = function(response) {
        var $resultJson = eval(response);
        console.log("into baseValueDisplayController success ");
        console.log($resultJson);
        $scope.POData = $resultJson;
    }

    $scope.getPoError = function (response, status, headers, config, statusText) {
        console.log("into baseValueDisplayController error");
        //request status: [parsererror],cased by:Error: flightHandler was not called
        console.error("request status: [" + status + "],\n cased by:" + statusText);
        //console.log(XMLHttpRequest);
    }

    $scope.po={};
    $scope.getPo = function() {
        console.log("into baseValueDisplayController");
        $scope.POData = {};//html中可以直接使用POData，即控制器$scope的属性可直接被视图所使用，视图通过{{ }}方式引用

        var requestUrl = getUrl($location, $suffix.policy  + '/getAllPo');
        httpGet($http, requestUrl, null, $scope.getPoSuccess, $scope.getPoError);
    }

    $scope.protocal = [];
    $scope.setProtocal = function (po) {
        console.log("into setProtocal");
        $scope.protocal = [];
        console.log("po is "+po);
        var poObject = eval("("+po+")");/*(new Function("","return "+po))();*/
        //var poObject = po;
        if (poObject.tcp > 0) {
            $scope.protocal.push({name:"TCP",value:6});
        }
        if (poObject.udp > 0) {
            $scope.protocal.push({name:"UDP",value:17});
        }
        if (poObject.icmp > 0) {
            $scope.protocal.push({name:"ICMP",value:1});
        }

        if (poObject.any > 0) {
            $scope.protocal.push({name:"other",value:0});
        }
    }

    $scope.getDataError = function (XMLHttpRequest, status, err) {
        console.log("into getDataError");
        console.error("request status: [" + status + "],cased by:" + err);
        console.log(XMLHttpRequest);
        //alert("不好意思，大爷，请求数据失败啦!");
    }

    $scope.getDataSuccess = function(result, status) {
        console.log("into getDataSuccess");
        if (result == null || result.length <= 0) {
            console.log("result == null || result.length <= 0");
            //bootbox.alert("不好意思，没有可显示流量数据！");
            return;
        }
        console.log(result);
        console.log(status);

        //将返回的category和series对象赋值给options对象内的category和series
        //因为xAxis是一个数组 这里需要是xAxis[i]的形式
        var now;
        var displayType="";
        var hour;
        var day;
        var year;
        var mon;
        var minute;
        var second;
        var x;

        var KB=1024;
        var rate;
        $scope.dayOption.xAxis[0].data=[0];
        $scope.dayOption.series[0].data=[0];
        for (x in result) {
            if (x == 0) {
                displayType = result[0].typePara;
                console.log("haha"+displayType);
                console.log("hh"+result[0]);

            } else {
                now = new Date(result[x].timestamp);
                year = now.getFullYear();
                mon = now.getMonth()+1;//1-12月
                day = now.getDate();
                hour = now.getHours();//0-23
                minute = now.getMinutes();//0-59
                second = now.getSeconds();

                switch (displayType) {
                    case "hour":
                        //options.xAxis[0].data[x-1] = now.toLocaleTimeString();
                        $scope.hourOption.xAxis[0].data[x-1] = hour+":"+minute+":"+second;
                        rate = result[x].byte/KB;
                        rate = result[x].byte*8;//用于测试
                        $scope.hourOption.series[0].data[x-1] = rate.toFixed(2);
                        //$scope.hourOption.version++;
                        break;
                    case "day":
                        $scope.dayOption.xAxis[0].data[x-1] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                        rate = result[x].byte/KB;
                        rate = result[x].byte*8;//用于测试
                        $scope.dayOption.series[0].data[x-1] = rate.toFixed(2);
                        //$scope.dayOption.version++;
                        break;
                    case "week":
                        $scope.weekOption.xAxis[0].data[x-1] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                        rate = result[x].byte/KB;
                        rate = result[x].byte*8;//用于测试
                        $scope.weekOption.series[0].data[x-1] = rate.toFixed(2);
                        break;
                    case "month":
                        //options.xAxis[0].data[x-1] = now.toLocaleDateString();
                        $scope.monthOption.xAxis[0].data[x-1] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                        rate = result[x].byte/KB;
                        rate = result[x].byte*8;//用于测试
                        $scope.monthOption.series[0].data[x-1] = rate.toFixed(2);
                        break;
                    case "year":
                        //options.xAxis[0].data[x-1] = now.toLocaleDateString();
                        $scope.yearOption.xAxis[0].data[x-1] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                        rate = result[x].byte/KB;
                        rate = result[x].byte*8;//用于测试
                        $scope.yearOption.series[0].data[x-1] = rate.toFixed(2);
                        break;
                    default :
                        bootbox.alert("错误的displayType："+displayType);
                        return;
                }
            }
        }

        //option version必须进行自加，否则不能根据最新的数据进行显示图
        var len = result.length;
        console.log("result.length="+result.length);
        switch (displayType) {
            case "hour":
                $scope.hourOption.version++;
                /*if (len < 20) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 1;
                } else if (len < 40) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 2;
                } else if (len < 60) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 3;
                } else if (len < 100) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 4;
                } else if (len < 200) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 10;
                } else if (len < 400) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 20;
                } else if (len < 800) {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 40;
                } else {
                    $scope.hourOption.xAxis[0].axisLabel.interval = 50;
                }*/
                //$scope.hourOption.xAxis[0].axisLabel.interval = 'auto';
                break;
            case "day":
                $scope.dayOption.version++;
                break;
            case "week":
                $scope.weekOption.version++;
                break;
            case "month":
                $scope.monthOption.version++;
                break;
            case "year":
                $scope.yearOption.version++;
                break;
            default :
                bootbox.alert("错误的displayType："+displayType);
                break;
        }
        $scope.$apply();
    }

    $scope.selected_protocal=-1;
    $scope.selected_poid;
    $scope.ajaxGetdataUrl="";
    $scope.getDatas = function() {
        console.log("into getDatas.........");

        myselect = document.getElementById("selectPo");
        index=myselect.selectedIndex ;
        var po = myselect.options[index].value;
        if(!po) {
            bootbox.alert($translate.instant('flowDisplay.SELECT_PO'));
            return;
        }
        var poObject = eval("("+po+")");
        console.log("poname:"+poObject.name);


        var  myselect=document.getElementById("selectProtocal");//从html中获取selectProtocal标签
        var  index=myselect.selectedIndex ;//拿到选中项的索引
        var protocal = myselect.options[index].value;
        console.log("protocal:"+myselect.options[index].value);
        if(protocal == -1) {
            bootbox.alert($translate.instant('flowDisplay.SELECT_PROTOCOL'));
            return;
        }

        /*console.log("local url:"+window.location.href);
        var baseUrl = "http://" +window.location.host;
        //var baseUrl = window.location.href;
        console.log("baseUrl : "+baseUrl);

        //var url = baseUrl+"/flowDisplay"+"/getdata";
        var url = baseUrl+"/rest/flowDisplay"+"/getdata";*/

        var url = getUrl($location, "/rest/flowDisplay"+"/getdata");
        console.log("flowDisplay url : "+url);

        for (var type=1;type<6;type++) {
            var urlPara = {displayType:type,poID:poObject.id,protocol:protocal};
            ajaxGetdataxx(url,urlPara,$scope.getDataSuccess,$scope.getDataError,"flightHandler"+type);
        }

        if ($scope.selected_protocal == -1) {
            $scope.updateHourOption();
            console.log("start timeout update hour option");
        }
        $scope.selected_poid=poObject.id;
        $scope.selected_protocal=protocal;
        $scope.ajaxGetdataUrl=url;
    }

    $scope.updateHourOption = function(){
       var timer = $timeout(function(){
            console.log("timeout update hour option,poid is "+ $scope.selected_poid + ",protocal "+$scope.selected_protocal);
            var urlPara = {displayType:1,poID:$scope.selected_poid,protocol:$scope.selected_protocal};
            ajaxGetdataxx($scope.ajaxGetdataUrl,urlPara,$scope.getDataSuccess,$scope.getDataError,"flightHandler10");
            $scope.updateHourOption();
        },5000);

        $scope.$on(
            "$destroy",
            function(event) {
                $timeout.cancel(timer);
            }
        );
    }

    $scope.$on('changeLangEvent', function(){
        //document.location.reload();
        $state.go($state.current, {}, {reload:true});
    });


    $scope.getPo();
    $scope.po = $scope.POData[0];

    //setTimeout(function(){$scope.haha();$scope.$apply();},100);
    //$scope.haha();
}]);