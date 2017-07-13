
function goControllerList(state) {
    state.go('app.controller');
}

function isExistSameController(http, location, id, ip, port, successCallback, errorCallback) {
    var requestUrl = getUrl(location, 'rest/policy/isExistController/' + id + '/' + ip + '/' + port);
    httpGet(http, requestUrl, null, successCallback, errorCallback);
}

function controllerList() {
    var controller = new Array();
    controller.push({'type':0, 'name':'OPENDAYLIGHT-HELIUM'});
    controller.push({'type':1, 'name':'OPENDAYLIGHT-LITHIUM'});
    //controller.push({'type':2, 'name':'UNINET'});
    controller.push({'type':3, 'name':'SSH+Open vSwitch'});

    return controller;
}

app.controller('conController', ['$scope', '$http','$translate', '$localStorage', '$location', '$state', '$timeout', '$animate', 'suffix', function ($scope, $http, $translate, $alerts, $location, $state, $timeout, $animate, $suffix) {
    $scope.getControllerListSuccess = function (response) {
        var $controllerJson = eval(response);
        $scope.controllerTableJson = $controllerJson.controllers;
        $scope.controllerTableLength = $scope.controllerTableJson.length;
        $scope.tableloading = false;

        pageUpdate($controllerJson.total, $scope.page);
    }

    $scope.getControllerListError = function (response, status) {
        $scope.tableloading = false;
        $scope.getErrShow = true;
    }

    $scope.getControllerList = function (page) {
        var requestUrl = getUrl($location, $suffix.controller + '?page=' + page);
        httpGet($http, requestUrl, null, $scope.getControllerListSuccess, $scope.getControllerListError);
    }

    $scope.pageGetControllerList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getControllerList($scope.page);
    }

    $scope.add = function(id) {
        $state.go('app.controllerAdd');
    }

    $scope.edit = function(id) {
        $state.go('app.controllerEdit', {id: id});
    }


    $scope.delControllerSuccess = function(response) {
        $scope.chkAll = false;
        $scope.listCheck.clearChecked();
        $scope.getControllerList($scope.page);
    }

    $scope.delControllerError = function() {
        //alert("bbbb");
    }


    $scope.del = function(id) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }

            var requestUrl = getUrl($location, $suffix.controller + '/' + id);
            httpDelete($http, requestUrl, null, $scope.delControllerSuccess, $scope.delControllerError);
        })

    }

    $scope.toggleCheckAll = function() {
        $scope.listCheck.toggleCheckAll($scope.chkAll, $scope.controllerTableJson);
    }

    $scope.toggleCheck = function(id) {
        $scope.listCheck.toggleCheck(id);
    }

    $scope.batchDel = function() {
        if ($scope.listCheck.checkedCount() === 0) {
            bootbox.alert($translate.instant('other.SELECT'));
            return;
        }

        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }

            var requestUrl = getUrl($location, $suffix.policy + '/batchDelController');
            httpPost($http, requestUrl, $scope.listCheck.getChecked(), $scope.delControllerSuccess, $scope.delControllerError);
        })
    }

    $scope.tableloading = true;

    $scope.controller = {0:"OPENDAYLIGHT-HELIUM", 1:"OPENDAYLIGHT-LITHIUM", 2:"UNINET", 3:"SSH+Open vSwitch"};
    $scope.visible = false;
    $scope.chkAll = false;
    $scope.listCheck = new ListCheck();

    $scope.page = 1;
    pageInit($scope.pageGetControllerList);
    $scope.getControllerList($scope.page);
}
]);



