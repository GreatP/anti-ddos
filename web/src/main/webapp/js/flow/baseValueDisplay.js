/**
 * Created by lb on 2015/8/24.
 */


app.controller('baseValueDisplayController', ['$scope', '$http','$localStorage', '$location', '$translate', '$state', '$timeout', '$animate', 'suffix', function ($scope, $http,$alerts, $location, $translate, $state, $timeout, $animate, $suffix) {
    'use strict';
    var baseValueOptionApi;
    //var KPS = '(KB/s)';
    var KPS = '(bps)';
    $scope.baseValueOption = {
        version: 1,
        /*title: {
            text: "流量基线图"
        },*/
        //气泡提示框
        tooltip: {
            trigger: 'axis'
        },

        //数据区域缩放
        dataZoom:{
            orient:"horizontal", //水平显示
            show:true, //显示滚动条
            start:0, //起始值为20%
            end:100,  //结束值为50%

            backgroundColor: 'rgba(221,160,221,0.5)',
            dataBackgroundColor: 'rgba(138,43,226,0.5)',
            fillerColor: 'rgba(38,143,26,0.6)',
            handleColor: 'rgba(128,43,16,0.8)'
        },

        xAxis: [
            {
                type: 'category',
                name : $translate.instant('flowDisplay.TIME'),
                boundaryGap:false,
                axisLabel:{//刻度间隔
                    //interval:23,
                    textStyle:{//刻度显示控制
                        color:"black", //刻度颜色
                        fontSize:12  //刻度大小
                    }
                },
                data : [0]
            }
        ],
        yAxis: [
            {
                type: 'value',
                name : $translate.instant('flowDisplay.SPEED') + KPS
            }

        ],
        series: [
            {
                "name":$translate.instant('flowDisplay.BASELINE_SPEED') + KPS,
                "type":"bar",
                data:[0]
            }
        ],
        onRegisterApi: function (chartApi) {
            baseValueOptionApi = chartApi;
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
        console.log("into basevalue getDataError");
        console.error("request status: [" + status + "],cased by:" + err);
        console.log(XMLHttpRequest);
        //bootbox.alert("不好意思，大爷，请求数据失败啦!");
    }

    $scope.getDataSuccess = function(result, status) {
        console.log("into basevalue getDataSuccess");
        if (result == null || result.length <= 0) {
            console.log("result == null || result.length <= 0");
            bootbox.alert($translate.instant('flowDisplay.BASELINE_LEARNING'));
            return;
        }

        var weekShow=[$translate.instant('flowDisplay.SUN'),$translate.instant('flowDisplay.MON'),$translate.instant('flowDisplay.TUE'),
            $translate.instant('flowDisplay.WED'),$translate.instant('flowDisplay.THURS'),$translate.instant('flowDisplay.FRI'),$translate.instant('flowDisplay.SAT')];

        $scope.baseValueOption.version++;//必须有，否则图表更加新数据而不能变化

        var i;
        var j;
        var x;

        var KB=1024;
        var rate;
        $scope.baseValueOption.xAxis[0].data=[0];
        $scope.baseValueOption.series[0].data=[0];
        for (x in result) {
            rate = result[x].bps/KB;
            rate = result[x].bps*8;//仅用于测试，正式版本去掉
            $scope.baseValueOption.series[0].data[x] = rate.toFixed(2);
            i = result[x].week;
            j = result[x].hour;
            if (result.length <= HOUR_OF_DAY) {//len <= 24表示一天的基线值
                $scope.baseValueOption.xAxis[0].data[x] = j+$translate.instant('flowDisplay.HOUR');
            } else {//一周的基线值
                $scope.baseValueOption.xAxis[0].data[x] = weekShow[i]+","+j+$translate.instant('flowDisplay.HOUR');
            }
        }

        //var HOUR_OF_DAY=24;
        //console.log("result.length="+result.length);
        //if (result.length <= HOUR_OF_DAY) {
        //    $scope.baseValueOption.xAxis[0].axisLabel.interval = 2;
        //    $scope.baseValueOption.dataZoom.start = 0;
        //    $scope.baseValueOption.dataZoom.end = 100;
        //    $scope.baseValueOption.dataZoom.show = true;
        //} else {
        //    $scope.baseValueOption.dataZoom.show = true;
        //    $scope.baseValueOption.dataZoom.start = 0;
        //    $scope.baseValueOption.dataZoom.end = 50;
        //   $scope.baseValueOption.xAxis[0].axisLabel.interval = 23;
        //}

        $scope.$apply();//必须有，否则会有延迟
    }

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
        console.log("poname:"+poObject.name+" poID : "+poObject.id);


        var  myselect=document.getElementById("selectProtocal");//从html中获取selectProtocal标签
        var  index=myselect.selectedIndex ;//拿到选中项的索引
        var protocal = myselect.options[index].value;
        console.log("protocal:"+myselect.options[index].value);
        if(protocal == -1) {
            bootbox.alert($translate.instant('flowDisplay.SELECT_PROTOCOL'));
            return;
        }

        //myselect = document.getElementById("selectTime");
        //index=myselect.selectedIndex ;
        //var week = myselect.options[index].value;
        //console.log("week:"+myselect.options[index].value);

        /*console.log("local url:"+window.location.href);

        var baseUrl = "http://" +window.location.host;
        //var baseUrl = window.location.href;
        console.log("baseUrl : "+baseUrl);
        //var url = baseUrl+"/getdata";
        //var url = baseUrl+"/baseValueDisplay"+"/getdata";
        var url = baseUrl+"/rest/baseValueDisplay"+"/getdata";*/

        var url = getUrl($location, "/rest/baseValueDisplay"+"/getdata");
        console.log("url : "+url);

        var urlPara = {poID:poObject.id,protocol:protocal,week:10};
        ajaxGetdataxx(url,urlPara,$scope.getDataSuccess,$scope.getDataError,"flightHandler");
    }

    $scope.$on('changeLangEvent', function(){
        //document.location.reload();
        $state.go($state.current, {}, {reload:true});
    });


    $scope.getPo();
    $scope.po = $scope.POData[0];
}
]);






