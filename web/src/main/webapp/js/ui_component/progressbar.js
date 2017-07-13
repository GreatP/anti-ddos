app.controller('DatepickerCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.max = 200;

  $scope.random = function() {
    $scope.dynamic = Math.floor((Math.random() * 100) + 1);
  };
  $scope.random();
}]);
