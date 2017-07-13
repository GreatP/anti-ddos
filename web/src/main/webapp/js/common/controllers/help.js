'use strict';

/* Controllers */
app.controller('HelpController', ['$scope', '$window', function($scope, $window) {
           
    $scope.help = function() {
    	$window.open("./tpl/user-guide.html", "帮助", "left=300,top=200, width=800,height=400,scrollbars=yes, resizable=yes");
    };
}]);