//'use strict';
//var $localhost="http://172.16.12.208:9200";
//var $postfix="/_all/_search?pretty";
//var $requestUrl=$localhost+$postfix;

//var $localhost="http://10.111.121.15:8080";
//var $date="2015.*";

//var $requestUrl=$localhost+"/sys/admin?callback=JSON_CALLBACK";
//var $suffix = 'sys/admin';


//全局函数 ,用于跳转页面到app.user
function goUserList(state) {
    state.go('app.user');
}

function goPageUserList(state) {
    state.go('app.page.user');
}

//app.user页面的控制器，进入此页面，则逐一执行该函数体的代码
app.controller('userController', ['$rootScope','$scope', '$translate', '$http', '$location', '$state', 'suffix', function ($rootScope, $scope, $translate, $http, $location, $state, $suffix) {

    $scope.getUserListSuccess = function(response){
        var $userJson = eval(response);
        $scope.userTableJson = $userJson.users;
        $scope.userTableLength = $scope.userTableJson.length;
        $scope.tableloading = false;
        /*
        for (var i=0,a; a=$scope.userTableJson[i++];)
        {
        	var aa = new Date(a.createTime);
        	//a.createTime=aa.toLocaleDateString()+" "+aa.toLocaleTimeString();
        	a.createTime = date2time(aa);
        }
        */
        
        pageUpdate($userJson.total, $scope.page);
        
    }

    $scope.getUserListError = function(response, status) {
    	alert("error");
    }

    $scope.getUserList = function(page){
    	var requestUrl = getUrl($location, 'rest/users/pagelist' + '?page=' + page);
        httpGet($http, requestUrl, null, $scope.getUserListSuccess, $scope.getUserListError);
    }
    $scope.pageGetUserList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getUserList($scope.page);
    }

    $scope.add = function(id) {
        $state.go('app.userAdd');
    }

    $scope.edit = function(id) {
        $state.go('app.userEdit', {id: id});
    }

    $scope.delUserSuccess = function(response) {
        $scope.chkAll = false;
        $scope.listCheck.clearChecked();
        $scope.getUserList($scope.page);
    }

    $scope.delUserError = function() {
        //alert("bbbb");
    }


    $scope.del = function(id,delUsername) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }
            
            if (delUsername == "admin" || delUsername == $rootScope.user.name)
            {
            	bootbox.alert($translate.instant('other.BANNED') +$rootScope.user.name);
            	return;
            }

            var requestUrl = getUrl($location, $suffix.user + '/' + id);
            httpDelete($http, requestUrl, null, $scope.delUserSuccess, $scope.delUserError);
        })

    }

    $scope.toggleCheckAll = function() {
        $scope.listCheck.toggleCheckAll($scope.chkAll, $scope.userTableJson);
    }

    $scope.toggleCheck = function(id,name) {
        $scope.listCheck.toggleCheck(id,name);
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
            
            var arrayname = $scope.listCheck.getCheckedName();
            for (var i=0,a; a=arrayname[i++];)
            {
            	if (a == "admin" || a == $rootScope.user.name)
            	{
            		bootbox.alert($translate.instant('other.BANNED')+$rootScope.user.name);
            		return;
            	}
            }

            var requestUrl = getUrl($location, $suffix.user+ '/batchDelUser');
            httpPost($http, requestUrl, $scope.listCheck.getChecked(), $scope.delUserSuccess, $scope.delUserError);
        })
    }
    
    $scope.visible = false;
    $scope.chkAll = false;
    $scope.listCheck = new ListCheck();

    $scope.page = 1;
    pageInit($scope.pageGetUserList);
    $scope.getUserList($scope.page);
   
}
]);

