function getUrl(location, suffix) {
    var url = location.protocol() + '://' + location.host() + ':' + location.port() + '/' + suffix;
    return url;
}

function __httpRequest(http, method, requestUrl, obj, successCallback, errorCallback) {
    http({
            method:method,
            url:requestUrl,
            data:JSON.stringify(obj)
            //headers:{ 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }
    )
        .success(successCallback)
        .error(errorCallback);
}

function httpPost(http, requestUrl, obj, successCallback, errorCallback) {
    __httpRequest(http, 'POST', requestUrl, obj, successCallback, errorCallback);
}

function httpPut(http, requestUrl, obj, successCallback, errorCallback) {
    //obj['_method'] = 'PUT';
    //httpPost(http, requestUrl, obj, successCallback, errorCallback);
    __httpRequest(http, 'PUT', requestUrl, obj, successCallback, errorCallback);
}

/*
function httpGet(http, requestUrl, successCallback, errorCallback) {
    http.jsonp(requestUrl).success(successCallback).error(errorCallback);
}*/

function httpGet(http, requestUrl, obj, successCallback, errorCallback) {
    __httpRequest(http, 'GET', requestUrl, obj, successCallback, errorCallback);
}

/*
function httpDelete(http, requestUrl, successCallback, errorCallback) {
    httpdelete(requestUrl).success(successCallback).error(errorCallback);
}*/

function httpDelete(http, requestUrl, obj, successCallback, errorCallback) {
    __httpRequest(http, 'DELETE', requestUrl, obj, successCallback, errorCallback);
}

function ListCheck() {
    var checkedArray = new Array();
    var checkedname = new Array();

    this.toggleCheck = function(id,name) {
        var index = checkedArray.indexOf(id);
        if (index === -1) {
            checkedArray.push(id);
            checkedname.push(name);
        } else {
            checkedArray.splice(index, 1);
            checkedname.splice(index,1);
        }
    }

    this.toggleCheckAll = function(chkAll, table) {
        //checkedArray = [];
        this.clearChecked();
        angular.forEach(table, function(value, key) {
            value.checked = chkAll;
            if (chkAll) {
                checkedArray.push(value.id);
                checkedname.push(value.username);
            }
        });
    }

    this.toggleCheckAllbyES = function(chkAll, table) {
        //checkedArray = [];
        this.clearChecked();
        angular.forEach(table, function(value, key) {
            value.checked = chkAll;
            if (chkAll) {
                checkedArray.push(value._id);
            }
        });
    }

    this.checkedCount = function() {
        return checkedArray.length;
    }

    this.getChecked = function() {
        return checkedArray;
    }

    this.getCheckedName = function() {
        return checkedname;
    }

    this.clearChecked = function() {
        checkedArray = [];
        checkedname = [];
    }
}

function pageInit(pageChanged) {
    angular.element("#page").page({
        debug: false,
        showInfo: true,
        showJump: true,
        showPageSizes: false,
        total: 0
    });

    angular.element("#page").on("pageClicked", function (event, pageIndex) {
        pageChanged(pageIndex);
        //$scope.pageGetAdminList(pageIndex);
    }).on('jumpClicked', function (event, pageIndex) {
        pageChanged(pageIndex);
        //$scope.pageGetAdminList(pageIndex);
    }).on('pageSizeChanged', function (event, pageSize) {

    });
}

function pageUpdate(total, page) {
    angular.element("#page").page('_updateTotal', total);
    angular.element("#page").page('_remoteOrRedner', page - 1);
}

function clone(obj) {
    if (null == obj || "object" != typeof obj) return obj;
    var copy = obj.constructor();
    for (var attr in obj) {
        if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
    }
    return copy;
}

function isNum(s) {
    if(s != null) {
        var reg = new RegExp("^-?\\d+$");
        return reg.test(s)? true:false;
    }
    return false;
}

function normalCtrl(ctrlId) {
    document.getElementById(ctrlId).style.border = "1px solid #ccc";

}

function warmCtrl(ctrlId) {
    document.getElementById(ctrlId).style.border = "1px solid red";
    //$('#' + ctrlId).addClass("form-control has-error");
}

function checkNullAndShowPrompt(str, ctrlId) {
    if ((str === undefined) || (str === '')) {
        warmCtrl(ctrlId);
        return false;
    }

    normalCtrl(ctrlId);

    return true;
}

function checkRangeAdnShowPrompt(str, ctrlId, min, max) {
    if (!isNum(str) || (str < min) || (str > max)) {
        warmCtrl(ctrlId);
        return false;
    }

    normalCtrl(ctrlId);
    return true;
}

function checkThresholdRangeFixValue(str, ctrlId) {
    var max = 9999999999;
    var min = 1;
    if ((str < min) || (str > max)) {
        warmCtrl(ctrlId);
        return false;
    }

    normalCtrl(ctrlId);
    return true;
}

function checkMinAndShowPrompt(str, ctrlId, min) {
    if (!isNum(str) || (str <= min)) {
        warmCtrl(ctrlId);
        return false;
    }

    normalCtrl(ctrlId);
    return true;
}

function checkPIntAndShowPrompt(str, ctrlId)
{
	if (isNum(str) && str > 0 && str%1===0)
	{
		normalCtrl(ctrlId);
	    return true;
	}
	
	warmCtrl(ctrlId);
    return false;
}

function date2time(obj){
	
	var dateString;
	var yearString;
	var year;
	var month;
	var day;
	var hour;
	var min;
	var sec;
	
	yearString = obj.toLocaleDateString();
	year = obj.getFullYear();
	month = obj.getMonth();
	day = obj.getDay();
	hour = obj.getHours();
	min = obj.getMinutes();
	sec = obj.getSeconds();
	
	dateString = year+"-"+month+"-"+day + " "+ hour+":"+min+":"+sec;
	
    return dateString;

}
