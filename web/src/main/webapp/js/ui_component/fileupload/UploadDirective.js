app.directive('upload', ['UploadManager', function factory(uploadManager) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            $(element).fileupload({
                dataType: 'json',
                add: function (e, data) {
                    uploadManager.add(data);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    uploadManager.setProgress(progress);
                },
                done: function (e, data) {
                    uploadManager.finish(data.files[0].name);
                },
                fail: function(e, data) {
                    uploadManager.fail(data.files[0].name);
                }
            });
        }
    };
}]);