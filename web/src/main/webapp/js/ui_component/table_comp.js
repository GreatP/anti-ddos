


app.controller('componentsTableController', ['$scope', '$http', '$location', '$state', 'suffix', function ($scope, $http, $location, $state, $suffix) {
	
	$scope.getUserListSuccess = function(response){
        var $userJson = eval(response);
        $scope.userTableJson = $userJson.users;
        $scope.userTableLength = $scope.userTableJson.length;
        $scope.tableloading = false;
        
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

    $scope.delUserSuccess = function(response) {
        $scope.chkAll = false;
        $scope.listCheck.clearChecked();
        $scope.getUserList();
    }

    $scope.delUserError = function() {
        //alert("bbbb");
    }


    $scope.del = function(id) {
        bootbox.confirm("确认删除？", function(result) {
            if (!result) {
                return;
            }

            var requestUrl = getUrl($location, $suffix.user + '/' + id);
            httpDelete($http, requestUrl, null, $scope.delUserSuccess, $scope.delUserError);
        })

    } 
    
    $scope.visible = false;
    $scope.chkAll = false;
    $scope.page = 1;
    pageInit($scope.pageGetUserList);

    $scope.getUserList($scope.page);
	
	}]);


