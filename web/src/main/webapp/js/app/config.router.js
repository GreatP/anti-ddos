'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
      function ($stateProvider,   $urlRouterProvider, $locationProvider, $httpProvider) {
       	
          $urlRouterProvider
              .otherwise('/app/home');
          
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/home/app.html'
              })
              //.state('app.home', {
              //    url: '/home',
              //    templateUrl: 'tpl/home/overview.html',
              //    resolve: {
              //      deps: ['$ocLazyLoad',
              //        function( $ocLazyLoad ){
              //          return $ocLazyLoad.load(['js/home/overViewController.js']);
              //      }]
              //    }
              //})

              .state('app.home', {
                  url: '/home',
                  templateUrl: 'tpl/home/attackview.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function( $ocLazyLoad ){
                              return $ocLazyLoad.load([
                                  'js/common/utils_chart.js',
                                  'js/home/attackViewController.js'
                              ]);
                          }]
                  }
              })

              .state('app.viewAttack', {
                  url: '/view/attackanalyse',
                  templateUrl: 'tpl/home/attackview.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function( $ocLazyLoad ){
                              return $ocLazyLoad.load([
                                  'js/common/utils_chart.js',
                                  'js/home/attackViewController.js'
                              ]);
                          }]
                  }
              })

              .state('app.attackLocation', {
                  url: '/src',
                  templateUrl: 'tpl/home/attackSourceDistribution.html',
                  controller:'attackSourceDistributionController',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function( $ocLazyLoad ){
                              return $ocLazyLoad.load([
                                  'js/home/attackSourceDistribution.js'
                              ]);
                          }]
                  }
              })
              /* data overview*/
              .state('app.dataOverview', {
                  url: '/view/overview',
                  templateUrl: 'tpl/home/data_overview.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function( $ocLazyLoad ){
                              return $ocLazyLoad.load([
                                  'js/common/utils_chart.js',
                                  'js/home/dataOverviewController.js'
                              ]);
                          }]
                  }
              })
              
              .state('app.user',{
                    url:'/user',
                    templateUrl:'tpl/user/user.html',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/user/user.js',
                                    'js/common/services/suffix.js'
                                    ]);

                            }]
                    }
                })
                
                .state('app.userAdd',{
                    url:'/user/add',
                    templateUrl:'tpl/user/userAdd.html',
                    controller:'userAddController',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/user/user.js']);

                            }]
                    }
                })

                .state('app.userEdit',{
                    url:'/user/edit/:id',
                    templateUrl:'tpl/user/userEdit.html',
                    controller:'userEditController',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/user/user.js']);
                            }]
                    }
                })
                
                .state('app.page.user',{
                    url:'/user/page',
                    templateUrl:'tpl/user/page_user.html',
                    controller:'userPageController',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/user/user.js']);
                            }]
                    }
                })
                
                .state('app.page.userEdit',{
                    url:'/user/page/edit/:id',
                    templateUrl:'tpl/user/page_user_edit.html',
                    controller:'userEditPageController',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/user/user.js']);
                            }]
                    }
                })

              .state('app.controller',{
                  url:'/controller',
                  templateUrl:'tpl/controller/controller.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/controller/controller.js'
                              ]);
                          }]
                  }
              })

              .state('app.controllerAdd',{
                  url:'/controller/add',
                  templateUrl:'tpl/controller/controllerEdit.html',
                  controller:'conAddController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/controller/controller.js']);

                          }]
                  }
              })

              .state('app.controllerEdit',{
                  url:'/controller/edit/:id',
                  templateUrl:'tpl/controller/controllerEdit.html',
                  controller:'conEditController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/controller/controller.js']);

                          }]
                  }
              })

              .state('app.netnode',{
                  url:'/netNode',
                  templateUrl:'tpl/netNode/netNode.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/netNode/netNode.js'
                              ]);

                          }]
                  }
              })

              .state('app.netnodeAdd',{
                  url:'/netNode/add',
                  templateUrl:'tpl/netNode/netNodeEdit.html',
                  controller:'netnodeAddController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/netNode/netNode.js'
                              ]);

                          }]
                  }
              })

              .state('app.netnodeEdit',{
                  url:'/netNode/edit/:id',
                  templateUrl:'tpl/netNode/netNodeEdit.html',
                  controller:'netNodeEditController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/netNode/netNode.js'
                              ]);
                          }]
                  }
              })

              .state('app.po',{
                  url:'/po',
                  templateUrl:'tpl/po/po.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/po/po.js'
                              ]);

                          }]
                  }
              })

              .state('app.poAdd',{
                  url:'/po/add',
                  templateUrl:'tpl/po/poEdit.html',
                  controller:'poAddController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/po/po.js'
                              ]);

                          }]
                  }
              })

              .state('app.poEdit',{
                  url:'/po/edit/:id',
                  templateUrl:'tpl/po/poEdit.html',
                  controller:'poEditController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/po/po.js'
                              ]);
                          }]
                  }
              })

              /* clean device configure */
              .state('app.cleandev',{
                  url:'/cleanDev',
                  templateUrl:'tpl/cleanDev/cleanDev.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/cleanDev/cleanDev.js'
                              ]);

                          }]
                  }
              })

              .state('app.cleandevAdd',{
                  url:'/cleanDev/add',
                  templateUrl:'tpl/cleanDev/cleanDevEdit.html',
                  controller:'cleandevAddController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/cleanDev/cleanDev.js'
                              ]);

                          }]
                  }
              })

              .state('app.cleandevEdit',{
                  url:'/cleanDev/edit/:id',
                  templateUrl:'tpl/cleanDev/cleanDevEdit.html',
                  controller:'cleanDevEditController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/cleanDev/cleanDev.js'
                              ]);
                          }]
                  }
              })

              /* system configure  */
              .state('app.admin',{
                  url:'/admin',
                  templateUrl:'tpl/admin/admin.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/admin/admin.js',
                                  'js/common/services/suffix.js'
                              ]);

                          }]
                  }
              })

              .state('app.adminAdd',{
                  url:'/admin/add',
                  templateUrl:'tpl/admin/adminEdit.html',
                  controller:'adminAddController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/admin/admin.js']);

                          }]
                  }
              })
              .state('app.adminEdit',{
                  url:'/admin/edit/:id',
                  templateUrl:'tpl/admin/adminEdit.html',
                  controller:'adminEditController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/admin/admin.js']);
                          }]
                  }
              })

              .state('app.backup',{
                  url:'/backup',
                  templateUrl:'tpl/backup/backup.html',
                  controller:'backupController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/backup/backup.js'
                              ]);
                          }]
                  }
              })

              /* End of system configure */

              .state('app.log1',{
                  url:'/log1',
                  templateUrl:'tpl/log/log1.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/log/log1.js'
                              ]);
                          }]
                  }
              })
              .state('app.log2',{
                  url:'/log2',
                  templateUrl:'tpl/log/log2.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/log/log2.js'
                              ]);
                          }]
                  }
              })
              .state('app.log3',{
                  url:'/log3',
                  templateUrl:'tpl/log/log3.html',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/log/log3.js'
                              ]);
                          }]
                  }
              })
              .state('app.flowDisplay',{
                  url:'/flowDisplay',
                  templateUrl:'tpl/flow/flowDisplay.html',
                  controller:'flowDisplayController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/flow/flowDisplay.js',
                                  'js/flow/display.js'
                              ]);

                          }]
                  }
              })
              
              .state('app.trafficDisplay',{
                  url:'/trafficDisplay',
                  templateUrl:'tpl/flow/trafficDisplay.html',
                  controller:'trafficDisplayController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/common/utils_chart.js',                              
                                  'js/flow/trafficDisplay.js',
                                  'js/flow/display.js'
                              ]);

                          }]
                  }
              })

              .state('app.baseValueDisplay',{
                  url:'/baseValueDisplay',
                  templateUrl:'tpl/flow/baseValueDisplay.html',
                  controller:'baseValueDisplayController',
                  resolve: {
                      deps: ['$ocLazyLoad','uiLoad',
                          function ($ocLazyLoad,uiLoad) {
                              return ($ocLazyLoad,uiLoad).load([
                                  'js/flow/baseValueDisplay.js',
                                  'js/flow/display.js'
                              ]);

                          }]
                  }
              })
                
                .state('app.componentTable',{
                    url:'/components/table',
                    templateUrl:'tpl/ui_component/table_comp.html',
                    controller:'componentsTableController',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/ui_component/table_comp.js']);
                            }]
                    }
                })
                
                .state('app.gchart',{
                    url:'/components/chart',
                    templateUrl:'tpl/ui_component/chart.html',
                    controller:'componentsChartController',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/ui_component/chart.js']);
                            }]
                    }
                })
                .state('app.componentFileUpload',{
                    url:'/components/fileupload',
                    templateUrl:'tpl/ui_component/fileupload.html',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/ui_component/fileupload/UploadController.js']);
                            }]
                    }
                })

                .state('app.datepicker',{
                    url:'/components/datepicker',
                    templateUrl:'tpl/ui_component/datepicker.html',
                    controller:'DatepickerCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad','uiLoad',
                            function ($ocLazyLoad,uiLoad) {
                                return ($ocLazyLoad,uiLoad).load([
                                    'js/ui_component/datepicker.js']);
                            }]
                    }                    
                })         
                
                .state('app.progressbar',{
                    url:'/components/progressbar',
                    templateUrl:'tpl/ui_component/progressbar.html'               
                })                  
                .state('app.componentFileDownload',{
                    url:'/components/filedownload',
                    templateUrl:'tpl/ui_component/filedownload.html'
                })
              .state('app.page', {
                  url: '/page',
                  template: '<div ui-view class="fade-in-down"></div>'
              })
              .state('app.page.profile', {
                  url: '/profile',
                  templateUrl: 'tpl/user/page_profile.html'
              })
              .state('app.page.setting',{
                    url:'/setting',
                    templateUrl:'tpl/common/settings.html'
                })
              .state('app.docs', {
                  url: '/docs',
                  templateUrl: 'tpl/docs.html'
              })
              
              .state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-up-big"></div>'
              })
              .state('login', {
                  url: '/login',
                  //template: '<div ui-view class="fade-in-up-big"></div>',
                  templateUrl: 'tpl/login/login.html',
                  controller:'SigninFormController',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/login/signin.js'] );
                      }]
                  }
              })
              .state('logout', {
                  url: '/logout',
                  templateUrl: 'tpl/login/login.html',
                  controller:'SignoutFormController',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/login/signin.js'] );
                      }]
                  }
              })
              .state('401', {
                  url: '/401',
                  templateUrl: 'tpl/page_401.html'
              });
              
          
			      
			              
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location, $window, AuthenticationService) {
		        return {
		        	'request': function(config) {
		        		var useAuthTokenHeader = true;
		        		var isRestCall = config.url.indexOf('rest/') >= 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	},
		        	 'requestError': function(rejection) {
		                 return $q.reject(rejection);
		             },
		             /* Set Authentication.isAuthenticated to true if 200 received */
		             'response': function (response) {
		                 if (response != null && response.status == 200 && $window.sessionStorage.token && !AuthenticationService.isAuthenticated) {
		                     AuthenticationService.isAuthenticated = true;
		                 }
		                 return response || $q.when(response);
		             },
		             /* Register error provider that shows message on failed requests or redirects to login page on
		 			 * unauthenticated requests */
		             'responseError': function(rejection) {
		            	 if (rejection != null && rejection.status === 401 && ($window.sessionStorage.token || AuthenticationService.isAuthenticated)) {
		                     delete $window.sessionStorage.token;
		                     AuthenticationService.isAuthenticated = false;
		                     $location.path("/login");
		                 }
		                 return $q.reject(rejection);
		        	}
		        };
		    }
	    );
      }
    ]
  ).run(function($rootScope, $location, $window, $cookieStore, UserService, AuthenticationService, RoleService) {
	  'use strict';
		
	// enumerate routes that don't need authentication
	  var routesThatDontRequireAuth = ['/login'];
	  var routesForAdmin = ['/app/user'];
	  var routesForControll = ['/app/controller'];
	  var routesForNode = ['/app/netNode'];
	  var routesForPo = ['/app/po'];
	  var routesForBackup = ['/app/backup'];
      var routesForCleanDev = ['/app/cleandev'];
	  
	  // check if current location matches route  
	  var routeClean = function (route) {
	    return _.find(routesThatDontRequireAuth,
	      function (noAuthRoute) {
	        return route.indexOf(noAuthRoute) >= 0;
	      });
	  };
	  // check if current location matches route  
	  var routeAdmin = function (route) {
	    return (_.find(routesForAdmin,
	      function (noAuthRoute) {
	        return route.indexOf(noAuthRoute) >= 0;
	      }) ||
	      _.find(routesForControll,
	    	      function (noAuthRoute) {
	    	        return route.indexOf(noAuthRoute) >= 0;
	      }) ||
	      _.find(routesForNode,
	    	      function (noAuthRoute) {
	    	        return route.indexOf(noAuthRoute) >= 0;
	      }) ||
	      _.find(routesForPo,
	    	      function (noAuthRoute) {
	    	        return route.indexOf(noAuthRoute) >= 0;
	      }) ||
	      _.find(routesForBackup,
	    	      function (noAuthRoute) {
	    	        return route.indexOf(noAuthRoute) >= 0;
	      })||
        _.find(routesForCleanDev,
            function (noAuthRoute) {
                return route.indexOf(noAuthRoute) >= 0;
            }));
	  };
	  $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams) {
	    // if route requires auth and user is not logged in
	    if (!routeClean($location.url()) && !AuthenticationService.isAuthenticated) {
	      // redirect back to login
	      //ev.preventDefault();
	      $location.path('/login');
	    }
	    else if (routeAdmin($location.url()) && !RoleService.validateRoleAdmin($rootScope.user)) {
	      // redirect back to login
	      //ev.preventDefault();
	      $location.path('/401');
	    }
	  });
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.$on("$routeChangeSuccess", function(userInfo) {
		    console.log(userInfo);
		  });
		  $rootScope.$on("$routeChangeError", function(event, current, previous, eventObj) {
		    if (eventObj.authenticated === false) {
		      $location.path("/login");
		    }
		  });
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles == undefined) {
				return false;
			}
			
			for (var i=0; i<$rootScope.user.roles.length; i++) {
				if ($rootScope.user.roles[i] == role) {
					return true;
				}
			}
			return false;
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			delete $window.sessionStorage.token;
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		//$location.path("/login");
		var authToken = $window.sessionStorage.token;
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				//$rootScope.user = angular.fromJson(user);
				$location.path(originalPath);
			});
		} else {
			$location.path("/login");
		};
		
		$rootScope.initialized = true;
	});

