'use strict';

function transformAuthority(val) {
    if (val) {
    } else {
        val = false;
    }

    return val;
}

app.controller('actionlog2Controller', ['$scope', '$http','$translate', '$localStorage', '$location', '$state', '$timeout', '$animate', 'suffix', function ($scope, $http, $translate, $alerts, $location, $state, $timeout, $animate, $suffix) {
    // alert('1111');
	
	var $localhost2="http://"+"10.111.121.27"+":9200";
	//var $localhost2="http://"+$location.host()+":9200";
	var $index2="/anti-ddos-operation";
	var $type2="/logs";
	var $requestUrl2=$localhost2+$index2+$type2+"/_search?pretty";
	var $delUrl2=$localhost2+$index2+$type2;
	var $refreshUrl2=$localhost2+$index2+"/_refresh";
	
	var $match2=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "WARN" }}, { "match": { "priority": "INFO" }}]; 
	
	var $filter2={
	    "bool": { "must": [ { "query_string": { "default_field": "logs.logger_name","query": "com.cetc*"}}],"must_not": [ ],"should": [ ]}};
	
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
            	
            httpDelete($http, $delUrl2, null, $scope.delLogSuccess, $scope.delLogError);
            
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
            	httpDelete($http, $delUrl2 +"/" + xxx[i], null,  $scope.delLogSuccess, $scope.delLogError);
            }
            
            $scope.chkAll=false;
            $scope.listCheck.clearChecked();
        })
    }
	
    $scope.getCallingList=function(){
    	
    	$http.post($requestUrl2,{"query": {"match_all":{}},"size": 1000,"sort": [{"@timestamp":{"order": "desc", "ignore_unmapped": true}}]}).success(function(response){
            var $countJson=eval(response);
            $scope.userTableJson=$countJson.hits.hits;
            $scope.userTableLength=$scope.userTableJson.length;
            $scope.tableloading = false;
        })
    }
    
    $scope.callBackRefresh = function(response){
    	
     	 $scope.getUserListnew($scope.page);
    } 

    $scope.delLogSuccess = function(response) {    
    	httpPost($http, $refreshUrl2,"{}", $scope.callBackRefresh, $scope.callBackRefresh);    	 
    }

    $scope.delLogError = function() {
        //alert("bbbb");
    }
    
    $scope.del = function(id) {
        bootbox.confirm($translate.instant('other.CONFIRM'), function(result) {
           if (!result) {
               return;
           }
           
           httpDelete($http, $delUrl2+"/"+id, null, $scope.delLogSuccess, $scope.delLogError);
       })

    }
    
    $scope.daterefresh = function(date){    	
    	var tmp;    	
    	tmp = date.replace("T","  ");
    	return tmp.replace("Z","");
   } 
    
    $scope.getUserList = function(page){
       	
    	$http.post($requestUrl2,{"from" : page*10-9, "size" : 10, "query": {"match_all":{}},"sort": [{"@timestamp":{"order": "desc", "ignore_unmapped": true}}]}).success(function(response){
            
            var $countJson = eval(response);
            $scope.userTableJson = $countJson.hits.hits;

            $scope.userTableLength = $scope.userTableJson.length;
            $scope.tableloading = false;

            pageUpdate($countJson.hits.total, $scope.page);
        })
    }
    
    
    $scope.getUserListnew = function(page){
     	
    	$http.post($requestUrl2,{"query": {"filtered": {"query": {"bool":{"should":$match2}}}},"filter": {
            "query": $filter2
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
        			$match2=[{ "match": { "priority": "xxx" }}];
        		}else{//001
        			$match2=[{ "match": { "priority": "WARN" }}];
        		}
        	}else{
        		if ($scope.formData.warn == false){//010
        			$match2=[{ "match": { "priority": "INFO" }}];
        		}else{//011
        			$match2=[{ "match": { "priority": "INFO" }}, { "match": { "priority": "WARN" }}];
        		}
        	}        	 
         }else{
        	 if ($scope.formData.info == false){
         		if ($scope.formData.warn == false){//100
         			$match2=[{ "match": { "priority": "ERROR" }}];
         		}else{//101
         			$match2=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "WARN" }}];
         		}
         	}else{
         		if ($scope.formData.warn == false){//110
         			$match2=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "INFO" }}];
         		}else{
         			$match2=[{ "match": { "priority": "ERROR" }}, { "match": { "priority": "INFO" }}, { "match": { "priority": "WARN" }}];
         		}
         	} 
         }
         
         if ($scope.formData.context){
        	 //$filter={"query_string": {"default_field": "logs.message", "query": $scope.formData.context}}; 
        	 $filter2= {
                 "bool": { "must": [ { "query_string": { "default_field": "logs.logger_name","query": "com.cetc*"}},{ "query_string": { "default_field": "logs.message","query": $scope.formData.context}}],"must_not": [ ],"should": [ ]}};
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
    $scope.formData.info = true;
    $scope.formData.warn = true;
    //$scope.formData.debug = true;
    //$scope.getCallingList();
    //$districtMapApi.refresh();
    //$scope.pieChage++;

}
]);