
//var $suffix = 'policy/netNode';


function goList(state) {
    state.go('app.netnode');
}

function poTransform(val) {
    if (val) {
        val = 1;
    } else {
        val = 0;
    }

    return val;
}

function isExistSameNetNode(http, location, id, name, successCallback, errorCallback) {
    var requestUrl = getUrl(location, 'rest/policy/isExistNetNode/' + id + '/' + name);
    httpGet(http, requestUrl, null, successCallback, errorCallback);
}

app.controller('netnodeController', ['$scope', '$http','$translate', '$localStorage', '$location', '$state', '$timeout', '$animate','suffix', function ($scope, $http, $translate, $alerts, $location, $state, $timeout, $animate, $suffix) {

    $scope.getnetNodeSuccess = function(response){
        var $countJson=eval(response);
        $scope.netNodeTableJson=$countJson.netNodes;
        $scope.netNodeTableLength=$scope.netNodeTableJson.length;
        $scope.tableloading=false;

        pageUpdate($countJson.total, $scope.page);
    }


    $scope.getnetNodeError = function() {
       // bootbox.alert("Get netnode information error");
        $scope.tableloading=false;
        $scope.getErrShow = true;
    }

    $scope.getNetNodeList = function(page){
        var requestUrl = getUrl($location, $suffix.netNode + '?page=' + page);
        httpGet($http, requestUrl, null, $scope.getnetNodeSuccess, $scope.getnetNodeError);
    }


    $scope.add = function(id) {
        $state.go('app.netnodeAdd');
    }

    $scope.edit = function(id) {
        $state.go('app.netnodeEdit', {id: id});
    }

    $scope.delnetNodeSuccess = function(response) {
        $scope.chkAll = false;
        $scope.listCheck.clearChecked();
        $scope.getNetNodeList($scope.page);
    }

    $scope.delnetNodeError = function() {

    }

    $scope.del = function(id) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }

            var requestUrl = getUrl($location, $suffix.netNode + '/' + id);
            httpDelete($http, requestUrl, null, $scope.delnetNodeSuccess, $scope.delnetNodeError);
        })

    }

    $scope.pageGetNetNodeList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getNetNodeList($scope.page);
    }

    $scope.toggleCheckAll = function() {
        $scope.listCheck.toggleCheckAll($scope.chkAll, $scope.netNodeTableJson);
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

            var requestUrl = getUrl($location, $suffix.policy + '/batchDelNetNode');
            httpPost($http, requestUrl, $scope.listCheck.getChecked(), $scope.delnetNodeSuccess, $scope.delnetNodeError);
        })
    }

    $scope.tableloading = true;
    $scope.visible=false;
    $scope.chkAll = false;
    $scope.listCheck = new ListCheck();

    $scope.page = 1;
    pageInit($scope.pageGetNetNodeList);
    $scope.getNetNodeList($scope.page);
}
]);

app.controller('netnodeAddController', ['$scope', '$http', '$location', '$state','suffix', function ($scope, $http, $location, $state, $suffix) {
    $scope.id = '';
    $scope.netnodeformData = {};

    $scope.success = function(response){
        goList($state);
    }

    $scope.error = function() {
        //bootbox.alert("error");
        $scope.saveErrShow = true;
    }

    $scope.goBack = function() {
        goList($state);
    }

    $scope.getControllerSuccess = function(response){
        var $countJson=eval(response);
        $scope.ControllerJson=$countJson;
        $scope.controllerTableLength=$scope.ControllerJson.length;
        $scope.tableloading=false;
        $scope.netnodeformData.controllerId = $countJson[0].id;
    }


    $scope.getControllerError = function() {
        //bootbox.alert("NetNode Get Controller information error");
        //$scope.warnControllerShow = true;
    }

    $scope.getControllerList = function(){
        var requestUrl = getUrl($location, $suffix.policy + '/getAllController');

        httpGet($http, requestUrl, null, $scope.getControllerSuccess, $scope.getControllerError);
    }

    $scope.blurCheckName = function() {
        $scope.warnNameShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.netnodeformData.name, "name")) {
            $scope.warnNameShow = true;
            return;
        }

        $scope.checkNetNode($scope.netnodeformData.name);
    }

    $scope.blurCheckSwId = function() {
        $scope.warnSwIdShow =false;
        if (!checkNullAndShowPrompt($scope.netnodeformData.swId, "swId")) {
            $scope.warnSwIdShow = true;
            return;
        }
    }

    $scope.checkNetNodeSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("name");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("name");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkNetNodeError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkNetNode = function($name) {
        $scope.warnNameShow = false;
        if (($name === undefined) || ($name === '')) {
            return;
        }

        isExistSameNetNode($http, $location, 0, $name, $scope.checkNetNodeSuccess, $scope.checkNetNodeError)
    }

    $scope.getControllerList();

    $scope.save = function() {

        if (poTransform($scope.netnodeformData.name)=== 0) {
            $scope.warnNameShow = true;
        }

        if (poTransform($scope.netnodeformData.swId)=== 0) {
            $scope.warnSwIdShow = true;
        }

        if ($scope.netnodeformData.controllerId == undefined) {
            $scope.warnControllerShow = true;
        }

        if (($scope.warnNameShow === true)
        || ($scope.warnSameShow === true)
        || ($scope.warnSwIdShow === true)
        || ($scope.warnControllerShow === true)) {
            return;
        }

        var requestUrl = getUrl($location, $suffix.netNode);

        httpPost($http, requestUrl, {"name":$scope.netnodeformData.name, "swId":$scope.netnodeformData.swId,
                "swType":$scope.netnodeformData.swType, "controllerId":$scope.netnodeformData.controllerId},
                $scope.success, $scope.error);
    }
}
]);

