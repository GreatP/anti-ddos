app.controller('DemoFileUploadController', [ '$scope', '$http', '$filter',
		'$window', function($scope, $http) {
			$scope.options = {
				url : 'rest/files/upload'
			};
			$('#fileupload').fileupload({
				// Uncomment the following to send cross-domain cookies:
				//xhrFields: {withCredentials: true},
				url : 'rest/files/upload',
				 add: function (e, data) {
			            data.context = $('<button/>').text('Upload')
			                .appendTo(document.body)
			                .click(function () {
			                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));
			                    data.submit();
			                });
			        },
			        done: function (e, data) {
			            data.context.text('Upload finished.');
			        }
			});
			// Load existing files:
			$('#fileupload').addClass('fileupload-processing');
			$.ajax({
				// Uncomment the following to send cross-domain cookies:
				//xhrFields: {withCredentials: true},
				url : $('#fileupload').fileupload('option', 'url'),
				dataType : 'json',
				context : $('#fileupload')[0]
			}).always(function() {
				$(this).removeClass('fileupload-processing');
			}).done(function(result) {
				//	            $(this).fileupload('option', 'done')
				//	                .call(this, $.Event('done'), {result: result});
			});
		} ]);

app.controller('FileUploadCtrl',
	    ['$scope', '$rootScope', 'UploadManager', 
	    function ($scope, $rootScope, uploadManager) {
	    $scope.files = [];
	    $scope.percentage = 0;
	    $scope.upload = function () {
	        uploadManager.upload();
	        //$scope.files = [];
	    };
	    $scope.cancel = function () {
	    	$scope.files = [];
	    	$scope.percentage = 0;
	    }
	    $rootScope.$on('fileAdded', function (e, call) {
	        $scope.files.push(call);
	        $scope.$apply();
	    });
	    $rootScope.$on('uploadProgress', function (e, call) {
	        $scope.percentage = call;
	        $scope.$apply();
	    });
	}]);

app.controller('FileDestroyController', [ '$scope', '$http',
		function($scope, $http) {
			var file = $scope.file, state;
			if (file.url) {
				file.$state = function() {
					return state;
				};
				file.$destroy = function() {
					state = 'pending';
					return $http({
						url : file.deleteUrl,
						method : file.deleteType
					}).then(function() {
						state = 'resolved';
						$scope.clear(file);
					}, function() {
						state = 'rejected';
					});
				};
			} else if (!file.$cancel && !file._index) {
				file.$cancel = function() {
					$scope.clear(file);
				};
			}
		} ]);