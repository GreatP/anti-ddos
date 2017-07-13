
function poTransform(val) {
    if (val) {
    	val = 1;
    } else {
        val = 0;
    }

    return val;
}

function defenseTypeTransform(val) {
    if (val == "0") {
        val = "LIMIT_RATE";
    } else {
        val = "GUIDE_FLOW";
    }

    return val;
}

function repoTransform(val) {
    if (val) {
    	val = true;
    } else {
        val = false;
    }

    return val;
}

function goPoList(state) {
    state.go('app.po');
}

//function isExistSamePo(http, location, id, name, successCallback, errorCallback) {
//    var requestUrl = getUrl(location, 'rest/policy/isExistPO/' + id + '/' + name);
//    httpGet(http, requestUrl, null, successCallback, errorCallback);
//}

function defensetypelist() {
    var defensetype = new Array();
    defensetype.push({'type':0, 'name':'LIMIT_RATE'});
    defensetype.push({'type':1, 'name':'GUIDE_FLOW'});

    return defensetype;
}

app.controller('poController', ['$scope', '$http','$translate', '$localStorage', '$location', '$state', '$timeout', '$animate','suffix', function ($scope, $http, $translate, $alerts, $location, $state, $timeout, $animate,$suffix) {
    
	

    $scope.getPoSuccess = function(response){
        var $countJson=eval(response);
        $scope.userTableJson=$countJson.pos;
        $scope.userTableLength=$scope.userTableJson.length;
        $scope.tableloading=false;
        pageUpdate($countJson.total, $scope.page);
    }
    
    
    $scope.getPoError = function() {
        $scope.tableloading=false;
        $scope.getErrShow = true;
    }

    $scope.getPoList = function(page){
        var requestUrl = getUrl($location, $suffix.po + '?page=' + page);
        httpGet($http, requestUrl, null, $scope.getPoSuccess, $scope.getPoError);
    }
    
    $scope.pageGetPoList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getPoList($scope.page);
    }

    $scope.add = function(id) {
        $state.go('app.poAdd');
    }

    $scope.edit = function(id) {
        $state.go('app.poEdit', {id: id});
    }

    $scope.delPoSuccess = function(response) {
        $scope.chkAll = false;
        $scope.listCheck.clearChecked();
        $scope.getPoList($scope.page);
    }

    $scope.delPoError = function() {

    }


    $scope.del = function(id) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }

            var requestUrl = getUrl($location, $suffix.po + '/' + id);
            httpDelete($http, requestUrl, null, $scope.delPoSuccess, $scope.delPoError);
        })

    }
    
    $scope.toggleCheckAll = function() {
        $scope.listCheck.toggleCheckAll($scope.chkAll, $scope.userTableJson);
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

            var requestUrl = getUrl($location, $suffix.policy + '/batchDelPo');
            httpPost($http, requestUrl, $scope.listCheck.getChecked(), $scope.delPoSuccess, $scope.delPoError);
        })
    }


    $scope.tableloading=true;
    $scope.visible=false;
    $scope.chkAll = false;
    $scope.listCheck = new ListCheck();
    $scope.page = 1;

    pageInit($scope.pageGetPoList);
    $scope.getPoList($scope.page);
}
]);

