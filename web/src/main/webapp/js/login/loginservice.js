
app.factory('RoleService', function ($http) {

	  'use strict';

	  var adminRole = 'ADMIN';
	  var userRole = 'USER';
      var tenantRole="TENANT";

	  return {
	    validateRoleAdmin: function (currentUser) {
	      return currentUser ? _.contains(currentUser.roles, adminRole) : false;
	    },

	    validateRoleUser: function (currentUser) {
	      return currentUser ? _.contains(currentUser.roles, userRole) : false;
	    },
        validateRoleTenant: function (currentUser) {
            return currentUser ? _.contains(currentUser.roles, tenantRole) : false;
        }
	  };
	});


	app.factory('AuthenticationService', function () {

	  'use strict';

	  return {
	    currentUser: null,
	    isAuthenticated: false,
        isAdmin: false
	  };
	});
	
app.factory('UserService', function($resource) {
	
	return $resource('rest/users/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}
			}
		);
});