app.controller('netNodeEditController', ['$scope','$http','$localStorage', '$location',  '$state','$stateParams', 'suffix', function ($scope, $http,$alerts, $location, $state, $stateParams, $suffix) {
    //$scope.id = $stateParams.id;
    $scope.netnodeformData = {};

    $scope.success = function(response){
        goList($state);
    }

    $scope.error = function() {
        //bootbox.alert("error");
        $scope.saveErrShow = true;
    }

    $scope.goBack = function() {
        goList($state);
    }

    $scope.getControllerSuccess = function(response){
        var $countJson=eval(response);
        $scope.ControllerJson=$countJson;
        $scope.controllerTableLength=$scope.ControllerJson.length;
        $scope.tableloading=false;
    }


    $scope.getControllerError = function() {
        bootbox.alert("NetNode Get Controller information error");
    }

    $scope.getControllerList = function(){
        var requestUrl = getUrl($location, $suffix.policy + '/getAllController');

        httpGet($http, requestUrl, null, $scope.getControllerSuccess, $scope.getControllerError);
    }

    $scope.blurCheckName = function() {
        $scope.warnNameShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.netnodeformData.name, "name")) {
            $scope.warnNameShow = true;
            return;
        }

        $scope.checkNetNode($scope.netnodeformData.id, $scope.netnodeformData.name);
    }

    $scope.blurCheckSwId = function() {
        $scope.warnSwIdShow =false;
        if (!checkNullAndShowPrompt($scope.netnodeformData.swId, "swId")) {
            $scope.warnSwIdShow = true;
            return;
        }
    }

    $scope.checkNetNodeSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("name");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("name");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkNetNodeError = function (response, status) {
        $scope.getErrShow = true;
    }

    $scope.checkNetNode = function($id, $name) {
        $scope.warnNameShow = false;
        if (($name === undefined) || ($name === '')) {
            return;
        }

        isExistSameNetNode($http, $location, $id, $name, $scope.checkNetNodeSuccess, $scope.checkNetNodeError);
    }

    $scope.getControllerList();

    $scope.save = function() {

        if (poTransform($scope.netnodeformData.name)=== 0) {
            $scope.warnNameShow = true;
        }

        if (poTransform($scope.netnodeformData.swId)=== 0) {
            $scope.warnSwIdShow = true;
        }

        if ($scope.netnodeformData.controllerId == undefined) {
            $scope.warnControllerShow = true;
        }

        if (($scope.warnNameShow === true)
            || ($scope.warnSameShow === true)
            || ($scope.warnSwIdShow === true)
            || ($scope.warnControllerShow === true)) {
            return;
        }

        var requestUrl = getUrl($location, $suffix.netNode);

        var requestUrl = getUrl($location, $suffix.netNode);
        httpPut($http, requestUrl, {"id":$scope.netnodeformData.id, "name":$scope.netnodeformData.name,
                "swId":$scope.netnodeformData.swId, "swType":$scope.netnodeformData.swType,
                "controllerId":$scope.netnodeformData.controllerId}, $scope.success, $scope.error);
    }

    $scope.putNetNodeToForm = function(response) {
        var $countJson=eval(response);

        $scope.netnodeformData.id = $countJson.id;
        $scope.netnodeformData.name = $countJson.name;
        $scope.netnodeformData.swId = $countJson.swId;
        $scope.netnodeformData.swType = $countJson.swType;
        $scope.netnodeformData.controllerId = $countJson.controllerId;
    }

    $scope.getNodeError = function(response) {

    }

    $scope.putForm = function(id) {
        var requestUrl = getUrl($location, $suffix.netNode + '/' + id);

        httpGet($http,requestUrl, null, $scope.putNetNodeToForm, $scope.getNodeError);
    }

    $scope.putForm($stateParams.id);

    $scope.blurCheckName = function() {
        $scope.warnNameShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.netnodeformData.name, "name")) {
            $scope.warnNameShow = true;
            return;
        }

        $scope.checkNetNode($scope.netnodeformData.id, $scope.netnodeformData.name);
    }

    $scope.blurCheckSwId = function() {
        $scope.warnSwIdShow =false;
        if (!checkNullAndShowPrompt($scope.netnodeformData.swId, "swId")) {
            $scope.warnSwIdShow = true;
            return;
        }
    }
    $scope.checkNetNodeSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("name");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("name");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkNetNodeError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkNetNode = function($id,$name) {
        $scope.warnNameShow = false;
        if (($name === undefined) || ($name === '')) {
            return;
        }

        isExistSameNetNode($http, $location, $id, $name, $scope.checkNetNodeSuccess, $scope.checkNetNodeError);
    }
}
]);