app.controller('poAddController', ['$scope', '$http', '$location', '$state', 'suffix', function ($scope, $http, $location, $state, $suffix) {
    $scope.id = '';
    $scope.formData = {};
    $scope.nodeJson = {};
    $scope.checkflag = 0;
    $scope.detectorShowForm = false;
    $scope.detectorCleanDevShow = false;
    $scope.detectorShow = false;


    $scope.success = function(response){
        goPoList($state);
    }

    $scope.error = function() {
        //bootbox.alert("error");
        $scope.saveErrShow = true;
    }
    
    
    $scope.getNodeSuccess = function(response){
        var $countJson=eval(response);
        $scope.nodeJson=$countJson;
        $scope.nodeLength=$scope.nodeJson.length;
        $scope.formData.netnode_id = $countJson[0].id;
        $scope.tableloading=false;
    }
    
    
    $scope.getNodeError = function() {
        bootbox.alert("error");
    }
    

    $scope.getNode = function(){
        var requestUrl = getUrl($location, $suffix.netNodeAll);
        httpGet($http, requestUrl, null, $scope.getNodeSuccess, $scope.getNodeError);
    }

    //get all controller
    $scope.getAllControllerSuccess = function(response){
        var $countJson=eval(response);
        $scope.nodeJson=$countJson;
        $scope.nodeLength=$scope.nodeJson.length;
        $scope.formData.controllerId = $countJson[0].id;
        $scope.tableloading=false;
    }


    $scope.getAllControllerError = function() {
        bootbox.alert("error");
    }


    $scope.getAllController = function(){
        var requestUrl = getUrl($location, $suffix.controllerAll);
        httpGet($http, requestUrl, null, $scope.getAllControllerSuccess, $scope.getAllControllerError);
    }
    /************************************************************************/

    $scope.getCleanDevSuccess = function(response){
        var $countJson=eval(response);
        $scope.cleanDevJson=$countJson;
        $scope.cleanDevLength=$scope.cleanDevJson.length;
        $scope.formData.cleanDevId = $countJson[0].id;
        $scope.tableloading=false;
    }

    $scope.getCleanDevError = function() {
        bootbox.alert("error");
    }

    $scope.getCleanDev = function() {
        var requestUrl = getUrl($location, $suffix.cleanDevAll);
        httpGet($http, requestUrl, null, $scope.getCleanDevSuccess, $scope.getCleanDevError);
    }

    $scope.getTenantSuccess = function(response){
        var $countJson=eval(response);
        $scope.tenantIdJson=$countJson;
        $scope.tenantIdLength=$scope.tenantIdJson.length;
        $scope.formData.tenantId = -1;
        $scope.tableloading=false;
    }

    $scope.getTenantError = function() {
        bootbox.alert("error");
    }

    $scope.getTenant = function() {
        var requestUrl = getUrl($location, $suffix.getTenants);
        httpGet($http, requestUrl, null, $scope.getTenantSuccess, $scope.getTenantError);
    }

    $scope.getCurrentUserSuccess = function (response) {
        var userJson = eval(response);
        if (userJson.role == "ADMIN") {
            $scope.detectorCleanDevShow = true;
            $scope.detectorShowForm = true;
        } else {
            $scope.detectorCleanDevShow = false;
            $scope.detectorShowForm = false;
        }
    }

    $scope.getCurrentUserError = function (response, status) {
        //alert(response);
        //alert(status);
        $scope.detectorCleanDevShow = false;
    }

    $scope.getCurrentUser = function() {
        var requestUrl = getUrl($location, $suffix.currentUser);
        httpGet($http, requestUrl, null, $scope.getCurrentUserSuccess, $scope.getCurrentUserError)
    }

    $scope.getTenant();
    $scope.getCurrentUser();
    $scope.getCleanDev();
    //$scope.getNode();


    
    $scope.goBack = function() {
        goPoList($state);
    }
    
    $scope.autodis = function() {
    	document.getElementById('pps').disabled = true;
    	document.getElementById('kbps').disabled = true;
        $scope.warnPpsShow = false;
        $scope.warnKbpsShow = false;
        $scope.formData.pps = "";
        $scope.formData.kbps = "";
        normalCtrl("pps");
        normalCtrl("kbps");
    }
    
    $scope.fixdis = function() {
    	
    	document.getElementById('pps').disabled = false;
    	document.getElementById('kbps').disabled = false;
    }
    $scope.blurCheckDefensetype = function() {
        var defenseType = document.getElementById('defenseType').value;
        if (defenseType == "0") {
            document.getElementById('guidePort').disabled = true;
            normalCtrl("guidePort");
            $scope.warnGuideportShow = false;
        } else {
            document.getElementById('guidePort').disabled = false;
        }
    }

    $scope.blurCheckName = function() {
        $scope.warnNameShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.formData.name, "name")) {
            $scope.warnNameShow = true;
            return;
        }

        $scope.checkPO($scope.formData.name);
    }

    $scope.check_tadem = function () {
        $scope.detectorShow = false;
        $scope.formData.controllerId = 0;
    }

    $scope.check_bypass = function () {
        $scope.getAllController();
        $scope.detectorShow = true;
    }

    $scope.blurCheckOutputport = function() {
        $scope.warnOutputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.outport, "outport")) {
            $scope.warnOutputportShow = true;
            return;
        }
    }


    $scope.checkPONetworkSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("network");
            $scope.warnSameNetWorkShow = true;
        } else {
            normalCtrl("network");
            $scope.warnSameNetWorkShow = false;
        }
    }

    $scope.checkPONetworkError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkPONetwork = function($network) {
        $scope.warnSameNetWorkShow = false;
        if (($network === undefined) || ($network === '')) {
            return;
        }

        //$network = $network.replace("/","-");

        var requestUrl = getUrl($location, "rest/policy/isExistPONetwork");
        httpPost($http, requestUrl, {"network":$network}, $scope.checkPONetworkSuccess, $scope.checkPONetworkError);
    }

    $scope.blurCheckPn = function() {
        $scope.warnPnShow =false;
        $scope.warnPnNullShow = false;
        if (!checkNullAndShowPrompt($scope.formData.network, "network")) {
            $scope.warnPnNullShow = true;
            return;
        }

        /**判断ipv4输入格式的正则表达式**/
        var regex = /^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\/(([0-9])|([1-2][0-9])|(3[0-2]))$/;

        if(!regex.test($scope.formData.network)){
        	$scope.warnPnShow = true;
            return ;
        }

        $scope.checkPONetwork($scope.formData.network);
        /**************************************************/
    }

    $scope.blurCheckGuideport = function() {
        $scope.warnGuideportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.guidePort, "guidePort")) {
            $scope.warnGuideportShow = true;
            return;
        }
    }

    $scope.blurCheckCleanOutputport = function() {
        $scope.warnCleanOutputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.cleanOutport, "cleanOutport")) {
            $scope.warnCleanOutputportShow = true;
            return;
        }
    }

    $scope.blurCheckPPS = function() {
        $scope.warnPpsShow =false;
        $scope.warnPpsTypeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.pps, "pps")) {
            $scope.warnPpsShow = true;
            return;
        }

        if (!checkPIntAndShowPrompt($scope.formData.pps, "pps")) {
            $scope.warnPpsTypeShow = true;
            return;
        }
    }

    $scope.blurCheckKbps = function() {
        $scope.warnKbpsShow =false;
        $scope.warnKbpsTypeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.kbps, "kbps")) {
            $scope.warnKbpsShow = true;
            return;
        }
        if (!checkPIntAndShowPrompt($scope.formData.kbps, "kbps")) {
            $scope.warnKbpsTypeShow = true;
            return;
        }
    }

    $scope.checkPOSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("name");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("name");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkPOError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkPO = function($name) {
        $scope.warnNameShow = false;
        if (($name === undefined) || ($name === '')) {
            return;
        }

        var requestUrl = getUrl($location, "rest/policy/isExistPO");
        httpPost($http, requestUrl, {"name":$name, "id":0}, $scope.checkPOSuccess, $scope.checkPOError);

        //isExistSamePo($http, $location, 0, $name, $scope.checkPOSuccess, $scope.checkPOError)
    }


    //$scope.clickCheckShowDetector = function() {
    //    $scope.detectorShow = $scope.formData.checkDetector;
    //}

    /***************检测PO阀值是否为空******************************************************************/

    $scope.blurCheckReinjectionPort = function() {
        $scope.warnReinjectionPortNullShow = false;

        if (!checkNullAndShowPrompt($scope.formData.reinjection_port, "reinjection_port")) {
            $scope.warnReinjectionPortNullShow = true;
            return;
        }
    }

    $scope.blurCheckInterval = function() {
        $scope.warnCheckIntervalShow = false;
        $scope.warnCheckIntervalNumShow = false;
        $scope.warnCheckIntervalRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.check_interval, "check_interval")) {
            $scope.warnCheckIntervalShow = true;
            return;
        }

        if(!isNum($scope.formData.check_interval)) {
            $scope.warnCheckIntervalNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.check_interval, "check_interval")) {
            $scope.warnCheckIntervalRangeShow = true;
        }
    }


    $scope.blurCheckTcpFlood = function() {
        $scope.warnCheckTcpShow = false;
        $scope.warnCheckTcpNumShow = false;
        $scope.warnCheckTcpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.tcp_flood, "tcp_flood")) {
            $scope.warnCheckTcpShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_flood)) {
            $scope.warnCheckTcpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_flood, "tcp_flood")) {
            $scope.warnCheckTcpRangeShow = true;
        }
    }



    $scope.blurCheckTcpSynAck = function() {
        $scope.warnCheckTcpSynAckShow = false;
        $scope.warnCheckTcpSynAckNumShow = false;
        $scope.warnCheckTcpSynAckRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.tcp_synack, "tcp_synack")) {
            $scope.warnCheckTcpSynAckShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_synack)) {
            $scope.warnCheckTcpSynAckNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_synack, "tcp_synack")) {
            $scope.warnCheckTcpSynAckRangeShow = true;
        }
    }
    $scope.blurCheckTcpSYN = function() {
        $scope.warnCheckTcpSynShow = false;
        $scope.warnCheckTcpSynNumShow = false;
        if (!checkNullAndShowPrompt($scope.formData.tcp_syn, "tcp_syn")) {
            $scope.warnCheckTcpSynShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_syn)) {
            $scope.warnCheckTcpSynNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_syn, "tcp_syn")) {
            $scope.warnCheckTcpSynRangeShow = true;
        }
    }


    $scope.blurCheckUDP = function() {
        $scope.warnCheckUdpShow = false;
        $scope.warnCheckUdpNumShow = false;
        $scope.warnCheckUdpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.udp_threshold, "udp_threshold")) {
            $scope.warnCheckUdpShow = true;
            return;
        }

        if(!isNum($scope.formData.udp_threshold)) {
            $scope.warnCheckUdpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.udp_threshold, "udp_threshold")) {
            $scope.warnCheckUdpRangeShow = true;
        }
    }

    $scope.blurCheckICMP = function() {
        $scope.warnCheckIcmpShow =false;
        $scope.warnCheckIcmpNumShow = false;
        $scope.warnCheckIcmpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.icmp_threshold, "icmp_threshold")) {
            $scope.warnCheckIcmpShow = true;
            return;
        }

        if(!isNum($scope.formData.icmp_threshold)) {
            $scope.warnCheckIcmpNumShow = true;
            return;
        }


        if (!checkThresholdRangeFixValue($scope.formData.icmp_threshold, "icmp_threshold")) {
            $scope.warnCheckIcmpRangeShow = true;
        }
    }

    $scope.blurCheckHttp = function() {
        $scope.warnCheckHttpShow = false;
        $scope.warnCheckHttpNumShow = false;
        $scope.warnCheckHttpRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http, "http")) {
            $scope.warnCheckHttpShow = true;
            return;
        }

        if(!isNum($scope.formData.http)) {
            $scope.warnCheckHttpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http, "http")) {
            $scope.warnCheckHttpRangeShow = true;
        }
    }

    $scope.blurCheckHttpRequest = function() {
        $scope.warnCheckHttpRequestShow = false;
        $scope.warnCheckHttpRequestNumShow = false;
        $scope.warnCheckHttpRequestRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http_request, "http_request")) {
            $scope.warnCheckHttpRequestShow = true;
            return;
        }

        if(!isNum($scope.formData.http_request)) {
            $scope.warnCheckHttpRequestNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http_request, "http_request")) {
            $scope.warnCheckHttpRequestRangeShow = true;
        }
    }

    $scope.blurCheckHttpPost = function() {
        $scope.warnCheckHttpPostShow = false;
        $scope.warnCheckHttpPostNumShow = false;
        $scope.warnCheckHttpPostRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http_post, "http_post")) {
            $scope.warnCheckHttpPostShow = true;
            return;
        }

        if(!isNum($scope.formData.http_post)) {
            $scope.warnCheckHttpPostNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http_post, "http_post")) {
            $scope.warnCheckHttpPostRangeShow = true;
        }
    }

    $scope.blurCheckHttpPort = function() {
        $scope.warnCheckHttpPortShow = false;
        $scope.warnHttpPortRangeShow = false;
        $scope.warnCheckHttpPortNumShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http_port, "http_port")) {
            $scope.warnCheckHttpPortShow = true;
            return;
        }

        if(!isNum($scope.formData.http_port)) {
            $scope.warnCheckHttpPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.http_port, "http_port", 1, 65535)) {
            $scope.warnHttpPortRangeShow = true;
            return;
        }
    }


    $scope.blurCheckHttpsRequest = function() {
        $scope.warnCheckHttpsRequestShow = false;
        $scope.warnCheckHttpsRequestNumShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https_request, "https_request")) {
            $scope.warnCheckHttpsRequestShow = true;
            return;
        }

        if(!isNum($scope.formData.https_request)) {
            $scope.warnCheckHttpsRequestNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.https_request, "https_request")) {
            $scope.warnCheckHttpsRequestRangeShow = true;
        }

    }


    $scope.blurCheckHttpsThc = function() {
        $scope.warnCheckHttpsThcShow = false;
        $scope.warnCheckHttpsThcNumShow = false;
        $scope.warnCheckHttpsThcRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https_thc, "https_thc")) {
            $scope.warnCheckHttpsThcShow = true;
            return;
        }

        if(!isNum($scope.formData.https_thc)) {
            $scope.warnCheckHttpsThcNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.https_thc, "https_thc")) {
            $scope.warnCheckHttpsThcRangeShow = true;
        }
    }

    $scope.blurCheckHttpsPort = function() {
        $scope.warnCheckHttpsPortShow = false;
        $scope.warnCheckHttpsPortNumShow = false;
        $scope.warnHttpsPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https_port, "https_port")) {
            $scope.warnCheckHttpsPortShow = true;
            return;
        }

        if(!isNum($scope.formData.https_port)) {
            $scope.warnCheckHttpsPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.https_port, "https_port", 1, 65535)) {
            $scope.warnHttpsPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckDnsReq = function() {
        $scope.warnCheckDnsReqShow = false;
        $scope.warnCheckDnsReqNumShow = false;
        $scope.warnCheckDnsReqRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_req, "dns_req")) {
            $scope.warnCheckDnsReqShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_req)) {
            $scope.warnCheckDnsReqNumShow = true;
            return;
        }


        if (!checkThresholdRangeFixValue($scope.formData.dns_req, "dns_req")) {
            $scope.warnCheckDnsReqRangeShow = true;
        }
    }

    $scope.blurCheckDnsRep = function() {
        $scope.warnCheckDnsRepShow = false;
        $scope.warnCheckDnsRepNumShow = false;
        $scope.warnCheckDnsRepRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_rep, "dns_rep")) {
            $scope.warnCheckDnsRepShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_rep)) {
            $scope.warnCheckDnsRepNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.dns_rep, "dns_rep")) {
            $scope.warnCheckDnsRepRangeShow = true;
        }
    }


    $scope.blurCheckDnsPort = function() {
        $scope.warnCheckDnsPortShow = false;
        $scope.warnCheckDnsPortNumShow = false;
        $scope.warnDnsPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_port, "dns_port")) {
            $scope.warnCheckDnsPortShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_port)) {
            $scope.warnCheckDnsPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.dns_port, "dns_port", 1, 65535)) {
            $scope.warnDnsPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckDnsAbnormal = function() {
        $scope.warnCheckDnsAbnormalShow = false;
        $scope.warnCheckDnsAbnormalNumShow = false;
        if (!checkNullAndShowPrompt($scope.formData.dns_abnormal, "dns_abnormal")) {
            $scope.warnCheckDnsAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_abnormal)) {
            $scope.warnCheckDnsAbnormalNumShow = true;
            return;
        }
    }

    /**************************************************************************************
     * For ntp and snmp
     */

    $scope.blurCheckNtpPort = function() {
        $scope.warnCheckNtpPortShow = false;
        $scope.warnCheckNtpPortNumShow = false;
        $scope.warnCheckNtpPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.ntp_port, "ntp_port")) {
            $scope.warnCheckNtpPortShow = true;
            return;
        }

        if(!isNum($scope.formData.ntp_port)) {
            $scope.warnCheckNtpPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.ntp_port, "ntp_port", 1, 65535)) {
            $scope.warnCheckNtpPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckNtp = function() {
        $scope.warnCheckNtpShow = false;
        $scope.warnCheckNtpNumShow = false;
        $scope.warnCheckNtpRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.ntp, "ntp")) {
            $scope.warnCheckNtpShow = true;
            return;
        }

        if(!isNum($scope.formData.ntp)) {
            $scope.warnCheckNtpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.ntp, "ntp")) {
            $scope.warnCheckNtpRangeShow = true;
        }
    }


    $scope.blurCheckSnmpPort = function() {
        $scope.warnCheckSnmpPortShow = false;
        $scope.warnCheckSnmpPortNumShow = false;
        $scope.warnCheckSnmpPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.snmp_port, "snmp_port")) {
            $scope.warnCheckSnmpPortShow = true;
            return;
        }

        if(!isNum($scope.formData.snmp_port)) {
            $scope.warnCheckSnmpPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.snmp_port, "snmp_port", 1, 65535)) {
            $scope.warnCheckSnmpPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckSnmp = function() {
        $scope.warnCheckSnmpShow = false;
        $scope.warnCheckSnmpNumShow = false;
        $scope.warnCheckSnmpRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.snmp, "snmp")) {
            $scope.warnCheckSnmpShow = true;
            return;
        }

        if(!isNum($scope.formData.snmp)) {
            $scope.warnCheckSnmpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.snmp, "snmp")) {
            $scope.warnCheckSnmpRangeShow = true;
        }
    }

    $scope.blurCheckIpOption = function() {
        $scope.warnCheckIpOptionShow = false;
        $scope.warnCheckIpOptionNumShow = false;
        $scope.warnCheckIpOptionRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.ipoption_threshold, "ipoption_threshold")) {
            $scope.warnCheckIpOptionShow = true;
            return;
        }

        if(!isNum($scope.formData.ipoption_threshold)) {
            $scope.warnCheckIpOptionNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.ipoption_threshold, "ipoption_threshold")) {
            $scope.warnCheckIpOptionRangeShow = true;
        }
    }

    /******************************************************************************************/

    /************************************************************************************************/


    $scope.save = function() {

        if (poTransform($scope.formData.name)=== 0) {
            $scope.warnNameShow = true;
        }

        if ($scope.formData.mode != 0) {
            if (poTransform($scope.formData.controllerId) === 0) {
                $scope.warnControllerShow = true;
            }

            if (poTransform($scope.formData.inport) === 0) {
                $scope.warnInputportShow = true;
            }

            if (poTransform($scope.formData.outport) === 0) {
                $scope.warnOutputportShow = true;
            }

            if (poTransform($scope.formData.guidePort) === 0) {
                $scope.warnGuideportShow = true;
            }
        }

/*
        if ($scope.formData.checkDetector == 1) {
            $scope.warnOutputportShow = false;
            $scope.warnReinjectionPortNullShow = false;
            $scope.warnGuideportShow = false;

            if (poTransform($scope.formData.outport) === 0) {
                $scope.warnOutputportShow = true;
            }

            if (poTransform($scope.formData.reinjection_port) === 0) {
                $scope.warnReinjectionPortNullShow = true;
            }

            if (poTransform($scope.formData.guidePort)=== 0) {
                $scope.warnGuideportShow = true;
            }
        }
*/
        /*
        if ($scope.formData.defenseType == "1") {
            if (poTransform($scope.formData.guidePort)=== 0) {
                $scope.warnGuideportShow = true;
            }
        }*/

        if (poTransform($scope.formData.network)=== 0) {
            $scope.warnPnNullShow = true;
        }


        if ($scope.formData.autoOrfix == 1) {
            if ($scope.formData.pps == 0) {
                $scope.warnPpsShow = true;
            }

            if ($scope.formData.kbps == 0) {
                $scope.warnKbpsShow = true;
            }
        }
        /******************************检测PO的阀值是否为空***************************************/
        if (poTransform($scope.formData.check_interval) == 0) {
            $scope.warnCheckIntervalShow = true;
        }

        if (poTransform($scope.formData.tcp_syn) == 0) {
            $scope.warnCheckTcpSynShow = true;
        }

        if (poTransform($scope.formData.tcp_synack) == 0) {
            $scope.warnCheckTcpSynAckShow = true;
        }

        if (poTransform($scope.formData.udp_threshold) == 0) {
            $scope.warnCheckUdpShow = true;
        }

        if (poTransform($scope.formData.icmp_threshold) == 0) {
            $scope.warnCheckIcmpShow = true;
        }

        if (poTransform($scope.formData.http) == 0) {
            $scope.warnCheckHttpShow = true;
        }

        if (poTransform($scope.formData.http_request) == 0) {
            $scope.warnCheckHttpRequestShow = true;
        }

        if (poTransform($scope.formData.http_post) == 0) {
            $scope.warnCheckHttpPostShow = true;
        }

        if (poTransform($scope.formData.http_port) == 0) {
            $scope.warnCheckHttpPortShow = true;
        }

        if (poTransform($scope.formData.https_request) == 0) {
            $scope.warnCheckHttpsRequestShow = true;
        }

        if (poTransform($scope.formData.https_thc) == 0) {
            $scope.warnCheckHttpsThcShow = true;
        }


        if (poTransform($scope.formData.https_port) == 0) {
            $scope.warnCheckHttpsPortShow = true;
        }

        if (poTransform($scope.formData.dns_req) == 0) {
            $scope.warnCheckDnsReqShow = true;
        }

        if (poTransform($scope.formData.dns_rep) == 0) {
            $scope.warnCheckDnsRepShow = true;
        }

        if (poTransform($scope.formData.dns_port) == 0) {
            $scope.warnCheckDnsPortShow = true;
        }

        if (poTransform($scope.formData.ntp) == 0) {
            $scope.warnCheckNtpShow = true;
        }

        if (poTransform($scope.formData.ntp_port) == 0) {
            $scope.warnCheckNtpPortShow = true;
        }

        if (poTransform($scope.formData.snmp) == 0) {
            $scope.warnCheckSnmpShow = true;
        }

        if (poTransform($scope.formData.snmp_port) == 0) {
            $scope.warnCheckSnmpPortShow = true;
        }

        if (poTransform($scope.formData.ipoption_threshold) == 0) {
            $scope.warnCheckIpOptionShow = true;
        }

        if (($scope.warnCheckIntervalShow === true)
            || ($scope.warnCheckTcpSynShow === true)
            || ($scope.warnCheckTcpSynAckShow === true)
            || ($scope.warnCheckUdpShow === true)
            || ($scope.warnCheckIcmpShow === true)
            || ($scope.warnCheckHttpRequestShow === true)
            || ($scope.warnCheckHttpPortShow === true)
            || ($scope.warnCheckHttpsRequestShow === true)
            || ($scope.warnCheckHttpsPortShow === true)
            || ($scope.warnCheckDnsReqShow === true)
            || ($scope.warnCheckDnsRepShow === true)
            || ($scope.warnReinjectionPortNullShow === true)
            || ($scope.warnCheckDnsPortShow === true)
            || ($scope.warnCheckIpOptionShow === true)
            || ($scope.warnCheckIpOptionNumShow === true)
            || ($scope.warnCheckNtpPortShow === true)
            || ($scope.warnCheckSnmpPortShow === true)
            || ($scope.warnCheckNtpShow === true)
            || ($scope.warnCheckSnmpShow === true)) {
            return;
        }
        /*********************************************************************************************/

            if (($scope.warnNameShow === true)
                || ($scope.warnSameShow === true)
            || ($scope.warnInputportShow === true)
            || ($scope.warnOutputportShow === true)
            || ($scope.warnGuideportShow === true)
            || ($scope.warnPnShow === true)
            || ($scope.warnPnNullShow === true)
            || ($scope.warnPpsShow === true)
            || ($scope.warnPpsTypeShow == true)
            || ($scope.warnKbpsShow === true)
            || ($scope.warnKpsTypeShow === true)) {
            return;
        }

        if (($scope.warnCheckIntervalRangeShow === true)
            || ($scope.warnCheckTcpSynRangeShow === true)
            || ($scope.warnCheckTcpSynAckRangeShow === true)
            || ($scope.warnCheckUdpRangeShow === true)
            || ($scope.warnCheckIcmpRangeShow === true)
            || ($scope.warnCheckHttpRequestRangeShow === true)
            || ($scope.warnHttpPortRangeShow === true)
            || ($scope.warnCheckHttpsRequestRangeShow === true)
            || ($scope.warnHttpsPortRangeShow === true)
            || ($scope.warnCheckDnsReqRangeShow === true)
            || ($scope.warnCheckDnsRepRangeShow === true)
            || ($scope.warnDnsPortRangeShow === true)
            || ($scope.warnCheckNtpPortRangeShow === true)
            || ($scope.warnCheckSnmpPortRangeShow === true)
            || ($scope.warnCheckNtpRangeShow === true)
            || ($scope.warnCheckSnmpRangeShow === true)
            || ($scope.warnCheckIpOptionRangeShow === true)) {
            return;
        }
        
        /*
        if ($scope.formData.autoOrfix == 0) {
        	$scope.formData.autoOrfix = 1;
        	$scope.formData.pps = 10000000;
        	$scope.formData.kbps = 30;
        }*/
        
        //autoOrfix_tmp = 1;
        //pps_tmp = 10000000;
        //kbps_tmp = 30;
        

        //$scope.formData.defenseType = defenseTypeTransform($scope.formData.defenseType);
        $scope.formData.defenseType = "GUIDE_FLOW";
        $scope.formData.tcp = 1;//poTransform($scope.formData.tcp);
        $scope.formData.udp = 1;//poTransform($scope.formData.udp);
        $scope.formData.icmp = 1;//poTransform($scope.formData.icmp);
        $scope.formData.any = 1;//poTransform($scope.formData.any);

        if ($scope.formData.iface_category == 1) {
            $scope.formData.cleanInport = 1;
            $scope.formData.cleanOutport = 2;
        } else {
            $scope.formData.cleanInport = 3;
            $scope.formData.cleanOutport = 4;
        }


        var requestUrl = getUrl($location, $suffix.po);
        httpPost($http, requestUrl, {"name":$scope.formData.name, "network":$scope.formData.network,
                "tenantId":$scope.formData.tenantId, "cleanDevId":$scope.formData.cleanDevId,
                "cleanInport": $scope.formData.cleanInport ,"controllerId":$scope.formData.controllerId,
                "cleanOutport": $scope.formData.cleanOutport, "inport":$scope.formData.inport,
                "outport":$scope.formData.outport, "reinjectionPort":$scope.formData.reinjection_port,
                "defenseType":$scope.formData.defenseType,
                "guidePort":$scope.formData.guidePort, "netnode_id":$scope.formData.netnode_id,
                "tcp":$scope.formData.tcp,"udp":$scope.formData.udp,"icmp":$scope.formData.icmp,
                "any":$scope.formData.any,"autoOrfix":$scope.formData.autoOrfix,"pps":$scope.formData.pps,
                "kbps":$scope.formData.kbps,"check_interval":$scope.formData.check_interval,
                "tcp_syn":$scope.formData.tcp_syn,"ipOption":$scope.formData.ipoption_threshold,
                "tcp_synack":$scope.formData.tcp_synack,
                "udp_threshold":$scope.formData.udp_threshold, "icmp_threshold":$scope.formData.icmp_threshold,
                "icmpRedirect":$scope.formData.icmp_redirect,"httpSrcAuth":$scope.formData.http_src_auth,
                "tcp_first":$scope.formData.tcp_first,"http_port":$scope.formData.http_port,
                "http_request":$scope.formData.http_request,"https_request":$scope.formData.https_request,
                "https_port":$scope.formData.https_port, "dns_request":$scope.formData.dns_req,
                "dns_reply":$scope.formData.dns_rep, "dns_port":$scope.formData.dns_port,
                "ntp_port":$scope.formData.ntp_port, "ntp":$scope.formData.ntp,
                "snmp_port":$scope.formData.snmp_port, "snmp":$scope.formData.snmp},
                $scope.success, $scope.error);
    }

    $scope.defensetype = defensetypelist();

    $scope.formData = {};
    $scope.formData.mode = 0;
    $scope.formData.controllerId = 0;
    $scope.formData.iface_category = 1;
    $scope.formData.defenseType = 0; // set the default defense type(flow-limit)
    $scope.formData.autoOrfix = 0;// set the default flow threshold type(auto-learn)

    /********************************为PO各协议阀值设置初始值******************************************/
    $scope.formData.check_interval = 2;
    $scope.formData.http_port = 80;
    $scope.formData.https_port = 443;
    $scope.formData.dns_port = 53;
    $scope.formData.ipoption_threshold = 10;
    $scope.formData.tcp_syn = 2000;
    $scope.formData.tcp_synack = 2000;
   // $scope.formData.tcp_flood = 2000;
    //$scope.formData.tcp_abnormal = 500;
    $scope.formData.udp_threshold = 50000;
    $scope.formData.icmp_threshold = 50000;
    //$scope.formData.icmp_abnormal = 500;
    $scope.formData.http = 40;
    $scope.formData.http_request = 1000;
    $scope.formData.http_post = 6;
    $scope.formData.https_request = 1000;
    $scope.formData.https_thc = 20;
    $scope.formData.dns_req = 20;
    $scope.formData.dns_rep = 2000;
    $scope.formData.dns_abnormal = 100;
    $scope.formData.tenantId = -1;


    $scope.formData.ntp = 1000;
    $scope.formData.ntp_port = 123;
    $scope.formData.snmp = 1000;
    $scope.formData.snmp_port = 161;
    /**********************************************************************************************/
}
]);