//app.userAdd页面的控制器
app.controller('userAddController', ['$scope', '$http', '$location', '$state', 'suffix', function ($scope, $http, $location, $state, $suffix) {
    $scope.id = '';
    $scope.formData = {};

    $scope.detectorShow = false;

    $scope.cleanDevJson = {};
    $scope.formData.role = "ADMIN";

    $scope.formData.iface_category = 1;

    $scope.success = function(response){
    	goUserList($state);
    }

    $scope.error = function() {
        alert("server error");
    }
    
    $scope.checksuccess = function(response){
    	if (response.success == false)
    	{
    		$scope.warnSameUserName = true;
    	}
    }

    $scope.checkerror = function() {
        alert("server error");
    }
    
    $scope.blurCheckUserName = function() {
        $scope.warnUserNameShow =false;
        $scope.warnSameUserName =false;
        if (!checkNullAndShowPrompt($scope.formData.username, "userName")) {
            $scope.warnUserNameShow = true;
            return;
        }
        
        var requestUrl = getUrl($location, "rest/users/checkUser");
        httpPost($http, requestUrl, {"username":$scope.formData.username},
            $scope.checksuccess, $scope.checkerror);
    }
    
    $scope.blurCheckNickName = function() {
        $scope.warnNickNameShow =false;
        if (!checkNullAndShowPrompt($scope.formData.username, "nickName")) {
            $scope.warnNickNameShow = true;
            return;
        }
    }

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

    $scope.getCleanDev();

    $scope.setRole = function () {
        if ($scope.formData.role == "TENANT") {
            $scope.detectorShow = false;
        } else {
            $scope.detectorShow = false;
        }
    }

    $scope.goBack = function() {
        goUserList($state);
    }

    $scope.save = function() {
    	
    	if ($scope.warnUserNameShow === undefined)
    	{
    		$scope.warnUserNameShow = true;
    	}
    	
    	if ($scope.warnNickNameShow === undefined)
    	{
    		$scope.warnNickNameShow = true;
    	}

    	if (($scope.warnUserNameShow === true)
    	        || ($scope.warnNickNameShow === true)
    	        || ($scope.warnSameUserName === true)) 
    	{
    	      return;
    	}
        if ($scope.formData.iface_category == 1) {
            $scope.formData.cleanInport = 1;
            $scope.formData.cleanOutport = 2;
        } else {
            $scope.formData.cleanInport = 3;
            $scope.formData.cleanOutport = 4;
        }

    	$scope.formData.password = Base64.encode("admin");
        var requestUrl = getUrl($location, $suffix.user);
        httpPost($http, requestUrl, {"username":$scope.formData.username, "password":$scope.formData.password,
                "nickName":$scope.formData.nickName, "role":$scope.formData.role,"cleanDevId":$scope.formData.cleanDevId,
                "cleanInport": $scope.formData.cleanInport , "cleanOutport": $scope.formData.cleanOutport},
            $scope.success, $scope.error);
    }
}
]);

