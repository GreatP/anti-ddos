app.controller('DatepickerCtrl', ['$scope', '$http', '$location', '$state', 'suffix', function ($scope, $http, $location, $state, $suffix) {
  $scope.today = function() {
    $scope.dt_from = new Date().toISOString().slice(0, 10);
    $scope.dt_to = new Date().toISOString().slice(0, 10);
  };
  $scope.today();

  $scope.clear = function () {
	  $scope.today();
  };

  $scope.open_from = function($event) {
    $scope.status.from_opened = true;
  };

  $scope.open_to = function($event) {
	$scope.status.to_opened = true;
  };
  
  $scope.dateOptions = {
    formatYear: 'yyyy',
    startingDay: 1
  };

  $scope.status = {
	to_opened: false,
	from_opened: false
  };
}]);