app.controller('poEditController', ['$scope','$http','$localStorage', '$location', '$state', '$stateParams', 'suffix', function ($scope, $http,$alerts, $location, $state, $stateParams, $suffix) {
    //$scope.id = $stateParams.id;
    $scope.formData = {};
    $scope.nodeJson = {};
    $scope.cleanDevJson = {};
    $scope.checkflag = 0;
    var Guideport = 0;
    var defenseType = "";
    $scope.detectorShowForm = false;
    $scope.detectorCleanDevShow = false;
    $scope.detectorShow = false;


    $scope.success = function(response){
        goPoList($state);
    }

    $scope.error = function() {
        //bootbox.alert("error");
        $scope.saveErrShow = true;
    }

    $scope.getCurrentUserSuccess = function (response) {
        var userJson = eval(response);
        if (userJson.role == "ADMIN") {
            $scope.detectorCleanDevShow = true;
            $scope.detectorShowForm = true;
        } else {
            $scope.detectorCleanDevShow = false;
            $scope.detectorShowForm = false;
        }
    }

    $scope.getCurrentUserError = function (response, status) {
        //alert(response);
        //alert(status);
        $scope.detectorCleanDevShow = false;
        $scope.detectorShow = false;
        $scope.detectorShowForm = false;
    }

    $scope.getCurrentUser = function() {
        var requestUrl = getUrl($location, $suffix.currentUser);
        httpGet($http, requestUrl, null, $scope.getCurrentUserSuccess, $scope.getCurrentUserError)
    }

    $scope.getTenantSuccess = function(response){
        var $countJson=eval(response);
        $scope.tenantIdJson=$countJson;
        $scope.tenantIdLength=$scope.tenantIdJson.length;
        $scope.formData.tenantId = -1;
        $scope.tableloading=false;
    }

    $scope.getTenantError = function() {
        bootbox.alert("error");
    }

    $scope.getTenant = function() {
        var requestUrl = getUrl($location, $suffix.getTenants);
        httpGet($http, requestUrl, null, $scope.getTenantSuccess, $scope.getTenantError);
    }


    //get all controller
    $scope.getAllControllerSuccess = function(response){
        var $countJson=eval(response);
        $scope.nodeJson=$countJson;
        $scope.nodeLength=$scope.nodeJson.length;
        $scope.formData.controllerId = $countJson[0].id;
        $scope.tableloading=false;
    }


    $scope.getAllControllerError = function() {
        bootbox.alert("error");
    }


    $scope.getAllController = function(){
        var requestUrl = getUrl($location, $suffix.controllerAll);
        httpGet($http, requestUrl, null, $scope.getAllControllerSuccess, $scope.getAllControllerError);
    }

    $scope.displayThreshold = function() {
    	if ($scope.formData.autoOrfix == 0)
    	{
    		document.getElementById('pps').disabled = true;
        	document.getElementById('kbps').disabled = true;
    	}
    	else
    	{
    		document.getElementById('pps').disabled = false;
        	document.getElementById('kbps').disabled = false;
    	}
    	
    }
    
    $scope.autodis = function() {
    	document.getElementById('pps').disabled = true;
    	document.getElementById('kbps').disabled = true;
        $scope.warnPpsShow = false;
        $scope.warnKbpsShow = false;
        $scope.formData.pps = "";
        $scope.formData.kbps = "";
        normalCtrl("pps");
        normalCtrl("kbps");
    }
    
    $scope.fixdis = function() {
    	document.getElementById('pps').disabled = false;
    	document.getElementById('kbps').disabled = false;
    }

    $scope.blurCheckDefensetype = function() {
        var defenseType = document.getElementById('defenseType').value;
        if (defenseType == "0") {
            //document.getElementById('guidePort').disabled = true;
            $scope.formData.guidePort = "";
            normalCtrl("guidePort");
            $scope.warnGuideportShow = false;
        } else {
            //document.getElementById('guidePort').disabled = false;
            $scope.formData.guidePort = Guideport;
        }
    }

    $scope.getNodeSuccess = function(response){
        var $editJson=eval(response);
        $scope.nodeJson=$editJson;
        $scope.nodeLength=$scope.nodeJson.length;
        $scope.tableloading=false;
    }
    
    $scope.getNodeError = function() {
        bootbox.alert("error");
    }
    

    $scope.getNode = function(){
        var requestUrl = getUrl($location, $suffix.netNodeAll);
        httpGet($http, requestUrl, null, $scope.getNodeSuccess, $scope.getNodeError);
    }


    $scope.getCleanDevSuccess = function(response){
        var $editJson=eval(response);
        $scope.cleanDevJson=$editJson;
        $scope.cleanDevLength=$scope.cleanDevJson.length;
        $scope.tableloading=false;

        document.getElementById('cleanDevId').disabled = true;
    }

    $scope.getCleanDevError = function() {
        bootbox.alert("error");
    }

    $scope.getCleanDev = function() {
        var requestUrl = getUrl($location, $suffix.cleanDevAll);
        httpGet($http, requestUrl, null, $scope.getCleanDevSuccess, $scope.getCleanDevError);
    }
    
    $scope.goBack = function() {
        goPoList($state);
    }



    /***************检测PO阀值是否为空******************************************************************/
    $scope.blurCheckReinjectionPort = function() {
        $scope.warnReinjectionPortNullShow = false;

        if (!checkNullAndShowPrompt($scope.formData.reinjection_port, "reinjection_port")) {
            $scope.warnReinjectionPortNullShow = true;
            return;
        }
    }

    $scope.blurCheckInterval = function() {
        $scope.warnCheckIntervalShow = false;
        $scope.warnCheckIntervalNumShow = false;
        $scope.warnCheckIntervalRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.check_interval, "check_interval")) {
            $scope.warnCheckIntervalShow = true;
            return;
        }

        if(!isNum($scope.formData.check_interval)) {
            $scope.warnCheckIntervalNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.check_interval, "check_interval")) {
            $scope.warnCheckIntervalRangeShow = true;
        }
    }


    $scope.blurCheckTcpFlood = function() {
        $scope.warnCheckTcpShow = false;
        $scope.warnCheckTcpNumShow = false;
        $scope.warnCheckTcpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.tcp_flood, "tcp_flood")) {
            $scope.warnCheckTcpShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_flood)) {
            $scope.warnCheckTcpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_flood, "tcp_flood")) {
            $scope.warnCheckTcpRangeShow = true;
        }
    }



    $scope.blurCheckTcpSynAck = function() {
        $scope.warnCheckTcpSynAckShow = false;
        $scope.warnCheckTcpSynAckNumShow = false;
        $scope.warnCheckTcpSynAckRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.tcp_synack, "tcp_synack")) {
            $scope.warnCheckTcpSynAckShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_synack)) {
            $scope.warnCheckTcpSynAckNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_synack, "tcp_synack")) {
            $scope.warnCheckTcpSynAckRangeShow = true;
        }
    }
    $scope.blurCheckTcpSYN = function() {
        $scope.warnCheckTcpSynShow = false;
        $scope.warnCheckTcpSynNumShow = false;
        if (!checkNullAndShowPrompt($scope.formData.tcp_syn, "tcp_syn")) {
            $scope.warnCheckTcpSynShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_syn)) {
            $scope.warnCheckTcpSynNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_syn, "tcp_syn")) {
            $scope.warnCheckTcpSynRangeShow = true;
        }
    }


    $scope.blurCheckUDP = function() {
        $scope.warnCheckUdpShow = false;
        $scope.warnCheckUdpNumShow = false;
        $scope.warnCheckUdpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.udp_threshold, "udp_threshold")) {
            $scope.warnCheckUdpShow = true;
            return;
        }

        if(!isNum($scope.formData.udp_threshold)) {
            $scope.warnCheckUdpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.udp_threshold, "udp_threshold")) {
            $scope.warnCheckUdpRangeShow = true;
        }
    }

    $scope.blurCheckICMP = function() {
        $scope.warnCheckIcmpShow =false;
        $scope.warnCheckIcmpNumShow = false;
        $scope.warnCheckIcmpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.icmp_threshold, "icmp_threshold")) {
            $scope.warnCheckIcmpShow = true;
            return;
        }

        if(!isNum($scope.formData.icmp_threshold)) {
            $scope.warnCheckIcmpNumShow = true;
            return;
        }


        if (!checkThresholdRangeFixValue($scope.formData.icmp_threshold, "icmp_threshold")) {
            $scope.warnCheckIcmpRangeShow = true;
        }
    }

    $scope.blurCheckHttp = function() {
        $scope.warnCheckHttpShow = false;
        $scope.warnCheckHttpNumShow = false;
        $scope.warnCheckHttpRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http, "http")) {
            $scope.warnCheckHttpShow = true;
            return;
        }

        if(!isNum($scope.formData.http)) {
            $scope.warnCheckHttpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http, "http")) {
            $scope.warnCheckHttpRangeShow = true;
        }
    }

    $scope.blurCheckHttpRequest = function() {
        $scope.warnCheckHttpRequestShow = false;
        $scope.warnCheckHttpRequestNumShow = false;
        $scope.warnCheckHttpRequestRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http_request, "http_request")) {
            $scope.warnCheckHttpRequestShow = true;
            return;
        }

        if(!isNum($scope.formData.http_request)) {
            $scope.warnCheckHttpRequestNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http_request, "http_request")) {
            $scope.warnCheckHttpRequestRangeShow = true;
        }
    }

    $scope.blurCheckHttpPost = function() {
        $scope.warnCheckHttpPostShow = false;
        $scope.warnCheckHttpPostNumShow = false;
        $scope.warnCheckHttpPostRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http_post, "http_post")) {
            $scope.warnCheckHttpPostShow = true;
            return;
        }

        if(!isNum($scope.formData.http_post)) {
            $scope.warnCheckHttpPostNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http_post, "http_post")) {
            $scope.warnCheckHttpPostRangeShow = true;
        }
    }

    $scope.blurCheckHttpPort = function() {
        $scope.warnCheckHttpPortShow = false;
        $scope.warnHttpPortRangeShow = false;
        $scope.warnCheckHttpPortNumShow = false;

        if (!checkNullAndShowPrompt($scope.formData.http_port, "http_port")) {
            $scope.warnCheckHttpPortShow = true;
            return;
        }

        if(!isNum($scope.formData.http_port)) {
            $scope.warnCheckHttpPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.http_port, "http_port", 1, 65535)) {
            $scope.warnHttpPortRangeShow = true;
            return;
        }
    }


    $scope.blurCheckHttpsRequest = function() {
        $scope.warnCheckHttpsRequestShow = false;
        $scope.warnCheckHttpsRequestNumShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https_request, "https_request")) {
            $scope.warnCheckHttpsRequestShow = true;
            return;
        }

        if(!isNum($scope.formData.https_request)) {
            $scope.warnCheckHttpsRequestNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.https_request, "https_request")) {
            $scope.warnCheckHttpsRequestRangeShow = true;
        }

    }


    $scope.blurCheckHttpsThc = function() {
        $scope.warnCheckHttpsThcShow = false;
        $scope.warnCheckHttpsThcNumShow = false;
        $scope.warnCheckHttpsThcRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https_thc, "https_thc")) {
            $scope.warnCheckHttpsThcShow = true;
            return;
        }

        if(!isNum($scope.formData.https_thc)) {
            $scope.warnCheckHttpsThcNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.https_thc, "https_thc")) {
            $scope.warnCheckHttpsThcRangeShow = true;
        }
    }

    $scope.blurCheckHttpsPort = function() {
        $scope.warnCheckHttpsPortShow = false;
        $scope.warnCheckHttpsPortNumShow = false;
        $scope.warnHttpsPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https_port, "https_port")) {
            $scope.warnCheckHttpsPortShow = true;
            return;
        }

        if(!isNum($scope.formData.https_port)) {
            $scope.warnCheckHttpsPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.https_port, "https_port", 1, 65535)) {
            $scope.warnHttpsPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckDnsReq = function() {
        $scope.warnCheckDnsReqShow = false;
        $scope.warnCheckDnsReqNumShow = false;
        $scope.warnCheckDnsReqRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_req, "dns_req")) {
            $scope.warnCheckDnsReqShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_req)) {
            $scope.warnCheckDnsReqNumShow = true;
            return;
        }


        if (!checkThresholdRangeFixValue($scope.formData.dns_req, "dns_req")) {
            $scope.warnCheckDnsReqRangeShow = true;
        }
    }

    $scope.blurCheckDnsRep = function() {
        $scope.warnCheckDnsRepShow = false;
        $scope.warnCheckDnsRepNumShow = false;
        $scope.warnCheckDnsRepRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_rep, "dns_rep")) {
            $scope.warnCheckDnsRepShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_rep)) {
            $scope.warnCheckDnsRepNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.dns_rep, "dns_rep")) {
            $scope.warnCheckDnsRepRangeShow = true;
        }
    }


    $scope.blurCheckDnsPort = function() {
        $scope.warnCheckDnsPortShow = false;
        $scope.warnCheckDnsPortNumShow = false;
        $scope.warnDnsPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_port, "dns_port")) {
            $scope.warnCheckDnsPortShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_port)) {
            $scope.warnCheckDnsPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.dns_port, "dns_port", 1, 65535)) {
            $scope.warnDnsPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckDnsAbnormal = function() {
        $scope.warnCheckDnsAbnormalShow = false;
        $scope.warnCheckDnsAbnormalNumShow = false;
        if (!checkNullAndShowPrompt($scope.formData.dns_abnormal, "dns_abnormal")) {
            $scope.warnCheckDnsAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_abnormal)) {
            $scope.warnCheckDnsAbnormalNumShow = true;
            return;
        }
    }

    /**************************************************************************************
     * For ntp and snmp
     */

    $scope.blurCheckNtpPort = function() {
        $scope.warnCheckNtpPortShow = false;
        $scope.warnCheckNtpPortNumShow = false;
        $scope.warnCheckNtpPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.ntp_port, "ntp_port")) {
            $scope.warnCheckNtpPortShow = true;
            return;
        }

        if(!isNum($scope.formData.ntp_port)) {
            $scope.warnCheckNtpPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.ntp_port, "ntp_port", 1, 65535)) {
            $scope.warnCheckNtpPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckNtp = function() {
        $scope.warnCheckNtpShow = false;
        $scope.warnCheckNtpNumShow = false;
        $scope.warnCheckNtpRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.ntp, "ntp")) {
            $scope.warnCheckNtpShow = true;
            return;
        }

        if(!isNum($scope.formData.ntp)) {
            $scope.warnCheckNtpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.ntp, "ntp")) {
            $scope.warnCheckNtpRangeShow = true;
        }
    }


    $scope.blurCheckSnmpPort = function() {
        $scope.warnCheckSnmpPortShow = false;
        $scope.warnCheckSnmpPortNumShow = false;
        $scope.warnCheckSnmpPortRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.snmp_port, "snmp_port")) {
            $scope.warnCheckSnmpPortShow = true;
            return;
        }

        if(!isNum($scope.formData.snmp_port)) {
            $scope.warnCheckSnmpPortNumShow = true;
            return;
        }

        if (!checkRangeAdnShowPrompt($scope.formData.snmp_port, "snmp_port", 1, 65535)) {
            $scope.warnCheckSnmpPortRangeShow = true;
            return;
        }
    }

    $scope.blurCheckSnmp = function() {
        $scope.warnCheckSnmpShow = false;
        $scope.warnCheckSnmpNumShow = false;
        $scope.warnCheckSnmpRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.snmp, "snmp")) {
            $scope.warnCheckSnmpShow = true;
            return;
        }

        if(!isNum($scope.formData.snmp)) {
            $scope.warnCheckSnmpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.snmp, "snmp")) {
            $scope.warnCheckSnmpRangeShow = true;
        }
    }


    $scope.blurCheckIpOption = function() {
        $scope.warnCheckIpOptionShow = false;
        $scope.warnCheckIpOptionNumShow = false;
        $scope.warnCheckIpOptionRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.ipoption_threshold, "ipoption_threshold")) {
            $scope.warnCheckIpOptionShow = true;
            return;
        }

        if(!isNum($scope.formData.ipoption_threshold)) {
            $scope.warnCheckIpOptionNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.ipoption_threshold, "ipoption_threshold")) {
            $scope.warnCheckIpOptionRangeShow = true;
        }
    }

    /******************************************************************************************/

    /************************************************************************************************/


    $scope.save = function() {

        if (poTransform($scope.formData.name)=== 0) {
            $scope.warnNameShow = true;
        }

        if (poTransform($scope.formData.ipoption_threshold) == 0) {
            $scope.warnCheckIpOptionShow = true;
        }
/*
        if ($scope.formData.checkDetector == 1) {
            $scope.warnOutputportShow = false;
            $scope.warnReinjectionPortNullShow = false;
            $scope.warnGuideportShow = false;

            if (poTransform($scope.formData.outport) === 0) {
                $scope.warnOutputportShow = true;
            }

            if (poTransform($scope.formData.reinjection_port) === 0) {
                $scope.warnReinjectionPortNullShow = true;
            }

            if (poTransform($scope.formData.guidePort)=== 0) {
                $scope.warnGuideportShow = true;
            }
        }
*/
        if (poTransform($scope.formData.network)=== 0) {
            $scope.warnPnNullShow = true;
        }

        if ($scope.formData.autoOrfix == 1) {
            if ($scope.formData.pps == 0) {
                $scope.warnPpsShow = true;
            }

            if ($scope.formData.kbps == 0) {
                $scope.warnKbpsShow = true;
            }
        }

        /*
        if ($scope.formData.defenseType == 1) {
            if ($scope.formData.guidePort == 0) {
                $scope.warnGuideportShow = true;
            }
        }*/

        if (poTransform($scope.formData.check_interval) == 0) {
            $scope.warnCheckIntervalShow = true;
        }

        if (poTransform($scope.formData.tcp_synack) == 0) {
            $scope.warnCheckTcpSynAckShow = true;
        }

        if (poTransform($scope.formData.tcp_syn) == 0) {
            $scope.warnCheckTcpSynShow = true;
        }

        if (poTransform($scope.formData.udp) == 0) {
            $scope.warnCheckUdpShow = true;
        }

        if (poTransform($scope.formData.icmp) == 0) {
            $scope.warnCheckIcmpShow = true;
        }

        /*if (poTransform($scope.formData.icmp_abnormal) == 0) {
         $scope.warnCheckIcmpAbnormalShow = true;
         }*/

        if (poTransform($scope.formData.http) == 0) {
            $scope.warnCheckHttpShow = true;
        }

        if (poTransform($scope.formData.http_request) == 0) {
            $scope.warnCheckHttpRequestShow = true;
        }

        if (poTransform($scope.formData.http_post) == 0) {
            $scope.warnCheckHttpPostShow = true;
        }

        if (poTransform($scope.formData.http_port) == 0) {
            $scope.warnCheckHttpPortShow = true;
        }

        if (poTransform($scope.formData.https_request) == 0) {
            $scope.warnCheckHttpsRequestShow = true;
        }

        if (poTransform($scope.formData.https_thc) == 0) {
            $scope.warnCheckHttpsThcShow = true;
        }


        if (poTransform($scope.formData.https_port) == 0) {
            $scope.warnCheckHttpsPortShow = true;
        }

        if (poTransform($scope.formData.dns_req) == 0) {
            $scope.warnCheckDnsReqShow = true;
        }

        if (poTransform($scope.formData.dns_rep) == 0) {
            $scope.warnCheckDnsRepShow = true;
        }

        if (poTransform($scope.formData.dns_abnormal) == 0) {
            $scope.warnCheckDnsAbnormalShow = true;
        }

        if (poTransform($scope.formData.dns_port) == 0) {
            $scope.warnCheckDnsPortShow = true;
        }


        if (($scope.warnNameShow === true)
            || ($scope.warnSameShow === true)
            || ($scope.warnOutputportShow === true)
            || ($scope.warnGuideportShow === true)
            || ($scope.warnPnShow === true)
            || ($scope.warnPnNullShow === true)
            || ($scope.warnPpsShow === true)
            || ($scope.warnPpsTypeShow == true)
            || ($scope.warnKbpsShow === true)
            || ($scope.warnKpsTypeShow === true)
            || ($scope.warnCheckNtpPortShow === true)
            || ($scope.warnCheckSnmpPortShow === true)
            || ($scope.warnCheckNtpShow === true)
            || ($scope.warnCheckSnmpShow === true)
            || ($scope.warnCheckNtpPortNumShow === true)
            || ($scope.warnCheckSnmpPortNumShow === true)
            || ($scope.warnCheckNtpNumShow === true)
            || ($scope.warnCheckIpOptionShow === true)
            || ($scope.warnCheckIpOptionNumShow === true)
            || ($scope.warnCheckSnmpNumShow === true)) {
            return;
        }

        if ((($scope.warnReinjectionPortNullShow === true)
            || ($scope.warnCheckIntervalRangeShow === true)
            || ($scope.warnCheckTcpSynRangeShow === true)
            || ($scope.warnCheckTcpSynAckRangeShow === true)
            || ($scope.warnCheckUdpRangeShow === true)
            || ($scope.warnCheckIcmpRangeShow === true)
            || ($scope.warnCheckHttpRequestRangeShow === true)
            || ($scope.warnCheckHttpPortShow === true)
            || ($scope.warnCheckHttpsRequestRangeShow === true)
            || ($scope.warnCheckHttpsPortShow === true)
            || ($scope.warnCheckDnsReqRangeShow === true)
            || ($scope.warnCheckDnsRepRangeShow === true)
            || ($scope.warnCheckDnsPortShow === true)
            || ($scope.warnCheckIpOptionRangeShow === true)
            || ($scope.warnCheckNtpPortRangeShow === true)
            || ($scope.warnCheckSnmpPortRangeShow === true)
            || ($scope.warnCheckNtpRangeShow === true)
            || ($scope.warnCheckSnmpRangeShow === true))) {
            return;
        }


        $scope.formData.tcp = 1;
        $scope.formData.udp = 1;
        $scope.formData.icmp = 1;
        $scope.formData.any = 1;
        $scope.formData.defenseType = defenseTypeTransform($scope.formData.defenseType);

        /*
        if ($scope.formData.defenseType == "LIMIT_RATE") {
            $scope.formData.guidePort = "";
        } else {
            if ($scope.formData.guidePort == 0) {
                $scope.warnGuideportShow = true;
            }
        }
        */
        $scope.formData.defenseType = "GUIDE_FLOW";

        if ($scope.formData.iface_category == 1) {
            $scope.formData.cleanInport = 1;
            $scope.formData.cleanOutport = 2;
        } else {
            $scope.formData.cleanInport = 3;
            $scope.formData.cleanOutport = 4;
        }

        var requestUrl = getUrl($location, $suffix.po);
        httpPut($http, requestUrl, {"id":$scope.formData.id,"name":$scope.formData.name,
                "network":$scope.formData.network,"tenantId":$scope.formData.tenantId,
                "cleanDevId":$scope.formData.cleanDevId, "cleanInport": $scope.formData.cleanInport ,
                "controllerId":$scope.formData.controllerId,
                "cleanOutport": $scope.formData.cleanOutport, "inport":$scope.formData.inport,
                "outport":$scope.formData.outport, "reinjectionPort":$scope.formData.reinjection_port,
                "defenseType":$scope.formData.defenseType,
                "guidePort":$scope.formData.guidePort, "netnode_id":$scope.formData.netnode_id,
                "ipOption":$scope.formData.ipoption_threshold, "icmpRedirect":$scope.formData.icmp_redirect,
                "httpSrcAuth":$scope.formData.http_src_auth,"tcpFirstSyn":$scope.formData.tcp_first,
                "tcp":$scope.formData.tcp,"udp":$scope.formData.udp,"icmp":$scope.formData.icmp,
                "any":$scope.formData.any,"autoOrfix":$scope.formData.autoOrfix,"pps":$scope.formData.pps,
                "kbps":$scope.formData.kbps,"check_interval":$scope.formData.check_interval,
                "tcp_syn":$scope.formData.tcp_syn, "tcp_synack":$scope.formData.tcp_synack,
                "udp_threshold":$scope.formData.udp_threshold, "icmp_threshold":$scope.formData.icmp_threshold,
                "http_port":$scope.formData.http_port, "http_request":$scope.formData.http_request,
                "https_request":$scope.formData.https_request, "https_port":$scope.formData.https_port,
                "dns_request":$scope.formData.dns_req,"dns_reply":$scope.formData.dns_rep,
                "dns_port":$scope.formData.dns_port,
                "ntp_port":$scope.formData.ntp_port, "ntp":$scope.formData.ntp,
                "snmp_port":$scope.formData.snmp_port, "snmp":$scope.formData.snmp},$scope.success, $scope.error);

    }

    $scope.putPOToForm = function(response) {
        var $countJson=eval(response);

        $scope.formData.id = $countJson.id;
        $scope.formData.name = $countJson.name;
        $scope.formData.ipType = $countJson.ipType;
        $scope.formData.network = $countJson.network;
        $scope.formData.cleanDevId = $countJson.cleanDevId;
        $scope.formData.tenantId = $countJson.tenantId;
        $scope.formData.cleanOutport = $countJson.cleanOutport;
        $scope.formData.cleanInport = $countJson.cleanInport;
        $scope.formData.reinjection_port = $countJson.reinjectionPort;
        $scope.formData.icmp_redirect = $countJson.icmpRedirect;

        $scope.formData.http_src_auth = $countJson.httpSrcAuth;
        $scope.formData.ipoption_threshold = $countJson.ipOption;

        if ($scope.formData.cleanInport == 1) {
            $scope.formData.iface_category = 1;
        } else {
            $scope.formData.iface_category = 2;
        }

        $scope.formData.inport = $countJson.inport;
        $scope.formData.outport = $countJson.outport;
        if ($countJson.defenseType == "LIMIT_RATE") {
            $scope.formData.defenseType = 0;
            //document.getElementById('guidePort').disabled = true;
        } else if ($countJson.defenseType == "GUIDE_FLOW"){
            $scope.formData.defenseType = 1;
            $scope.formData.guidePort = $countJson.guidePort;
            Guideport = $countJson.guidePort;
            //document.getElementById('guidePort').disabled = false;
        } else {
            $scope.formData.defenseType = 0;
        }

        $scope.formData.learn_status = $countJson.learn_status;
        // $scope.formData.netnode_swid = $countJson.netnode_swid;
        //$scope.formData.netnode_name = $countJson.netnode_name;
        $scope.formData.netnode_id = $countJson.netnode_id;
        $scope.formData.controllerId = $countJson.controllerId;
        if ($scope.formData.controllerId == 0) {
            //$scope.formData.checkDetector = false;
            $scope.formData.mode = 0;
            $scope.detectorShow = false;
            document.getElementById('bypass_mode').disabled = true;
        } else {
            //$scope.formData.checkDetector = true;
            $scope.formData.mode = 1;
            $scope.detectorShow = true;
            document.getElementById('tadenm_mode').disabled = true;
            document.getElementById('controllerId').disabled = true;
        }

        $scope.formData.cleanDevId = $countJson.cleanDevId;
        $scope.formData.tcp = repoTransform($countJson.tcp);
        $scope.formData.udp = repoTransform($countJson.udp);
        $scope.formData.icmp = repoTransform($countJson.icmp);
        $scope.formData.any = repoTransform($countJson.any);
        $scope.formData.autoOrfix = $countJson.autoOrfix;
        if ($scope.formData.autoOrfix == 1) {
            $scope.formData.pps = $countJson.pps;
            $scope.formData.kbps = $countJson.kbps;
        } else {
            $scope.formData.pps = "";
            $scope.formData.kbps = "";
        }

        $scope.formData.tcp_syn = $countJson.tcp_syn;
        $scope.formData.tcp_synack = $countJson.tcp_synack;
        $scope.formData.udp_threshold = $countJson.udp_threshold;
        $scope.formData.icmp_threshold = $countJson.icmp_threshold;
        $scope.formData.check_interval = $countJson.check_interval;
        $scope.formData.http_request = $countJson.http_request;
        $scope.formData.http_port = $countJson.http_port;
        $scope.formData.https_request = $countJson.https_request;
        $scope.formData.https_port = $countJson.https_port;
        $scope.formData.dns_req = $countJson.dns_request;
        $scope.formData.dns_rep = $countJson.dns_reply;
        $scope.formData.dns_port = $countJson.dns_port;

        $scope.formData.ntp = $countJson.ntp;
        $scope.formData.ntp_port = $countJson.ntp_port;
        $scope.formData.snmp = $countJson.snmp;
        $scope.formData.snmp_port = $countJson.snmp_port;


        //document.getElementById('cleanPort').disabled = true;
        document.getElementById('network').disabled = true;
        document.getElementById('tenantId').disabled = true;
        //document.getElementById('checkDetector').disabled = true;
        //$scope.displayThreshold();
    }

    $scope.getPOError = function(response) {
        $scope.getErrShow = true;
    }

    $scope.putForm = function(id) {
        var requestUrl = getUrl($location, $suffix.po + '/' + id);

        httpGet($http, requestUrl, null, $scope.putPOToForm, $scope.getPOError);
    }

    $scope.formData = {};

    $scope.defensetype = defensetypelist();

    $scope.getCleanDev();
   // $scope.getNode();
    $scope.getCurrentUser();
    $scope.getTenant();
    $scope.getAllController();
    $scope.putForm($stateParams.id);


    $scope.blurCheckName = function() {
        $scope.warnNameShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.formData.name, "name")) {
            $scope.warnNameShow = true;
            return;
        }

        $scope.checkPO($scope.formData.id, $scope.formData.name);
    }

    $scope.check_tadem = function () {
        $scope.detectorShow = false;
    }

    $scope.check_bypass = function () {
        $scope.detectorShow = true;
    }

    $scope.blurCheckOutputport = function() {
        $scope.warnOutputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.outport, "outport")) {
            $scope.warnOutputportShow = true;
            return;
        }
    }

    $scope.blurCheckCleanOutputport = function() {
        $scope.warnCleanOutputportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.cleanOutport, "cleanOutport")) {
            $scope.warnCleanOutputportShow = true;
            return;
        }
    }

    //$scope.clickCheckShowDetector = function() {
    //    $scope.detectorShow = $scope.formData.checkDetector;
    //}


    $scope.checkPONetworkSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("network");
            $scope.warnSameNetWorkShow = true;
        } else {
            normalCtrl("network");
            $scope.warnSameNetWorkShow = false;
        }
    }

    $scope.checkPONetworkError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkPONetwork = function($id, $network) {
        $scope.warnSameNetWorkShow = false;
        if (($network === undefined) || ($network === '')) {
            return;
        }

        var requestUrl = getUrl($location, "rest/policy/isExistPONetwork");

        httpPost($http, requestUrl, {"id":$id, "network":$network}, $scope.checkPONetworkSuccess, $scope.checkPONetworkError);
    }

    $scope.blurCheckPn = function() {
        $scope.warnPnShow =false;
        if (!checkNullAndShowPrompt($scope.formData.network, "network")) {
            $scope.warnPnNullShow = true;
            return;
        }

        /**判断ipv4输入格式的正则表达式**/
        var regex = /^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\/(([0-9])|([1-2][0-9])|(3[0-2]))$/;

        if(!regex.test($scope.formData.network)){
            $scope.warnPnShow = true;
            return ;
        }

        $scope.checkPONetwork($scope.formData.id, $scope.formData.network);
    }

    $scope.blurCheckGuideport = function() {
        $scope.warnGuideportShow =false;
        if (!checkNullAndShowPrompt($scope.formData.guidePort, "guidePort")) {
            $scope.warnGuideportShow = true;
            return;
        }
    }

    $scope.blurCheckPPS = function() {
        $scope.warnPpsShow =false;
        $scope.warnPpsTypeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.pps, "pps")) {
            $scope.warnPpsShow = true;
            return;
        }

        if (!checkMinAndShowPrompt($scope.formData.pps, "pps", 0)) {
            $scope.warnPpsTypeShow = true;
            return;
        }
    }

    $scope.blurCheckKbps = function() {
        $scope.warnKbpsShow =false;
        $scope.warnKpsTypeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.kbps, "kbps")) {
            $scope.warnKbpsShow = true;
            return;
        }
        if (!checkMinAndShowPrompt($scope.formData.kbps, "kbps", 0)) {
            $scope.warnKpsTypeShow = true;
            return;
        }
    }

    $scope.checkPOSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("name");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("name");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkPOError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkPO = function($id,$name) {
        $scope.warnNameShow = false;
        if (($name === undefined) || ($name === '')) {
            return;
        }


        var requestUrl = getUrl($location, "rest/policy/isExistPO");
        httpPost($http, requestUrl, {"name":$name, "id":$id}, $scope.checkPOSuccess, $scope.checkPOError);

        //isExistSamePo($http, $location, $id, $name, $scope.checkPOSuccess, $scope.checkPOError)
    }
}
]);
