/**
 * Created by lb on 2015/10/8.
 */
'use strict';
app.controller('overViewController', ['$scope', '$http','$localStorage', '$location', '$timeout', '$animate', '$translate', '$state', function ($scope, $http,$alerts, $location, $timeout, $animate, $translate, $state) {
//app.controller('overViewController', ['$scope', '$http','$localStorage', '$timeout', '$animate','$stateParams', function ($scope, $http,$alerts, $timeout, $animate,$stateParams) {

    $scope.normalNumber = 0;
    $scope.attackNumber = 0;
    $scope.exceptionNumber = 0;
    $scope.defenseNumber = 0;
    $scope.recoverNumber = 0;

    $scope.protocolStr = {0:"OTHER", 1:"ICMP", 6:"TCP", 17:"UDP"};
    $scope.eventType = {EVENT_ATTACKED:$translate.instant('overView.ATTACK_EVENT_ATTACKED'), EVENT_START_DEFENSE:$translate.instant('overView.ATTACK_EVENT_START_DEFENSE'), EVENT_STOP_DEFENSE:$translate.instant('overView.ATTACK_EVENT_STOP_DEFENSE')};
    $scope.defenseStart = {LIMIT_RATE:$translate.instant('overView.START_LIMIT'), GUIDE_FLOW:$translate.instant('overView.START_GUIDE')};
    $scope.defenseStop = {LIMIT_RATE:$translate.instant('overView.STOP_LIMIT'), GUIDE_FLOW:$translate.instant('overView.STOP_GUIDE')};

    $scope.numberOption = {
        version : 0,
        legend: {
            data: [$translate.instant('overView.NORMAL_TIME'),$translate.instant('overView.ABNORMAL_TIME'),$translate.instant('overView.ATTACK_TIME')],
            y: 'bottom'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c} 次 ({d}%)"
        },
        toolbox: {
            show : false
        },
        calculable : true,
        series : [
            {
                //name:'检测类型',
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
                data:[/*{value: 0, name:"速率正常次数"},{value: 0, name:"速率异常次数"},{value: 0, name:"攻击次数"}*/]
            }
        ]
    };


    $scope.protocalPieOption = {
        version : 0,
        legend: {
            y: 'bottom',
            data: ['TCP','UDP','ICMP','OTHER']
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c} 次  ({d}%)"
        },
        toolbox: {
            show : false
        },
        calculable : true,
        series : [
            {
                //name:'通话类型',
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


    $scope.getOverViewSuccess = function(response) {
        console.log(response);

        //框图
        var totalInfo = response[0];
        $scope.normalNumber = totalInfo['normalSpeedCount'];
        $scope.attackNumber = totalInfo['attackCount'];
        $scope.exceptionNumber = totalInfo['exceptionSpeedCount'];
        $scope.defenseNumber = totalInfo['defenseCount'];
        $scope.recoverNumber = totalInfo['recoverCount'];

        $scope.numberOption.series[0].data = [];
        $scope.numberOption.series[0].data.push({value:totalInfo['normalSpeedCount'],name:$translate.instant('overView.NORMAL_TIME')});
        $scope.numberOption.series[0].data.push({value:totalInfo['exceptionSpeedCount'],name:$translate.instant('overView.ABNORMAL_TIME')});
        $scope.numberOption.series[0].data.push({value:totalInfo['attackCount'],name:$translate.instant('overView.ATTACK_TIME')});
        $scope.numberOption.version++;

        //饼图
        var protocolInfo = response[1];
        var proname;

        $scope.protocalPieOption.series[0].data = [];
        for (var i=0;i<protocolInfo.length;i++) {

            switch (protocolInfo[i].protocol) {
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


            $scope.protocalPieOption.series[0].data.push({value:protocolInfo[i].attackCount,name:proname});
            console.log(protocolInfo[i].attackCount+proname);
        }
        $scope.protocalPieOption.version++;

        //攻击详细列表
        var statInfo = response[2];
        $scope.tableJson = statInfo.stats;
        $scope.tableLength = statInfo.limit;
        $scope.tableloading = false;

        console.log(statInfo.total);
        console.log($scope.page);
        pageUpdate(statInfo.total, $scope.page);

        //攻击防御事件
        var eventInfo = response[3];
        console.log(eventInfo);
        $scope.eventTableJson = eventInfo;
        $scope.eventTableLength = eventInfo.length;
        $scope.eventTableLoading = false;
    }

    $scope.getOverViewError = function(response, status) {
        console.log("getOverViewError "+response);
        console.log(status);
    }

    $scope.overViewInfo = function(){
        console.log("into overViewInfo");
        var requestUrl = getUrl($location, 'rest/overView?page=' + $scope.page);
        httpGet($http, requestUrl, null, $scope.getOverViewSuccess, $scope.getOverViewError);

        var timer = $timeout(function(){
            $scope.overViewInfo();
        },5000);

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

        var requestUrl = getUrl($location, '/rest/overView/attackTable' + '?page=' + page);
        console.log(requestUrl);
        httpGet($http, requestUrl, null, $scope.getTableListSuccess, $scope.getTableListError);


        var timer = $timeout(function(){
            $scope.getTablerList(1);
        },5000);

        $scope.$on(
            "$destroy",
            function(event) {
                $timeout.cancel(timer);
            }
        );
    }

    $scope.pageGetTablerList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getTablerList($scope.page);
    }
    
    $scope.$on('changeLangEvent', function(){
    	 //document.location.reload();
    	$state.go($state.current, {}, {reload:true});
    });

    $scope.page = 1;
    pageInit($scope.pageGetTablerList);

    $scope.overViewInfo();

}]);
