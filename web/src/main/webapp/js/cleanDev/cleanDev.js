

function goList(state) {
    state.go('app.cleandev');
}

function cleanDevTransform(val) {
    if (val) {
        val = 1;
    } else {
        val = 0;
    }

    return val;
}

function cleanDirectionTransform(val) {
    if (val == "0") {
        val = "uni-direction";
    } else {
        val = "bi-direction";
    }

    return val;
}

//function cleandirectionlist() {
//    var cleandirection = new Array();
//    cleandirection.push({'type':0, 'name':'uni-direction'});
//    cleandirection.push({'type':1, 'name':'bi-direction'});
//
//    return cleandirection;
//}

function isExistSameCleanDev(http, location, id, ip, successCallback, errorCallback) {
    var requestUrl = getUrl(location, 'rest/policy/isExistCleanDev/' + id + '/' + ip);
    httpGet(http, requestUrl, null, successCallback, errorCallback);
}

app.controller('cleandevController', ['$scope', '$http','$translate', '$localStorage', '$location', '$state', '$timeout', '$animate','suffix', function ($scope, $http, $translate, $alerts, $location, $state, $timeout, $animate, $suffix) {

    $scope.getcleanDevSuccess = function(response){
        var $countJson=eval(response);
        $scope.cleanDevTableJson=$countJson.cleandevs;
        $scope.cleanDevTableLength=$scope.cleanDevTableJson.length;
        $scope.tableloading=false;

        pageUpdate($countJson.total, $scope.page);
    }


    $scope.getcleanDevError = function() {
        $scope.tableloading=false;
        $scope.getErrShow = true;
    }

    $scope.getCleanDevList = function(page){
        var requestUrl = getUrl($location, $suffix.cleanDev + '?page=' + page);
        httpGet($http, requestUrl, null, $scope.getcleanDevSuccess, $scope.getcleanDevError);
    }


    $scope.add = function(id) {
        $state.go('app.cleandevAdd');
    }

    $scope.edit = function(id) {
        $state.go('app.cleandevEdit', {id: id});
    }

    $scope.delcleanDevSuccess = function(response) {
        $scope.chkAll = false;
        $scope.listCheck.clearChecked();
        $scope.getCleanDevList($scope.page);
    }

    $scope.delcleanDevError = function() {

    }

    $scope.del = function(id) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }

            var requestUrl = getUrl($location, $suffix.cleanDev + '/' + id);
            httpDelete($http, requestUrl, null, $scope.delcleanDevSuccess, $scope.delcleanDevError);
        })

    }

    $scope.pageGetCleanDevList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getCleanDevList($scope.page);
    }

    $scope.toggleCheckAll = function() {
        $scope.listCheck.toggleCheckAll($scope.chkAll, $scope.cleanDevTableJson);
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

            var requestUrl = getUrl($location, $suffix.policy + '/batchDelCleanDev');
            httpPost($http, requestUrl, $scope.listCheck.getChecked(), $scope.delcleanDevSuccess, $scope.delcleanDevError);
        })
    }

    $scope.tableloading = true;
    $scope.visible=false;
    $scope.chkAll = false;
    $scope.listCheck = new ListCheck();

    $scope.page = 1;
    pageInit($scope.pageGetCleanDevList);
    $scope.getCleanDevList($scope.page);
}
]);

