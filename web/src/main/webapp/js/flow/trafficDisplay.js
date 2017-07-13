/**
 * Created by cw on 2016/5/12.
 */

var $suffix = 'trafficDisplay';
app.controller('trafficDisplayController', ['$scope', '$http', '$location', '$timeout', 'suffix', '$translate', '$state',function($scope, $http, $location, $timeout,  $suffix, $translate, $state) {
    'use strict';
    var hourOptionApi;
    //var KPS = '(KB/s)';
    var KPS = '(B/s)';
    
    $scope.legend = [$translate.instant("TCP"),
                     $translate.instant("UDP"),
                     $translate.instant("ICMP"),
                     $translate.instant("OTHER")];

     $scope.protocalPieOption = pipeChart();

     var cf = 1;
     $scope.protocalPieOption.series[0].data = [];
     $scope.protocalPieOption.legend.data = [];

     $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[0]});

     $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[1]});

     $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[2]});

     $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[3]});

     $scope.protocalPieOption.version++;
    
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
    
    $scope.showtime=[{"name":"最近一个小时流量","value":1},{"name":"最近一天流量","value":2},{"name":"最近一个星期流量","value":3},{"name":"最近一个月流量","value":4},{"name":"最近一年流量","value":5}];

    //$scope.showtitle = "流量展示图";
    
    var tcpRate = 0;
    var udpRate = 0;
    var icmpRate = 0;
    var otherRate = 0;
    var totalRate = 0;
        
    $scope.protoData=[];
    //$scope.protoData=[{"proto":"TOTAL","value":totalRate, "prent":"-"},{"proto":"TCP","value":tcpRate,"prent":"-"},{"proto":"UDP","value":udpRate,"prent":"-"},{"proto":"ICMP","value":totalRate,"prent":"-"}];
    
    /*
    $scope.protocal = [];
    $scope.setProtocal = function (po) {
        console.log("into setProtocal");
        $scope.protocal = [];
        console.log("po is "+po);
        var poObject = eval("("+po+")");
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
        var abc = $scope.protocal;
    }*/

    $scope.getDataError = function (XMLHttpRequest, status, err) {
        console.log("into getDataError");
        console.error("request status: [" + status + "],cased by:" + err);
        console.log(XMLHttpRequest);
    }

    $scope.showtype = 1;
    $scope.dataLength = 0;
    $scope.getDataSuccess = function(result, status) {
        console.log("into getDataSuccess");
        //$scope.dataLength = result.length;
        if (result == null) {
            console.log("result == null");
            //bootbox.alert("不好意思，没有可显示流量数据！");
            $scope.tableShow=null;
            $scope.hourOption.xAxis[0].data=[0];
            $scope.hourOption.series[0].data=[0];
	        $scope.hourOption.version++;
	    
            $scope.protoData=[];
            $scope.protocalPieOption.series[0].data = [];
            $scope.protocalPieOption.legend.data=[];
            $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[0]});
            $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[1]});
            $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[2]});
            $scope.protocalPieOption.series[0].data.push({value:0,name:$scope.legend[3]});
            //$scope.protocalPieOption.legend.data[0] = $scope.legend[0];
            //$scope.protocalPieOption.legend.data[1] = $scope.legend[1];
            //$scope.protocalPieOption.legend.data[2] = $scope.legend[2];
            //$scope.protocalPieOption.legend.data[3] = $scope.legend[3];
            $scope.protocalPieOption.version++;
            $scope.$apply();
            return;
        }
        $scope.tableShow = result.trafficList;
        $scope.dataLength = $scope.tableShow.length;
        var lineShow = result.trafficAll;
        console.log($scope.lineShow);
        console.log(status);

        //将返回的category和series对象赋值给options对象内的category和series
        //因为xAxis是一个数组 这里需要是xAxis[i]的形式
        var now;
        var type;
        var hour;
        var day;
        var year;
        var mon;
        var minute;
        var second;
        var x;

        var KB=1024;
        var rate;
        //$scope.dayOption.xAxis[0].data=[0];
        //$scope.dayOption.series[0].data=[0];
        
        tcpRate = 0;
        udpRate = 0;
        icmpRate = 0;
        otherRate = 0;
        totalRate = 0;
        
        type = $scope.showtype;
        for (x in lineShow) {

            now = new Date(lineShow[x].time);
            year = now.getFullYear();
            mon = now.getMonth()+1;//1-12月
            day = now.getDate();
            hour = now.getHours();//0-23
            minute = now.getMinutes();//0-59
            second = now.getSeconds();
          
            tcpRate+=lineShow[x].bps_tcp;
            udpRate+=lineShow[x].bps_udp;
            icmpRate+=lineShow[x].bps_icmp;
            otherRate+=lineShow[x].bps_other;
            totalRate+=lineShow[x].bps_all;

            switch (type) {
                case "1":
                    //options.xAxis[0].data[x-1] = now.toLocaleTimeString();
                	//$scope.showtitle = "一小时流量图";
                    $scope.hourOption.xAxis[0].data[x] = hour+":"+minute+":"+second;
                    rate = lineShow[x].bps_all;//用于测试
                    $scope.hourOption.series[0].data[x] = rate.toFixed(2);
                    break;
                case "2":
                	//$scope.showtitle = "一天流量图";
                    $scope.hourOption.xAxis[0].data[x] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                    rate = lineShow[x].bps_all;//用于测试
                    $scope.hourOption.series[0].data[x] = rate.toFixed(2);
                    //$scope.dayOption.version++;
                    break;
                case "3":
                	//$scope.showtitle = "一周流量图";
                    $scope.hourOption.xAxis[0].data[x] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                    rate = lineShow[x].bps_all;//用于测试
                    $scope.hourOption.series[0].data[x] = rate.toFixed(2);
                    break;
                case "4":
                	//$scope.showtitle = "一个月流量图";
                    //options.xAxis[0].data[x-1] = now.toLocaleDateString();
                    $scope.hourOption.xAxis[0].data[x] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                    rate = lineShow[x].bps_all;//用于测试
                    $scope.hourOption.series[0].data[x] = rate.toFixed(2);
                    break;
                case "5":
                	//$scope.showtitle = "一年流量图";
                    //options.xAxis[0].data[x-1] = now.toLocaleDateString();
                    $scope.hourOption.xAxis[0].data[x] = year+"/"+mon+"/"+day+","+hour+":"+minute;
                    rate = lineShow[x].bps_all;//用于测试
                    $scope.hourOption.series[0].data[x] = rate.toFixed(2);
                    break;
                default :
                    bootbox.alert("错误的displayType："+displayType);
                    return;
            }
            
        }
        
        if (x != 0)
        {   
        	x++;
        	tcpRate /= x;
            udpRate /= x;
            icmpRate /= x;
            otherRate /= x;
            totalRate /= x;
            
            if (totalRate != 0)
            {
            
            var tcpPer = tcpRate / totalRate * 100;
            var udpPer = udpRate / totalRate * 100;
            var icmpPer = icmpRate / totalRate * 100;
            var totalPer = 100;
            var otherPer = totalPer - tcpPer - udpPer - icmpPer;
            
            $scope.protoData=[{"proto":"TOTAL","value":totalRate.toFixed(0),"prent": totalPer.toFixed(2) + "%"},
                {"proto":"TCP","value":tcpRate.toFixed(0),"prent":tcpPer.toFixed(2)+"%"},
                {"proto":"UDP","value":udpRate.toFixed(0),"prent":udpPer.toFixed(2)+"%"},
                {"proto":"ICMP","value":icmpRate.toFixed(0),"prent":icmpPer.toFixed(2)+"%"},
                {"proto":"OTHER","value":otherRate.toFixed(0),"prent":otherPer.toFixed(2)+"%"}];
        
            $scope.protocalPieOption.series[0].data = [];
            $scope.protocalPieOption.legend.data=[];
            $scope.protocalPieOption.series[0].data.push({value:tcpRate.toFixed(0),name:$scope.legend[0]});
            $scope.protocalPieOption.series[0].data.push({value:udpRate.toFixed(0),name:$scope.legend[1]});
            $scope.protocalPieOption.series[0].data.push({value:icmpRate.toFixed(0),name:$scope.legend[2]});
            $scope.protocalPieOption.series[0].data.push({value:otherRate.toFixed(0),name:$scope.legend[3]});

            $scope.protocalPieOption.legend.data[0] = $scope.legend[0];
            $scope.protocalPieOption.legend.data[1] = $scope.legend[1];
            $scope.protocalPieOption.legend.data[2] = $scope.legend[2];
            $scope.protocalPieOption.legend.data[3] = $scope.legend[3];

            $scope.protocalPieOption.version++;
           }
           else
           {
               $scope.protoData=[{"proto":"TOTAL","value":totalRate.toFixed(0),"prent":"-"},
                   {"proto":"TCP","value":tcpRate.toFixed(0),"prent":"-"},
                   {"proto":"UDP","value":udpRate.toFixed(0),"prent":"-"},
                   {"proto":"ICMP","value":icmpRate.toFixed(0),"prent":"-"},
                   {"proto":"OTHER","value":otherRate.toFixed(0),"prent":"-"}];
           }
            
        
        }
        else
        {
        	tcpRate = 0;
            udpRate = 0;
            icmpRate = 0;
            otherRate = 0;
            totalRate = 0;
            
            $scope.protoData=[{"proto":"TOTAL","value":totalRate.toFixed(0),"prent":"-"},{"proto":"TCP","value":tcpRate.toFixed(0),"prent":"-"},{"proto":"UDP","value":udpRate.toFixed(0),"prent":"-"},{"proto":"ICMP","value":totalRate.toFixed(0),"prent":"-"}];
        }        
       
        
        //$scope.protoData=[{"proto":"TOTAL","value":totalRate.toFixed(0),"prent":"-"},{"proto":"TCP","value":tcpRate.toFixed(0),"prent":"-"},{"proto":"UDP","value":udpRate.toFixed(0),"prent":"-"},{"proto":"ICMP","value":totalRate.toFixed(0),"prent":"-"}];
        
        $scope.hourOption.version++;
        //option version必须进行自加，否则不能根据最新的数据进行显示图
        var len = result.length;
        console.log("result.length="+result.length);
        
        /*
        switch (type) {
            case "1":
                $scope.hourOption.version++;
                break;
            case "2":
                $scope.dayOption.version++;
                break;
            case "3":
                $scope.weekOption.version++;
                break;
            case "4":
                $scope.monthOption.version++;
                break;
            case "5":
                $scope.yearOption.version++;
                break;
            default :
                bootbox.alert("错误的displayType："+displayType);
                break;
        }*/
        $scope.$apply();
    }

    $scope.flag=-1;
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
        
        
        var  myselect=document.getElementById("selectTime");//从html中获取selectTime标签
        var  index=myselect.selectedIndex ;//拿到选中项的索引
        var timetype = myselect.options[index].value;
        console.log("timetype:"+myselect.options[index].value);
        if(timetype == -1) {
            bootbox.alert($translate.instant('flowDisplay.SELECT_TIME'));
            return;
        }
        
        $scope.showtype = timetype;

        /*console.log("local url:"+window.location.href);
        var baseUrl = "http://" +window.location.host;
        //var baseUrl = window.location.href;
        console.log("baseUrl : "+baseUrl);

        //var url = baseUrl+"/flowDisplay"+"/getdata";
        var url = baseUrl+"/rest/flowDisplay"+"/getdata";*/

        var url = getUrl($location, "/rest/trafficDisplay"+"/getdata");
        console.log("flowDisplay url : "+url);

        	
        var urlPara = {displayType:timetype,poID:poObject.id};
        ajaxGetdataxx(url,urlPara,$scope.getDataSuccess,$scope.getDataError, "flightHandler"+timetype);

        /*
        if ($scope.flag == -1) {
            $scope.updateHourOption();
            console.log("start timeout update hour option");
        }*/
        $scope.selected_poid=poObject.id;
        $scope.flag=1;
        $scope.ajaxGetdataUrl=url;
    }

    $scope.updateHourOption = function(){
       var timer = $timeout(function(){
            console.log("timeout update hour option,poid is "+ $scope.selected_poid);
            var urlPara = {displayType:1,poID:$scope.selected_poid};
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