'use strict';

function transformAuthority(val) {
    if (val) {
    } else {
        val = false;
    }

    return val;
}

app.controller('actionlog1Controller',  ['$scope', '$http','$translate', '$localStorage', '$location', '$state', '$timeout', '$animate', 'suffix', function ($scope, $http,$translate, $alerts, $location, $state, $timeout, $animate, $suffix) {
    // alert('1111');
	
	var $localhostlog1="http://"+"10.111.121.27"+":9200";
	//var $localhostlog1="http://"+$location.host()+":9200";
	var $indexlog1="/anti-ddos-system";
	var $tyeplog1="/logs";
	var $requestUrllog1=$localhostlog1+$indexlog1+$tyeplog1+"/_search?pretty";
	var $delUrllog1=$localhostlog1+$indexlog1+$tyeplog1;
	var $refreshUrllog1=$localhostlog1+$indexlog1+"/_refresh";
	var $match=[{ "match": { "priority": "WARN" }}, { "match": { "priority": "ERROR" }}, { "match": { "priority": "INFO" }}]; 
	var $filter={
	    "bool": { "must": [ { "query_string": { "default_field": "logs.logger_name","query": "com.cetc.security.ddos*"}}],"must_not": [ ],"should": [ ]}};
	
	$scope.toggleCheckAll = function() {
        $scope.listCheck.toggleCheckAllbyES($scope.chkAll, $scope.userTableJson);
    }

    $scope.toggleCheck = function(id) {
        $scope.listCheck.toggleCheck(id);
    }
	
    $scope.clearall = function() {
    	bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
            if (!result) {
                return;
            }
            	
            httpDelete($http, $delUrllog1, null, $scope.delLogSuccess, $scope.delLogError);
            
    	})
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

            var xxx = $scope.listCheck.getChecked();
            
            for (var i = 0; i < xxx.length; i++)
            {                      	
            	httpDelete($http, $delUrllog1 +"/" + xxx[i], null, $scope.delLogSuccess, $scope.delLogError);
            }
            
            $scope.chkAll=false;
            $scope.listCheck.clearChecked();
        })
    }
	
    $scope.getCallingList=function(){
    	
    	$http.post($requestUrllog1,{"query": {"match_all":{}},"size": 1000,"sort": [{"@timestamp":{"order": "desc", "ignore_unmapped": true}}]}).success(function(response){
            var $countJson=eval(response);
            $scope.userTableJson=$countJson.hits.hits;
            $scope.userTableLength= $scope.userTableJson.length;
            $scope.tableloading = false;
        })
    }
    
    $scope.callBackRefresh = function(response){
    	
     	 $scope.getUserListnew($scope.page);
    } 
    
    $scope.daterefresh = function(date){    	
    	var tmp;    	
    	tmp = date.replace("T","  ");
    	return tmp.replace("Z","");
   } 

    $scope.delLogSuccess = function(response) {    
    	httpPost($http, $refreshUrllog1,"{}", $scope.callBackRefresh, $scope.callBackRefresh);    	 
    }

    $scope.delLogError = function() {
        //alert("bbbb");
    }
    
    $scope.del = function(id) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
           if (!result) {
               return;
           }
           
           httpDelete($http, $delUrllog1+"/"+id, null, $scope.delLogSuccess, $scope.delLogError);
       })

    }
    
    $scope.getUserList = function(page){
       	
    	$http.post($requestUrllog1,{"from" : page*10-9, "size" : 10, "query": {"match_all":{}},"sort": [{"@timestamp":{"order": "desc", "ignore_unmapped": true}}]}).success(function(response){
            
            var $countJson = eval(response);
            $scope.userTableJson = $countJson.hits.hits;

            $scope.userTableLength = $scope.userTableJson.length;
            $scope.tableloading = false;

            pageUpdate($countJson.hits.total, $scope.page);
        })
    }
    
    
    $scope.getUserListnew = function(page){
     	
    	$http.post($requestUrllog1,{"query": {"filtered": {"query": {"bool":{"should":$match}}}},"filter": {
            "query": $filter
        },"sort": [{"@timestamp":{"order": "desc", "ignore_unmapped": true}}], "from" : page*10-9, "size" : 10,}).success(function(response){

            
            var $countJson = eval(response);
            $scope.userTableJson = $countJson.hits.hits;
            $scope.userTableLength = $scope.userTableJson.length;
            $scope.tableloading = false;

            /*
            angular.element("#page").page('_updateTotal', $adminJson.total);
             angular.element("#page").page('_remoteOrRedner', $scope.page - 1);
             */

            pageUpdate($countJson.hits.total, $scope.page);
        })
    }
    
    $scope.pageGetUserList = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getUserList($scope.page);
    }
    
    $scope.pageGetUserListnew = function(pageIndex) {
        $scope.page = pageIndex + 1;
        $scope.getUserListnew($scope.page);
    }
    
    $scope.save = function(){
    	 $scope.formData.error = transformAuthority($scope.formData.error);
         $scope.formData.info = transformAuthority($scope.formData.info);
         $scope.formData.debug = transformAuthority($scope.formData.debug);

         /*恶心想吐的代码*/
         
         if ($scope.formData.error == false){
        	if ($scope.formData.info == false){
        		if ($scope.formData.warn == false){//000
        			$match=[{ "match": { "priority": "xxx" }}];
        		}else{//001
        			$match=[{ "match": { "priority": "WARN" }}];
        		}
        	}else{
        		if ($scope.formData.warn == false){//010
        			$match=[{ "match": { "priority": "INFO" }}];
        		}else{//011
        			$match=[{ "match": { "priority": "INFO" }}, { "match": { "priority": "WARN" }}];
        		}
        	}        	 
         }else{
        	 if ($scope.formData.info == false){
         		if ($scope.formData.warn == false){//100
         			$match=[{ "match": { "priority": "ERROR" }}];
         		}else{//101
         			$match=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "WARN" }}];
         		}
         	}else{
         		if ($scope.formData.warn == false){//110
         			$match=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "INFO" }}];
         		}else{
         			$match=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "INFO" }}, { "match": { "priority": "WARN" }}];
         		}
         	} 
         }
         
         if ($scope.formData.context){
        	 //$filter={"query_string": {"default_field": "logs.message", "query": $scope.formData.context}}; 
        	 $filter= {
                 "bool": { "must": [ { "query_string": { "default_field": "logs.logger_name","query": "com.cetc.security.ddos*"}},{ "query_string": { "default_field": "logs.message","query": $scope.formData.context}}],"must_not": [ ],"should": [ ]}};
         }
          
         $scope.page = 1;
         pageInit($scope.pageGetUserListnew);
         $scope.getUserListnew($scope.page);    
                 
    }
    
    $scope.listCheck = new ListCheck();
    $scope.page = 1;
    $scope.tableloading = true;
    pageInit($scope.pageGetUserListnew);
    $scope.getUserListnew($scope.page);
    $scope.formData = {};
    $scope.formData.error = true;
    $scope.formData.warn = true;
    $scope.formData.info = true;
    $scope.formData.debug = true;
    //$scope.getCallingList();
    //$districtMapApi.refresh();
    //$scope.pieChage++;

}
]);