app.controller('cleandevAddController', ['$scope', '$http', '$location', '$state','suffix', function ($scope, $http, $location, $state, $suffix) {
    $scope.id = '';
    $scope.formData = {};
    var direction = "uni-direction";

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

    $scope.getCleanDevSuccess = function(response){
        var $countJson=eval(response);
        $scope.cleanDevTableJson = $countJson;
        $scope.cleanDevTableLength = $scope.cleanDevTableJson.length;
        $scope.tableloading=false;
        $scope.formData.id = $countJson[0].id;
    }


    $scope.getCleanDevError = function() {
        $scope.getErrShow = true;
    }

    $scope.getCleanDevList = function(){
        var requestUrl = getUrl($location, $suffix.cleanDevAll);

        httpGet($http, requestUrl, null, $scope.getCleanDevSuccess, $scope.getCleanDevError);
    }

    $scope.blurCheckIp = function() {
        $scope.warnAddrShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.formData.ip, "ip")) {
            $scope.warnAddrShow = true;
            return;
        }

        $scope.checkCleanDev($scope.formData.ip);
    }

    $scope.unidirection = function() {
        $scope.formData.direction = 0;
        direction = "单向清洗";
    }

    $scope.bidirection = function() {
        $scope.formData.direction = 1;
        direction = "双向清洗";
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


    $scope.blurCheckTcpAbnormal = function() {
        $scope.warnCheckTcpAbnormalShow = false;
        $scope.warnCheckTcpAbnormalNumShow = false;
        $scope.warnCheckTcpAbnormalRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.tcp_abnormal, "tcp_abnormal")) {
            $scope.warnCheckTcpAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_abnormal)) {
            $scope.warnCheckTcpAbnormalNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_abnormal, "tcp_abnormal")) {
            $scope.warnCheckTcpAbnormalRangeShow = true;
        }
    }

    $scope.blurCheckUDP = function() {
        $scope.warnCheckUdpShow = false;
        $scope.warnCheckUdpNumShow = false;
        $scope.warnCheckUdpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.udp, "udp")) {
            $scope.warnCheckUdpShow = true;
            return;
        }

        if(!isNum($scope.formData.udp)) {
            $scope.warnCheckUdpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.udp, "udp")) {
            $scope.warnCheckUdpRangeShow = true;
        }
    }

    $scope.blurCheckICMP = function() {
        $scope.warnCheckIcmpShow =false;
        $scope.warnCheckIcmpNumShow = false;
        $scope.warnCheckIcmpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.icmp, "icmp")) {
            $scope.warnCheckIcmpShow = true;
            return;
        }

        if(!isNum($scope.formData.icmp)) {
            $scope.warnCheckIcmpNumShow = true;
            return;
        }


        if (!checkThresholdRangeFixValue($scope.formData.icmp, "icmp")) {
            $scope.warnCheckIcmpRangeShow = true;
        }
    }

    /*$scope.blurCheckICMPAbnormal = function() {
        $scope.warnCheckIcmpAbnormalShow = false;
        $scope.warnCheckIcmpAbnormalNumShow = false;
        if (!checkNullAndShowPrompt($scope.formData.icmp_abnormal, "icmp_abnormal")) {
            $scope.warnCheckIcmpAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.icmp_abnormal)) {
            $scope.warnCheckIcmpAbnormalNumShow = true;
            return;
        }
    }*/

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

    $scope.blurCheckHttpHeader = function() {
        $scope.warnCheckHttpHeaderShow = false;
        $scope.warnCheckHttpHeaderNumShow = false;
        $scope.warnCheckHttpHeaderRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.http_header, "http_header")) {
            $scope.warnCheckHttpHeaderShow = true;
            return;
        }

        if(!isNum($scope.formData.http_header)) {
            $scope.warnCheckHttpHeaderNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http_header, "http_header")) {
            $scope.warnCheckHttpHeaderRangeShow = true;
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


    $scope.blurCheckHttps = function() {
        $scope.warnCheckHttpsShow = false;
        $scope.warnCheckHttpsNumShow = false;
        $scope.warnCheckHttpsRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https, "https")) {
            $scope.warnCheckHttpsShow = true;
            return;
        }

        if(!isNum($scope.formData.https)) {
            $scope.warnCheckHttpsNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.https, "https")) {
            $scope.warnCheckHttpsRangeShow = true;
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
        $scope.warnCheckDnsAbnormalRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_abnormal, "dns_abnormal")) {
            $scope.warnCheckDnsAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_abnormal)) {
            $scope.warnCheckDnsAbnormalNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.dns_abnormal, "dns_abnormal")) {
            $scope.warnCheckDnsAbnormalRangeShow = true;
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
    /******************************************************************************************/


    $scope.checkCleanDevSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("ip");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("ip");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkCleanDevError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkCleanDev = function($ip) {
        $scope.warnSameShow = false;
        if (($ip === undefined) || ($ip === '')) {
            return;
        }

        isExistSameCleanDev($http, $location, 0, $ip, $scope.checkCleanDevSuccess, $scope.checkCleanDevError)
    }


    $scope.getCleanDevList();

    $scope.save = function() {

        if (cleanDevTransform($scope.formData.ip) == 0) {
            $scope.warnAddrShow = true;
        }

        if (cleanDevTransform($scope.formData.check_interval) == 0) {
            $scope.warnCheckIntervalShow = true;
        }

        if (cleanDevTransform($scope.formData.tcp_flood) == 0) {
            $scope.warnCheckTcpShow = true;
        }

        if (cleanDevTransform($scope.formData.tcp_abnormal) == 0) {
            $scope.warnCheckTcpSynAckShow = true;
        }

        if (cleanDevTransform($scope.formData.udp) == 0) {
            $scope.warnCheckUdpShow = true;
        }

        if (cleanDevTransform($scope.formData.icmp) == 0) {
            $scope.warnCheckIcmpShow = true;
        }

        /*if (cleanDevTransform($scope.formData.icmp_abnormal) == 0) {
            $scope.warnCheckIcmpAbnormalShow = true;
        }*/

        if (cleanDevTransform($scope.formData.http) == 0) {
            $scope.warnCheckHttpShow = true;
        }

        if (cleanDevTransform($scope.formData.http_header) == 0) {
            $scope.warnCheckHttpRequestShow = true;
        }

        if (cleanDevTransform($scope.formData.http_post) == 0) {
            $scope.warnCheckHttpPostShow = true;
        }

        if (cleanDevTransform($scope.formData.http_port) == 0) {
            $scope.warnCheckHttpPortShow = true;
        }

        if (cleanDevTransform($scope.formData.https) == 0) {
            $scope.warnCheckHttpsShow = true;
        }

        if (cleanDevTransform($scope.formData.https_thc) == 0) {
            $scope.warnCheckHttpsThcShow = true;
        }


        if (cleanDevTransform($scope.formData.https_port) == 0) {
            $scope.warnCheckHttpsPortShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_req) == 0) {
            $scope.warnCheckDnsReqShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_rep) == 0) {
            $scope.warnCheckDnsRepShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_abnormal) == 0) {
            $scope.warnCheckDnsAbnormalShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_port) == 0) {
            $scope.warnCheckDnsPortShow = true;
        }

        if (cleanDevTransform($scope.formData.ntp) == 0) {
            $scope.warnCheckNtpShow = true;
        }

        if (cleanDevTransform($scope.formData.ntp_port) == 0) {
            $scope.warnCheckNtpPortShow = true;
        }

        if (cleanDevTransform($scope.formData.snmp) == 0) {
            $scope.warnCheckSnmpShow = true;
        }

        if (cleanDevTransform($scope.formData.snmp_port) == 0) {
            $scope.warnCheckSnmpPortShow = true;
        }


        if (($scope.warnAddrShow === true)
            || ($scope.warnCheckIntervalShow === true)
            || ($scope.warnCheckTcpShow === true)
            || ($scope.warnCheckTcpSynAckShow === true)
            || ($scope.warnCheckUdpShow === true)
            || ($scope.warnCheckIcmpShow === true)
            //|| ($scope.warnCheckIcmpAbnormalShow === true)
            || ($scope.warnCheckHttpShow === true)
            || ($scope.warnCheckHttpRequestShow === true)
            || ($scope.warnCheckHttpPortShow === true)
            || ($scope.warnCheckHttpPostShow === true)
            || ($scope.warnCheckHttpsShow === true)
            || ($scope.warnCheckHttpsThcShow === true)
            || ($scope.warnCheckHttpsPortShow === true)
            || ($scope.warnCheckDnsReqShow === true)
            || ($scope.warnCheckDnsRepShow === true)
            || ($scope.warnCheckDnsAbnormalShow === true)
            || ($scope.warnCheckDnsPortShow === true)
            || ($scope.warnCheckNtpPortShow === true)
            || ($scope.warnCheckSnmpPortShow === true)
            || ($scope.warnCheckNtpShow === true)
            || ($scope.warnCheckSnmpShow === true)) {
            return;
        }

        if (($scope.warnSameShow === true)
            || ($scope.warnCheckIntervalNumShow === true)
            || ($scope.warnCheckTcpNumShow === true)
            || ($scope.warnCheckTcpAbnormalNumShow === true)
            || ($scope.warnCheckUdpNumShow === true)
            || ($scope.warnCheckIcmpNumShow === true)
            || ($scope.warnCheckHttpNumShow === true)
            || ($scope.warnCheckHttpHeaderNumShow === true)
            || ($scope.warnCheckHttpPortNumShow === true)
            || ($scope.warnCheckHttpPostNumShow === true)
            || ($scope.warnCheckHttpsNumShow === true)
            || ($scope.warnCheckHttpsThcNumShow === true)
            || ($scope.warnCheckHttpsPortNumShow === true)
            || ($scope.warnCheckDnsReqNumShow === true)
            || ($scope.warnCheckDnsRepNumShow === true)
            || ($scope.warnCheckDnsAbnormalNumShow === true)
            || ($scope.warnCheckDnsPortNumShow === true)
            || ($scope.warnCheckNtpPortNumShow === true)
            || ($scope.warnCheckSnmpPortNumShow === true)
            || ($scope.warnCheckNtpNumShow === true)
            || ($scope.warnCheckSnmpNumShow === true)) {
            return;
        }

        if (($scope.warnCheckIntervalRangeShow === true)
            || ($scope.warnCheckTcpRangeShow === true)
            || ($scope.warnCheckTcpAbnormalRangeShow === true)
            || ($scope.warnCheckUdpRangeShow === true)
            || ($scope.warnCheckIcmpRangeShow === true)
            || ($scope.warnCheckHttpRangeShow === true)
            || ($scope.warnCheckHttpHeaderRangeShow === true)
            || ($scope.warnHttpPortRangeShow === true)
            || ($scope.warnCheckHttpPostRangeShow === true)
            || ($scope.warnCheckHttpsRangeShow === true)
            || ($scope.warnCheckHttpsThcRangeShow === true)
            || ($scope.warnHttpsPortRangeShow === true)
            || ($scope.warnCheckDnsReqRangeShow === true)
            || ($scope.warnCheckDnsRepRangeShow === true)
            || ($scope.warnCheckDnsAbnormalRangeShow === true)
            || ($scope.warnDnsPortRangeShow === true)
            || ($scope.warnCheckNtpPortRangeShow === true)
            || ($scope.warnCheckSnmpPortRangeShow === true)
            || ($scope.warnCheckNtpRangeShow === true)
            || ($scope.warnCheckSnmpRangeShow === true)) {
            return;
        }

        var requestUrl = getUrl($location, $suffix.cleanDev);

        httpPost($http, requestUrl, {"ip":$scope.formData.ip,"direct":direction,
                "tcp_first":$scope.formData.tcp_first,"check_interval":$scope.formData.check_interval,
                "tcp":$scope.formData.tcp_flood, "tcp_abnormal":$scope.formData.tcp_abnormal,
                "udp":$scope.formData.udp, "icmp":$scope.formData.icmp,//"icmp_abnormal":$scope.formData.icmp_abnormal,
                "http":$scope.formData.http, "http_port":$scope.formData.http_port,
                "http_header":$scope.formData.http_header, "http_post":$scope.formData.http_post,
                "https":$scope.formData.https, "https_port":$scope.formData.https_port,
                "https_thc":$scope.formData.https_thc, "dns_request":$scope.formData.dns_req,
                "dns_reply":$scope.formData.dns_rep, "dns_abnormal":$scope.formData.dns_abnormal,
                "dns_port":$scope.formData.dns_port,
                "ntp_port":$scope.formData.ntp_port, "ntp":$scope.formData.ntp,
                "snmp_port":$scope.formData.snmp_port, "snmp":$scope.formData.snmp},
                $scope.success, $scope.error);
    }

    $scope.formData.direction = 0;
    $scope.formData.check_interval = 2;
    $scope.formData.http_port = 80;
    $scope.formData.https_port = 443;
    $scope.formData.dns_port = 53;

    $scope.formData.tcp_flood = 2000;
    $scope.formData.tcp_abnormal = 500;
    $scope.formData.udp = 2000;
    $scope.formData.icmp = 2000;
    //$scope.formData.icmp_abnormal = 500;
    $scope.formData.http = 200;
    $scope.formData.http_header = 10;
    $scope.formData.http_post = 10;
    $scope.formData.https = 40;
    $scope.formData.https_thc = 40;
    $scope.formData.dns_req = 20;
    $scope.formData.dns_rep = 2000;
    $scope.formData.dns_abnormal = 100;
    $scope.formData.ntp = 100;
    $scope.formData.ntp_port = 123;
    $scope.formData.snmp = 100;
    $scope.formData.snmp_port = 161;
}
]);

