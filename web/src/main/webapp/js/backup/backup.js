app.controller('backupController',
    ['$scope', '$rootScope', 'UploadManager', '$http', '$location', '$state', 'suffix',
    function ($scope,$rootScope, uploadManager, $http, $location, $state, $suffix) {

    $scope.files = [];
    $scope.percentage = 0;
    function goList(state) {
        state.go('app.backup');
    }

    $scope.success = function(response){
        $scope.BackupSuccessShow = true;
        var backup = document.getElementById("backup");
        backup.href = "data/anti-ddos.sql";
        backup.click();
        backup.href = "javascript:void(0);";
        goList($state);
        $scope.$apply();
    }

    $scope.error = function() {
        //bootbox.alert("Get Backup-DB error");
        $scope.warnBackupFailShow = true;
    }

    $scope.backUp = function() {

        $scope.BackupSuccessShow = false;

        $scope.warnBackupFailShow = false;

        var requestUrl = getUrl($location, $suffix.backUp);

        httpGet($http, requestUrl,"", $scope.success, $scope.error);
    }

    $scope.upload = function () {
        uploadManager.upload();
        //$scope.files = [];
    };
    $scope.cancel = function () {
        $scope.files = [];
        $scope.percentage = 0;
    }
    $rootScope.$on('fileAdded', function (e, call) {
        $scope.RestoreSuccessShow = false;
        $scope.warnRestoreFailShow = false;
        $scope.cancel();
        $scope.files.push(call);
        $scope.$apply();
    });
    $rootScope.$on('uploadProgress', function (e, call) {
        $scope.percentage = call;
        $scope.$apply();
    });

    $rootScope.$on('uploadFinish', function (e, call) {
        /*var fileName = call;
        bootbox.alert("Restore database file " + fileName + " successfully!");
        */
        $scope.RestoreSuccessShow = true;
        $scope.$apply();
    });
    $rootScope.$on('uploadFail', function (e, call) {
        /*var fileName = call;
        bootbox.alert("Restore database file " + fileName + " failed!");*/
        $scope.warnRestoreFailShow = true;
        $scope.$apply();
    });

}
]);