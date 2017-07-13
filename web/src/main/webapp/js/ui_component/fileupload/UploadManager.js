app.factory('UploadManager', function ($rootScope) {
    var _files = [];
    return {
        add: function (file) {
        	this.clear();
            _files.push(file);
            $rootScope.$broadcast('fileAdded', file.files[0].name);
        },
        clear: function () {
            _files = [];
        },
        files: function () {
            var fileNames = [];
            $.each(_files, function (index, file) {
                fileNames.push(file.files[0].name);
            });
            return fileNames;
        },
        upload: function () {
            $.each(_files, function (index, file) {
                file.submit();
            });
            this.clear();
        },
        setProgress: function (percentage) {
            $rootScope.$broadcast('uploadProgress', percentage);
        },

        finish: function(filename) {
            $rootScope.$broadcast('uploadFinish', filename);
        },

        fail: function(filename) {
            $rootScope.$broadcast('uploadFail', filename);
        }
    };
});