app.controller('cleanDevEditController', ['$scope','$http','$localStorage', '$location',  '$state','$stateParams', 'suffix', function ($scope, $http,$alerts, $location, $state, $stateParams, $suffix) {
    //$scope.id = $stateParams.id;
    $scope.formData = {};
    $scope.cleanDevTableJson = {};
    var direction = "";

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

    $scope.getCleanDevSuccess = function(response){
        var $countJson=eval(response);
        $scope.cleanDevTableJson=$countJson;
        $scope.cleanDevTableLength=$scope.cleanDevTableJson.length;
        $scope.tableloading=false;
    }


    $scope.getCleanDevError = function() {
        bootbox.alert("CleanDev Get CleanDev information error");
    }

    $scope.getCleanDevList = function(){
        var requestUrl = getUrl($location, $suffix.cleanDevAll);

        httpGet($http, requestUrl, null, $scope.getCleanDevSuccess, $scope.getCleanDevError);
    }

    $scope.checkCleanDevSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("ip");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("ip");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkCleanDevError = function (response, status) {
        $scope.getErrShow = true;
    }

    $scope.checkCleanDev = function($id, $ip) {
        $scope.warnNameShow = false;
        if (($id === undefined) || ($ip === '')) {
            return;
        }

        isExistSameCleanDev($http, $location, $id, $ip, $scope.checkCleanDevSuccess, $scope.checkCleanDevError);
    }

    $scope.getCleanDevList();

    $scope.save = function() {

        if (cleanDevTransform($scope.formData.ip) == 0) {
            $scope.warnAddrShow = true;
        }

        if (cleanDevTransform($scope.formData.check_interval) == 0) {
            $scope.warnCheckIntervalShow = true;
        }

        if (cleanDevTransform($scope.formData.tcp_flood) == 0) {
            $scope.warnCheckTcpShow = true;
        }

        if (cleanDevTransform($scope.formData.tcp_abnormal) == 0) {
            $scope.warnCheckTcpSynAckShow = true;
        }

        if (cleanDevTransform($scope.formData.udp) == 0) {
            $scope.warnCheckUdpShow = true;
        }

        if (cleanDevTransform($scope.formData.icmp) == 0) {
            $scope.warnCheckIcmpShow = true;
        }

       /* if (cleanDevTransform($scope.formData.icmp_abnormal) == 0) {
            $scope.warnCheckIcmpAbnormalShow = true;
        }*/

        if (cleanDevTransform($scope.formData.http) == 0) {
            $scope.warnCheckHttpShow = true;
        }

        if (cleanDevTransform($scope.formData.http_header) == 0) {
            $scope.warnCheckHttpRequestShow = true;
        }

        if (cleanDevTransform($scope.formData.http_post) == 0) {
            $scope.warnCheckHttpPostShow = true;
        }

        if (cleanDevTransform($scope.formData.http_port) == 0) {
            $scope.warnCheckHttpPortShow = true;
        }

        if (cleanDevTransform($scope.formData.https) == 0) {
            $scope.warnCheckHttpsShow = true;
        }

        if (cleanDevTransform($scope.formData.https_thc) == 0) {
            $scope.warnCheckHttpsThcShow = true;
        }


        if (cleanDevTransform($scope.formData.https_port) == 0) {
            $scope.warnCheckHttpsPortShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_req) == 0) {
            $scope.warnCheckDnsReqShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_rep) == 0) {
            $scope.warnCheckDnsRepShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_abnormal) == 0) {
            $scope.warnCheckDnsAbnormalShow = true;
        }

        if (cleanDevTransform($scope.formData.dns_port) == 0) {
            $scope.warnCheckDnsPortShow = true;
        }

        if (cleanDevTransform($scope.formData.ntp) == 0) {
            $scope.warnCheckNtpShow = true;
        }

        if (cleanDevTransform($scope.formData.ntp_port) == 0) {
            $scope.warnCheckNtpPortShow = true;
        }

        if (cleanDevTransform($scope.formData.snmp) == 0) {
            $scope.warnCheckSnmpShow = true;
        }

        if (cleanDevTransform($scope.formData.snmp_port) == 0) {
            $scope.warnCheckSnmpPortShow = true;
        }


        if (($scope.warnAddrShow === true)
            || ($scope.warnCheckIntervalShow === true)
            || ($scope.warnCheckTcpShow === true)
            || ($scope.warnCheckTcpSynAckShow === true)
            || ($scope.warnCheckUdpShow === true)
            || ($scope.warnCheckIcmpShow === true)
            //|| ($scope.warnCheckIcmpAbnormalShow === true)
            || ($scope.warnCheckHttpShow === true)
            || ($scope.warnCheckHttpRequestShow === true)
            || ($scope.warnCheckHttpPortShow === true)
            || ($scope.warnCheckHttpPostShow === true)
            || ($scope.warnCheckHttpsShow === true)
            || ($scope.warnCheckHttpsThcShow === true)
            || ($scope.warnCheckHttpsPortShow === true)
            || ($scope.warnCheckDnsReqShow === true)
            || ($scope.warnCheckDnsRepShow === true)
            || ($scope.warnCheckDnsAbnormalShow === true)
            || ($scope.warnCheckNtpPortShow === true)
            || ($scope.warnCheckSnmpPortShow === true)
            || ($scope.warnCheckNtpShow === true)
            || ($scope.warnCheckSnmpShow === true)) {
            return;
        }

        if (($scope.warnSameShow === true)
            || ($scope.warnCheckIntervalNumShow === true)
            || ($scope.warnCheckTcpNumShow === true)
            || ($scope.warnCheckTcpAbnormalNumShow === true)
            || ($scope.warnCheckUdpNumShow === true)
            || ($scope.warnCheckIcmpNumShow === true)
            || ($scope.warnCheckHttpNumShow === true)
            || ($scope.warnCheckHttpHeaderNumShow === true)
            || ($scope.warnCheckHttpPortNumShow === true)
            || ($scope.warnCheckHttpPostNumShow === true)
            || ($scope.warnCheckHttpsNumShow === true)
            || ($scope.warnCheckHttpsThcNumShow === true)
            || ($scope.warnCheckHttpsPortNumShow === true)
            || ($scope.warnCheckDnsReqNumShow === true)
            || ($scope.warnCheckDnsRepNumShow === true)
            || ($scope.warnCheckDnsAbnormalNumShow === true)
            || ($scope.warnCheckDnsPortNumShow === true)
            || ($scope.warnCheckNtpPortNumShow === true)
            || ($scope.warnCheckSnmpPortNumShow === true)
            || ($scope.warnCheckNtpNumShow === true)
            || ($scope.warnCheckSnmpNumShow === true)) {
            return;
        }

        if (($scope.warnCheckIntervalRangeShow === true)
            || ($scope.warnCheckTcpRangeShow === true)
            || ($scope.warnCheckTcpAbnormalRangeShow === true)
            || ($scope.warnCheckUdpRangeShow === true)
            || ($scope.warnCheckIcmpRangeShow === true)
            || ($scope.warnCheckHttpRangeShow === true)
            || ($scope.warnCheckHttpHeaderRangeShow === true)
            || ($scope.warnHttpPortRangeShow === true)
            || ($scope.warnCheckHttpPostRangeShow === true)
            || ($scope.warnCheckHttpsRangeShow === true)
            || ($scope.warnCheckHttpsThcRangeShow === true)
            || ($scope.warnHttpsPortRangeShow === true)
            || ($scope.warnCheckDnsReqRangeShow === true)
            || ($scope.warnCheckDnsRepRangeShow === true)
            || ($scope.warnCheckDnsAbnormalRangeShow === true)
            || ($scope.warnDnsPortRangeShow === true)
            || ($scope.warnCheckNtpPortRangeShow === true)
            || ($scope.warnCheckSnmpPortRangeShow === true)
            || ($scope.warnCheckNtpRangeShow === true)
            || ($scope.warnCheckSnmpRangeShow === true)) {
            return;
        }


        var requestUrl = getUrl($location, $suffix.cleanDev);

        httpPut($http, requestUrl, {"id":$scope.formData.id, "ip":$scope.formData.ip,"direct":direction,
                "tcp_first":$scope.formData.tcp_first,"check_interval":$scope.formData.check_interval,
                "tcp":$scope.formData.tcp_flood, "tcp_abnormal":$scope.formData.tcp_abnormal,
                "udp":$scope.formData.udp, "icmp":$scope.formData.icmp,//"icmp_abnormal":$scope.formData.icmp_abnormal,
                "http":$scope.formData.http, "http_port":$scope.formData.http_port,
                "http_header":$scope.formData.http_header, "http_post":$scope.formData.http_post,
                "https":$scope.formData.https, "https_port":$scope.formData.https_port,
                "https_thc":$scope.formData.https_thc, "dns_request":$scope.formData.dns_req,
                "dns_reply":$scope.formData.dns_rep, "dns_abnormal":$scope.formData.dns_abnormal,
                "dns_port":$scope.formData.dns_port, "cleanDevId":$scope.cleanDevId,
                "ntp_port":$scope.formData.ntp_port, "ntp":$scope.formData.ntp,
                "snmp_port":$scope.formData.snmp_port, "snmp":$scope.formData.snmp}, $scope.success, $scope.error);
    }

    $scope.putCleanDevToForm = function(response) {
        var $countJson=eval(response);

        $scope.formData.id = $countJson.id;
        $scope.formData.ip = $countJson.ip;
        if ($countJson.direct == "单向清洗") {
            $scope.formData.direction = 0;
        } else {
            $scope.formData.direction = 1;
        }

        $scope.formData.tcp_first = $countJson.tcp_first;
        $scope.formData.check_interval = $countJson.check_interval;
        $scope.formData.tcp_flood = $countJson.tcp;
        $scope.formData.tcp_abnormal = $countJson.tcp_abnormal;

        $scope.formData.udp = $countJson.udp;

        $scope.formData.icmp = $countJson.icmp;
        //$scope.formData.icmp_abnormal = $countJson.icmp_abnormal;

        $scope.formData.http = $countJson.http;
        $scope.formData.http_header = $countJson.http_header;
        $scope.formData.http_post = $countJson.http_post;
        $scope.formData.http_port = $countJson.http_port;

        $scope.formData.https_thc = $countJson.https_thc;
        $scope.formData.https_port = $countJson.https_port;
        $scope.formData.https = $countJson.https;

        $scope.formData.dns_req = $countJson.dns_request;
        $scope.formData.dns_rep = $countJson.dns_reply;
        $scope.formData.dns_port = $countJson.dns_port;
        $scope.formData.dns_abnormal = $countJson.dns_abnormal;

        $scope.cleanDevId = $countJson.cleanDevId;

        $scope.formData.ntp = $countJson.ntp;
        $scope.formData.ntp_port = $countJson.ntp_port;
        $scope.formData.snmp = $countJson.snmp;
        $scope.formData.snmp_port = $countJson.snmp_port;

    }

    $scope.getCleanDevError = function(response) {
        $scope.getErrShow = true;
    }

    $scope.putForm = function(id) {
        var requestUrl = getUrl($location, $suffix.cleanDev + '/' + id);

        httpGet($http,requestUrl, null, $scope.putCleanDevToForm, $scope.getCleanDevError);
    }

    $scope.putForm($stateParams.id);

    $scope.blurCheckIp = function() {
        $scope.warnAddrShow =false;
        $scope.warnSameShow = false;
        if (!checkNullAndShowPrompt($scope.formData.ip, "ip")) {
            $scope.warnAddrShow = true;
            return;
        }

        $scope.checkCleanDev($scope.formData.ip);
    }

    $scope.unidirection = function() {
        $scope.formData.direction = 0;
        direction = "单向清洗";
    }

    $scope.bidirection = function() {
        $scope.formData.direction = 1;
        direction = "双向清洗";
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


    $scope.blurCheckTcpAbnormal = function() {
        $scope.warnCheckTcpAbnormalShow = false;
        $scope.warnCheckTcpAbnormalNumShow = false;
        $scope.warnCheckTcpAbnormalRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.tcp_abnormal, "tcp_abnormal")) {
            $scope.warnCheckTcpAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.tcp_abnormal)) {
            $scope.warnCheckTcpAbnormalNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.tcp_abnormal, "tcp_abnormal")) {
            $scope.warnCheckTcpAbnormalRangeShow = true;
        }
    }

    $scope.blurCheckUDP = function() {
        $scope.warnCheckUdpShow = false;
        $scope.warnCheckUdpNumShow = false;
        $scope.warnCheckUdpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.udp, "udp")) {
            $scope.warnCheckUdpShow = true;
            return;
        }

        if(!isNum($scope.formData.udp)) {
            $scope.warnCheckUdpNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.udp, "udp")) {
            $scope.warnCheckUdpRangeShow = true;
        }
    }

    $scope.blurCheckICMP = function() {
        $scope.warnCheckIcmpShow =false;
        $scope.warnCheckIcmpNumShow = false;
        $scope.warnCheckIcmpRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.icmp, "icmp")) {
            $scope.warnCheckIcmpShow = true;
            return;
        }

        if(!isNum($scope.formData.icmp)) {
            $scope.warnCheckIcmpNumShow = true;
            return;
        }


        if (!checkThresholdRangeFixValue($scope.formData.icmp, "icmp")) {
            $scope.warnCheckIcmpRangeShow = true;
        }
    }

    /*$scope.blurCheckICMPAbnormal = function() {
     $scope.warnCheckIcmpAbnormalShow = false;
     $scope.warnCheckIcmpAbnormalNumShow = false;
     if (!checkNullAndShowPrompt($scope.formData.icmp_abnormal, "icmp_abnormal")) {
     $scope.warnCheckIcmpAbnormalShow = true;
     return;
     }

     if(!isNum($scope.formData.icmp_abnormal)) {
     $scope.warnCheckIcmpAbnormalNumShow = true;
     return;
     }
     }*/

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

    $scope.blurCheckHttpHeader = function() {
        $scope.warnCheckHttpHeaderShow = false;
        $scope.warnCheckHttpHeaderNumShow = false;
        $scope.warnCheckHttpHeaderRangeShow = false;
        if (!checkNullAndShowPrompt($scope.formData.http_header, "http_header")) {
            $scope.warnCheckHttpHeaderShow = true;
            return;
        }

        if(!isNum($scope.formData.http_header)) {
            $scope.warnCheckHttpHeaderNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.http_header, "http_header")) {
            $scope.warnCheckHttpHeaderRangeShow = true;
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


    $scope.blurCheckHttps = function() {
        $scope.warnCheckHttpsShow = false;
        $scope.warnCheckHttpsNumShow = false;
        $scope.warnCheckHttpsRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.https, "https")) {
            $scope.warnCheckHttpsShow = true;
            return;
        }

        if(!isNum($scope.formData.https)) {
            $scope.warnCheckHttpsNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.https, "https")) {
            $scope.warnCheckHttpsRangeShow = true;
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
        $scope.warnCheckDnsAbnormalRangeShow = false;

        if (!checkNullAndShowPrompt($scope.formData.dns_abnormal, "dns_abnormal")) {
            $scope.warnCheckDnsAbnormalShow = true;
            return;
        }

        if(!isNum($scope.formData.dns_abnormal)) {
            $scope.warnCheckDnsAbnormalNumShow = true;
            return;
        }

        if (!checkThresholdRangeFixValue($scope.formData.dns_abnormal, "dns_abnormal")) {
            $scope.warnCheckDnsAbnormalRangeShow = true;
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
    /******************************************************************************************/

    $scope.checkCleanDevSuccess = function (response) {
        var $ret = eval(response);
        if ($ret !== 1) {
            warmCtrl("ip");
            $scope.warnSameShow = true;
        } else {
            normalCtrl("ip");
            $scope.warnSameShow = false;
        }
    }

    $scope.checkCleanDevError = function (response, status) {
        //alert(response);
        //alert(status);
    }

    $scope.checkCleanDev = function($id,$ip) {
        $scope.warnAddrShow = false;
        if (($id === undefined) || ($ip === '')) {
            return;
        }

        isExistSameCleanDev($http, $location, $id, $ip, $scope.checkCleanDevSuccess, $scope.checkCleanDevError);
    }
}
]);

