angular.module('bcApp').factory('authService', ['$http', '$cookies', 'base64', '$timeout', 'appUrl', function($http, $cookies, base64, $timeout, appUrl) {
	var tokenURL = appUrl + 'oauth/token';
	var authURL = appUrl + 'rest/authentication';

	var authService = {};
	var user = {};
    var refreshUser = function(time) {
        $timeout(getUserData, time);
    }
    var setTokenHeader = function(token) {
        $cookies.put('token', token);
        $http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
    }
	var getUserData = function() {
		$http.get(authURL).then(function(resp) {
			user = resp.data;
			if(!user.token) {
				delete $http.defaults.headers.common['Authorization'];
				return;
			}
			setTokenHeader(user.token);
			refreshUser(user.expiresIn);
		}, function(err) {
			delete $http.defaults.headers.common['Authorization'];
		});
	}
	getUserData();
	authService.getCurrentUser = function() {
		return user;
	};
	authService.isAuthencticated = function() {
		return user.token ? true : false;
	};
	authService.isAdmin = function() {
		if(!user.roles) {
			return false;
		}
		return user.roles.indexOf('ROLE_ADMIN') > -1;
	};
	authService.login = function(name, password) {
        var config = {headers:  {
                'Authorization': 'Basic ' + base64.encode(name + ':' + password),
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        };

        $http.post(tokenURL, $.param({grant_type: 'client_credentials'}), config).then(function(resp){
        	setTokenHeader(resp.data.access_token);
            getUserData();
            refreshUser((resp.data.expires_in - 1) * 1000);
        }, function(err) {
            alert("Ошибка входа");            
        });
	};
    return authService;
}]);