app.controller('conAddController', ['$scope', '$http', '$location', '$state', 'suffix', function ($scope, $http, $location, $state, $suffix) {
    $scope.success = function(response){
        goControllerList($state);
    }

    $scope.error = function() {
        //bootbox.alert("error");
        $scope.saveErrShow = true;
    }

    $scope.goBack = function() {
        goControllerList($state);
    }

    $scope.save = function() {
        if ($scope.warnAddrShow === undefined) {
            $scope.warnAddrShow = true;
        }

        if ($scope.warnPortNullShow === undefined) {
            $scope.warnPortNullShow = true;
        }

        if ($scope.warnInputportShow === undefined) {
            $scope.warnInputportShow = true;
        }

        if ($scope.warnOutputportShow === undefined) {
            $scope.warnOutputportShow = true;
        }

        if ($scope.warnUserShow === undefined) {
            $scope.warnUserShow = true;
        }

        if ($scope.warnPasswordShow === undefined) {
            $scope.warnPasswordShow = true;
        }

        if (($scope.warnAddrShow === true)
        || ($scope.warnPortNullShow === true)
        || ($scope.warnInputportShow === true)
        || ($scope.warnOutputportShow === true)
        || ($scope.warnPortRangeShow === true)
        || ($scope.warnUserShow === true)
        || ($scope.warnPasswordShow === true)
        || ($scope.warnIntervalNullShow === true)
        || ($scope.warnIntervalRangeShow === true)
        || ($scope.warnSuspicionsThresholdNullShow === true)
        || ($scope.warnSuspicionsThresholdRangeShow === true)
        || ($scope.warnDeviationPercentageNullShow === true)
        || ($scope.warnDeviationPercentageRangeShow === true)
        || ($scope.warnRecoverNormalThresholdNullShow === true)
        || ($scope.warnRecoverNormalThresholdRangeShow === true)) {
            return;
        }


        if ($scope.warnSameShow !== false) {
            $scope.isSave = true;
            return;
        }

        $scope._save();
    }

    $scope._save = function() {
        var requestUrl = getUrl($location, $suffix.controller);

        $scope.formData.type = 3;
        httpPost($http, requestUrl, {"ip":$scope.formData.ip, "port":$scope.formData.port,
                "inport":$scope.formData.inport, "outport":$scope.formData.outport, "user":$scope.formData.user,
                "password":$scope.formData.password, "type":$scope.formData.type,
                "detectionInterval":$scope.formData.interval,
                "detectionDeviationPercentage":$scope.formData.deviationPercentage,
                "attackSuspicionsThreshold":$scope.formData.suspicionsThreshold,
                "recoverNormalThreshold":$scope.formData.recoverNormalThreshold},
                $scope.success, $scope.error);
    }

    $scope.blurCheckIp = function() {
        $scope.warnAddrShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.formData.ip, "ip")) {
            $scope.warnAddrShow = true;
            return;
        }

        $scope.checkController($scope.formData.id, $scope.formData.ip, $scope.formData.port);
    }

    $scope.blurCheckPort = function() {
        $scope.warnPortNullShow = false;
        $scope.warnPortRangeShow = false;
        $scope.warnSameShow = false;

        if (!checkNullAndShowPrompt($scope.formData.port, "port")) {
            $scope.warnPortNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.port, "port", 1, 65535)) {
            $scope.warnPortRangeShow = true;
            return;
        }

        $scope.checkController($scope.formData.id, $scope.formData.ip, $scope.formData.port);
    }


    /****************************************************************************/
    //add input and output port check
    $scope.blurCheckInputport = function() {
        $scope.warnInputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.inport, "inport")) {
            $scope.warnInputportShow = true;
            return;
        }
    }

    $scope.blurCheckOutputport = function() {
        $scope.warnOutputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.outport, "outport")) {
            $scope.warnOutputportShow = true;
            return;
        }
    }

    /****************************************************************************/

    $scope.blurCheckUser = function() {
        $scope.warnUserShow = false;
        if (!checkNullAndShowPrompt($scope.formData.user, "user")) {
            $scope.warnUserShow = true;
            return;
        }
    }

    $scope.blurCheckPassword = function() {
        $scope.warnPasswordShow = false;
        if (!checkNullAndShowPrompt($scope.formData.password, "password")) {
            $scope.warnPasswordShow = true;
            return;
        }
    }

    $scope.blurCheckInterval = function() {
        $scope.warnIntervalNullShow = false;
        $scope.warnIntervalRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.interval, "interval")) {
            $scope.warnIntervalNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.interval, "interval", 5, 600)) {
            $scope.warnIntervalRangeShow = true;
            return;
        }
    }

    $scope.blurCheckSuspicionsThreshold = function() {
        $scope.warnSuspicionsThresholdNullShow = false;
        $scope.warnSuspicionsThresholdRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.suspicionsThreshold, "suspicionsThreshold")) {
            $scope.warnSuspicionsThresholdNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.suspicionsThreshold, "suspicionsThreshold", 0, 100)) {
            $scope.warnSuspicionsThresholdRangeShow = true;
            return;
        }
    }

    $scope.blurCheckDeviationPercentage = function() {
        $scope.warnDeviationPercentageNullShow = false;
        $scope.warnDeviationPercentageRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.deviationPercentage, "deviationPercentage")) {
            $scope.warnDeviationPercentageNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.deviationPercentage, "deviationPercentage", 0, 1000)) {
            $scope.warnDeviationPercentageRangeShow = true;
            return;
        }
    }

    $scope.blurCheckRecoverNormalThreshold = function() {
        $scope.warnRecoverNormalThresholdNullShow = false;
        $scope.warnRecoverNormalThresholdRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.recoverNormalThreshold, "recoverNormalThreshold")) {
            $scope.warnRecoverNormalThresholdNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.recoverNormalThreshold, "recoverNormalThreshold", 0, 100)) {
            $scope.warnRecoverNormalThresholdRangeShow = true;
            return;
        }
    }

    $scope.checkControllerSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("ip");
            warmCtrl("port");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("ip");
            normalCtrl("port");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkControllerError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkController = function($ip, $port) {
        $scope.isSave = false;
        if (($ip === undefined) || ($ip === '')) {
            return;
        }

        if (($port === undefined) || ($port === '')) {
            return;
        }

        $scope.warnAddrShow = false;
        isExistSameController($http, $location, 0, $ip, $port, $scope.checkControllerSuccess, $scope.checkControllerError)
    }

    $scope.controller = controllerList();

    $scope.formData = {};
    $scope.formData.type = 3;
    $scope.formData.interval = 60;
    $scope.formData.suspicionsThreshold = 3;
    $scope.formData.deviationPercentage = 50;
    $scope.formData.recoverNormalThreshold = 3;

    $scope.isSave = false;

    //ODL_HELIUM((short)0), ODL_LITHIUM((short)1), UNINET((short)2);
}
]);