//app.userEdit的控制器
app.controller('userEditController', ['$scope','$http','$localStorage', '$location',  '$state', '$stateParams', 'suffix','$rootScope','$window','UserService','AuthenticationService',
                                      function ($scope, $http,$alerts, $location, $state, $stateParams, $suffix, $rootScope, $window,UserService, AuthenticationService) {
    $scope.formData = {};
    $scope.detectorShow = false;
    $scope.cleanDevJson = {};

    $scope.success = function(response){
 	
    	if ($scope.formData.changePasswd == true)
		{
			UserService.authenticate($.param({username: $scope.formData.username, password: $scope.formData.newPasswd}), function(authenticationResult) {
				var authToken = authenticationResult.token;
				$rootScope.authToken = authToken;
				$window.sessionStorage.token = authToken;
				$state.go('app.user');
			});
		}
		else
		{
			goUserList($state);
		}
        
    }

    $scope.error = function() {
        alert("error");
    }

    $scope.goBack = function() {
        goUserList($state);
    }
    
    $scope.blurUserName = function() {
        $scope.warnUserName =false;
        if (!checkNullAndShowPrompt($scope.formData.username, "userName")) {
            $scope.warnUserName = true;
            return;
        }
    }
    
    $scope.blurNickName = function() {
        $scope.warnNickName =false;
        if (!checkNullAndShowPrompt($scope.formData.username, "nickName")) {
            $scope.warnNickName = true;
            return;
        }
    }

    $scope.setRole = function () {
        if ($scope.formData.role == "TENANT") {
            $scope.detectorShow = false;
            $scope.formData.iface_category = 1;
        } else {
            $scope.detectorShow = false;
        }
    }

    $scope.save = function() {
    	if (($scope.warnUserName === true)
    	        || ($scope.warnNickName === true)) 
    	{
    	      return;
    	}

        if ($scope.formData.iface_category == 1) {
            $scope.formData.cleanInport = 1;
            $scope.formData.cleanOutport = 2;
        } else {
            $scope.formData.cleanInport = 3;
            $scope.formData.cleanOutport = 4;
        }

        var requestUrl = getUrl($location, $suffix.user);
        httpPut($http, requestUrl, {"username":$scope.formData.username, "nickName":$scope.formData.nickName,
                "role":$scope.formData.role, "newPasswd":$scope.formData.newPasswd,
                "changePasswd":$scope.formData.changePasswd,"cleanDevId":$scope.formData.cleanDevId,
                "cleanInport": $scope.formData.cleanInport , "cleanOutport": $scope.formData.cleanOutport},
            $scope.success, $scope.error);
    }

    $scope.getSuccess = function(response) {
        var $userJson=eval(response);
        $scope.detectorShow = false;

        $scope.formData.id = $userJson.id;

        $scope.formData.username = $userJson.username;
        $scope.formData.nickName = $userJson.nickName;
        $scope.formData.role = $userJson.role;
        document.getElementById('role').disabled = true;

        /*$scope.formData.username = $userJson.userEntity.username;
        $scope.formData.nickName = $userJson.userEntity.nickName;
        $scope.formData.role = $userJson.userEntity.role;
        document.getElementById('role').disabled = true;
        if ($scope.formData.role == "TENANT") {
            $scope.detectorShow = true;
            document.getElementById('cleanDevId').disabled = true;
            $scope.formData.cleanDevId = $userJson.cleanDevId;
            $scope.formData.cleanOutport = $userJson.cleanOutport;
            $scope.formData.cleanInport = $userJson.cleanInport;

            if ($scope.formData.cleanInport == 1) {
                $scope.formData.iface_category = 1;
            } else {
                $scope.formData.iface_category = 2;
            }
            document.getElementById('cleanPort').disabled = true;
        } else {
            $scope.detectorShow = false;
        }*/
    }

    $scope.getError = function() {
    	alert("error");
    }
    
    $scope.resetPasswd = function() {
    	$scope.formData.changePasswd = true;	
    	$scope.formData.newPasswd  = Base64.encode("admin");
    }
    $scope.getCleanDevSuccess = function(response){
        var $editJson=eval(response);
        $scope.cleanDevJson=$editJson;
        $scope.cleanDevLength=$scope.cleanDevJson.length;
        $scope.formData.cleanDevId = $editJson[0].id;

        $scope.tableloading=false;
    }

    $scope.getCleanDevError = function() {
        bootbox.alert("error");
    }

    $scope.getCleanDev = function() {
        var requestUrl = getUrl($location, $suffix.cleanDevAll);
        httpGet($http, requestUrl, null, $scope.getCleanDevSuccess, $scope.getCleanDevError);
    }

    $scope.putForm = function(id) {
        var requestUrl = getUrl($location, $suffix.userlist + '/' + id);

        httpGet($http, requestUrl, null, $scope.getSuccess, $scope.getError);
    }

    $scope.getCleanDev();
    $scope.putForm($stateParams.id);
}
]);

//用于控制页面右上角编辑当前用户信息的页面
app.controller('userPageController', ['$scope', '$rootScope', '$http', '$location', '$state', 'suffix', function ($scope, $rootScope, $http, $location, $state, $suffix) {

	$scope.passId = 0;
    $scope.getUserPageSuccess = function(response){
        var $userJson = eval(response);
        $scope.userTableJson = $userJson;
        $scope.userTableLength = $scope.userTableJson.length;
        $scope.tableloading = false;
        $scope.passId = $scope.userTableJson.id;
    }

    $scope.getUserPageError = function(response, status) {
    	alert("error");
    }
    

    $scope.getUserPage = function(){
        var requestUrl = getUrl($location, $suffix.user + '/userinfo/' + $rootScope.user.name);
        httpGet($http, requestUrl, null, $scope.getUserPageSuccess, $scope.getUserPageError);
    }

    $scope.pageEdit = function(id) {
        $state.go('app.page.userEdit', {id: id});
    }

    $scope.getUserPage();
}
]);

