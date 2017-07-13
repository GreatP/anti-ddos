'use strict';

/* Controllers */
  // signin controller
app.controller('SigninFormController', ['$rootScope', '$scope', '$http', '$window', '$state', 'UserService', 'AuthenticationService', '$cookieStore', '$localStorage', 
                                        function($rootScope, $scope, $http, $window, $state, UserService, AuthenticationService, $cookieStore, $localStorage) {
    $scope.user = {};
    $scope.rememberMe = false;
    
    if ($localStorage.rememberMe && $localStorage.user) {
    	$scope.user = $localStorage.user;
    	$scope.rememberMe = true;
    }
        
    $scope.login = function() {
      $scope.authError = null;
      // Try to login
      
      UserService.authenticate($.param({username: $scope.user.username, password: Base64.encode($scope.user.password)}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}; 
			$window.sessionStorage.token = authToken;
			AuthenticationService.isAuthenticated = true;
			
			UserService.get(function(user) {
				$rootScope.user = user;
				$state.go('app.home');
				//$location.path("/");
			});
		}, function(response){
			if (response.status == 401) {
				$scope.authError = '用户名或密码不正确';
			} else {
				$scope.authError = '服务器错误';
			}
		});
    };
    
    $scope.remember = function() {
    	if ($scope.rememberMe) {
        	$localStorage.user = $scope.user;   
        	$localStorage.rememberMe = true;
        } else {
        	$localStorage.user = {};
        	$localStorage.rememberMe = false;
        }
    };
	
  }])
;

app.controller('SignoutFormController', ['$rootScope', '$scope', '$http', '$state', 'UserService', 'AuthenticationService', '$cookieStore', function($rootScope, $scope, $http, $state, UserService, AuthenticationService, $cookieStore) {
    $scope.user = {};
    $scope.rememberMe = false;
        
    $scope.logout = function() {
    	if (AuthenticationService.isAuthenticated) {
            
            UserService.logOut().success(function(data) {
                AuthenticationService.isAuthenticated = false;
                delete $rootScope.user;
    			delete $rootScope.authToken;
                delete $window.sessionStorage.token;
        		$cookieStore.remove('authToken');
                $location.path("/login");
            }).error(function(status, data) {
                console.log(status);
                console.log(data);
            });
        }
        else {
            $location.path("/login");
        }
	};
	
	
  }])
;