app.controller('conEditController', ['$scope','$http','$localStorage', '$location',  '$state', '$stateParams', 'suffix', function ($scope, $http,$alerts, $location, $state, $stateParams, $suffix) {
    //$scope.id = $stateParams.id;


    $scope.success = function(response){
        goControllerList($state);
    }

    $scope.error = function() {
        $scope.saveErrShow = true;
    }



    $scope.goBack = function() {
        goControllerList($state);
    }

    $scope.save = function() {
        if (($scope.warnAddrShow === true)
            || ($scope.warnPortNullShow === true)
            || ($scope.warnInputportShow === true)
            || ($scope.warnOutputportShow === true)
            || ($scope.warnPortRangeShow === true)
            || ($scope.warnUserShow === true)
            || ($scope.warnPasswordShow === true)
            || ($scope.warnIntervalNullShow === true)
            || ($scope.warnIntervalRangeShow === true)
            || ($scope.warnSuspicionsThresholdNullShow === true)
            || ($scope.warnSuspicionsThresholdRangeShow === true)
            || ($scope.warnDeviationPercentageNullShow === true)
            || ($scope.warnDeviationPercentageRangeShow === true)
            || ($scope.warnRecoverNormalThresholdNullShow === true)
            || ($scope.warnRecoverNormalThresholdRangeShow === true)) {
            return;
        }



        if (($scope.warnSameShow !== undefined) && ($scope.warnSameShow !== false)) {
            $scope.isSave = true;
            return;
        }

        $scope._save();
    }

    $scope._save = function() {
        var requestUrl = getUrl($location, $suffix.controller);

        httpPut($http, requestUrl, {"id":$scope.formData.id, "ip":$scope.formData.ip, "port":$scope.formData.port,
                "inport":$scope.formData.inport, "outport":$scope.formData.outport, "user":$scope.formData.user,
                "password":$scope.formData.password, "type":$scope.formData.type,
                "detectionInterval":$scope.formData.interval,
                "detectionDeviationPercentage":$scope.formData.deviationPercentage,
                "attackSuspicionsThreshold":$scope.formData.suspicionsThreshold,
                "recoverNormalThreshold":$scope.formData.recoverNormalThreshold},
                $scope.success, $scope.error);
    }

    $scope.getSuccess = function(response) {
        var $controllerJson=eval(response);

        $scope.formData.id = $controllerJson.id;
        $scope.formData.ip = $controllerJson.ip;
        $scope.formData.port = $controllerJson.port;
        $scope.formData.inport = $controllerJson.inport;
        $scope.formData.outport = $controllerJson.outport;
        $scope.formData.user = $controllerJson.user;
        $scope.formData.password = $controllerJson.password;
        $scope.formData.type = $controllerJson.type;
        $scope.formData.interval = $controllerJson.detectionInterval;
        $scope.formData.suspicionsThreshold = $controllerJson.attackSuspicionsThreshold;
        $scope.formData.deviationPercentage = $controllerJson.detectionDeviationPercentage;
        $scope.formData.recoverNormalThreshold = $controllerJson.recoverNormalThreshold;

    }

    $scope.getError = function() {
        $scope.getErrShow = true;
    }

    $scope.putForm = function(id) {
        var requestUrl = getUrl($location, $suffix.controller + '/' + id);

        httpGet($http, requestUrl, null, $scope.getSuccess, $scope.getError);
    }

    $scope.blurCheckIp = function() {
        $scope.warnAddrShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.formData.ip, "ip")) {
            $scope.warnAddrShow = true;
            return;
        }

        $scope.checkController($scope.formData.id, $scope.formData.ip, $scope.formData.port);
    }

    $scope.blurCheckPort = function() {
        $scope.warnPortNullShow = false;
        $scope.warnPortRangeShow = false;
        $scope.warnSameShow = false;

        if (!checkNullAndShowPrompt($scope.formData.port, "port")) {
            $scope.warnPortNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.port, "port", 1, 65535)) {
            $scope.warnPortRangeShow = true;
            return;
        }

        $scope.checkController($scope.formData.id, $scope.formData.ip, $scope.formData.port);
    }

    /****************************************************************************/
    //add input and output port check
    $scope.blurCheckInputport = function() {
        $scope.warnInputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.inport, "inport")) {
            $scope.warnInputportShow = true;
            return;
        }
    }

    $scope.blurCheckOutputport = function() {
        $scope.warnOutputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.outport, "outport")) {
            $scope.warnOutputportShow = true;
            return;
        }
    }

    /****************************************************************************/

    $scope.blurCheckUser = function() {
        $scope.warnUserShow = false;
        if (!checkNullAndShowPrompt($scope.formData.user, "user")) {
            $scope.warnUserShow = true;
            return;
        }
    }

    $scope.blurCheckPassword = function() {
        $scope.warnPasswordShow = false;
        if (!checkNullAndShowPrompt($scope.formData.password, "password")) {
            $scope.warnPasswordShow = true;
            return;
        }
    }

    $scope.blurCheckInterval = function() {
        $scope.warnIntervalNullShow = false;
        $scope.warnIntervalRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.interval, "interval")) {
            $scope.warnIntervalNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.interval, "interval", 5, 600)) {
            $scope.warnIntervalRangeShow = true;
            return;
        }
    }

    $scope.blurCheckSuspicionsThreshold = function() {
        $scope.warnSuspicionsThresholdNullShow = false;
        $scope.warnSuspicionsThresholdRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.suspicionsThreshold, "suspicionsThreshold")) {
            $scope.warnSuspicionsThresholdNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.suspicionsThreshold, "suspicionsThreshold", 0, 100)) {
            $scope.warnSuspicionsThresholdRangeShow = true;
            return;
        }
    }

    $scope.blurCheckDeviationPercentage = function() {
        $scope.warnDeviationPercentageNullShow = false;
        $scope.warnDeviationPercentageRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.deviationPercentage, "deviationPercentage")) {
            $scope.warnDeviationPercentageNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.deviationPercentage, "deviationPercentage", 0, 1000)) {
            $scope.warnDeviationPercentageRangeShow = true;
            return;
        }
    }

    $scope.blurCheckRecoverNormalThreshold = function() {
        $scope.warnRecoverNormalThresholdNullShow = false;
        $scope.warnRecoverNormalThresholdRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.recoverNormalThreshold, "recoverNormalThreshold")) {
            $scope.warnRecoverNormalThresholdNullShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.recoverNormalThreshold, "recoverNormalThreshold", 0, 100)) {
            $scope.warnRecoverNormalThresholdRangeShow = true;
            return;
        }
    }

    $scope.checkControllerSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("ip");
            warmCtrl("port");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("ip");
            normalCtrl("port");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkControllerError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkController = function($id, $ip, $port) {
        $scope.isSave = false;
        if (($ip === undefined) || ($ip === '')) {
            return;
        }

        if (($port === undefined) || ($port === '')) {
            return;
        }

        $scope.warnAddrShow = false;
        isExistSameController($http, $location, $id, $ip, $port, $scope.checkControllerSuccess, $scope.checkControllerError)
    }

    $scope.formData = {};
    $scope.isSave = false;
    $scope.controller = controllerList();
    $scope.putForm($stateParams.id);

}
]);