app.controller('userEditPageController', ['$rootScope', '$scope', '$stateParams', '$http', '$window', 'UserService', 'AuthenticationService', '$location', '$state', 'suffix', 
                                           function ($rootScope, $scope, $stateParams, $http, $window, UserService, AuthenticationService, $location, $state, $suffix) {

	$scope.formData = {};
	$scope.showFlag = false;
	$scope.warnNewShow = false;
	$scope.warnCheckShow = false;
	
	$scope.success = function(response){
		
		if ($scope.formData.changePasswd == true)
		{
			UserService.authenticate($.param({username: $scope.formData.username, password: $scope.formData.newPasswd}), function(authenticationResult) {
				var authToken = authenticationResult.token;
				$rootScope.authToken = authToken;
				$window.sessionStorage.token = authToken;
				$state.go('app.page.user');
			});
		}
		else
		{
			goPageUserList($state);
		}
		
		
    }

    $scope.error = function() {
        alert("error");
    }

    $scope.goBack = function() {
    	goPageUserList($state);
    }

    $scope.save = function() {
    	
    	if ($scope.formData.nickName == null || $scope.formData.nickName == "")
    	{
    		alert("请输入新昵称");
    		return;
    	}
    	
        $scope.formData.changePasswd = $scope.showFlag;
        if ($scope.formData.changePasswd == true)
        {
        	if ($scope.formData.newPasswd == "" || $scope.formData.newPasswd == null 
        			||$scope.formData.confirmPasswd == "" || $scope.formData.confirmPasswd == null )
        	{
        		alert("请输入新密码");
        		return;
        	}
        	
        	if ($scope.warnNewShow == true)
        	{
        		alert("输入新密码有误，请按提示重新输入！");
        		return;
        	}
        	
        	if ($scope.warnCheckShow == true)
        	{
        		alert("两次输入的密码不一致，请重新确定新密码！");
        		return;
        	}    
        	
        	$scope.formData.newPasswd = Base64.encode($scope.formData.newPasswd);
        }
        else
        {
        	$scope.formData.newPasswd = Base64.encode("admin");
        }
        
        var requestUrl = getUrl($location, $suffix.user);
        httpPut($http, requestUrl, {"username":$scope.formData.username, "nickName":$scope.formData.nickName, "role":$scope.formData.role, "changePasswd": $scope.formData.changePasswd, "newPasswd":$scope.formData.newPasswd},
            $scope.success, $scope.error);
    }
	
	$scope.getSuccess = function(response) {
        var $userJson=eval(response);

        /*$scope.formData.id = $userJson.userEntity.id;
        $scope.formData.username = $userJson.userEntity.username;
        $scope.formData.nickName = $userJson.userEntity.nickName;
        $scope.formData.role = $userJson.userEntity.role;*/

        $scope.formData.id = $userJson.id;
        $scope.formData.username = $userJson.username;
        $scope.formData.nickName = $userJson.nickName;
        $scope.formData.role = $userJson.role;
    }

    $scope.getError = function() {
    	alert("error");
    }
    
    $scope.changePassword = function() {	
    	$scope.showFlag = !($scope.showFlag);
    	
    }
    
    $scope.blurnew = function(){
    	
    	if ($scope.formData.newPasswd.length < 6)
    	{
    		document.getElementById('newPasswd').style.border ="1px solid red";
    		$scope.warnNewShow = true;
    	}
    	else
    	{
    		document.getElementById('newPasswd').style.border ="1px solid #ccc";
    		$scope.warnNewShow = false;
    	}	
    }
    
    $scope.blurcheck = function(){
    	
    	if ($scope.formData.confirmPasswd == "" || $scope.formData.confirmPasswd == null)
    	{
    		return;
    	}
    	
    	if ($scope.formData.newPasswd == $scope.formData.confirmPasswd)
    	{
    		document.getElementById('confirmPasswd').style.border ="1px solid #ccc";
    		$scope.warnCheckShow = false;
    	}
    	else
    	{
    		document.getElementById('confirmPasswd').style.border ="1px solid red";
    		$scope.warnCheckShow = true;
    	}	
    	
    }

    $scope.putForm = function(id) {
        var requestUrl = getUrl($location, $suffix.userlist + '/' + id);

        httpGet($http, requestUrl, null, $scope.getSuccess, $scope.getError);
    }

    $scope.putForm($stateParams.id);
}
]);
