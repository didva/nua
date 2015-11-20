angular.module('bcApp').factory('userService', ['$q', '$http', 'appUrl', function($q, $http, appUrl) {
	var userServiceURL = appUrl + 'rest/users/';
    var changePasswordURL = userServiceURL + 'password/';
	var userService = {};

	userService.update = function(user) {
		var defer = $q.defer();
        $http.put(userServiceURL + user.id, user).then(function(resp){
            if(!resp.data.success) {
            	defer.reject();
                console.log('Error while updating user');
                return;
            }
            defer.resolve(user);
        }, function(e){
        	defer.reject();
            console.log('Error while updating user');
        }); 
		return defer.promise;
	};

	userService.delete = function(user) {
		var defer = $q.defer();
        $http.delete(userServiceURL + user.id).then(function(resp){
            if(!resp.data.success) {
            	defer.reject();
                console.log('Error while deleting user');
                return;
            }
            defer.resolve(user);
        }, function(e){
        	defer.reject();
            console.log('Error while deleting user');
        }); 
		return defer.promise;
	}

    userService.create = function(user) {
        var defer = $q.defer();
        $http.post(userServiceURL).then(function(resp){
            if(!resp.data.success) {
                defer.reject();
                console.log('Error while creating user');
                return;
            }
            defer.resolve(resp.data);
        }, function(e){
            defer.reject();
            console.log('Error while creating user');
        }); 
        return defer.promise;
    }

    userService.changePassword = function(user, newPassword) {
        var defer = $q.defer();
        $http.post(changePasswordURL + user.id, newPassword).then(function(resp){
            if(!resp.data.success) {
                defer.reject();
                console.log('Error while changing password');
                return;
            }
            defer.resolve();
        }, function(e){
            defer.reject();
            console.log('Error while changing password');
        }); 
        return defer.promise;
    }

    userService.getAll = function() {
        var defer = $q.defer();
        $http.get(userServiceURL).then(function(resp){
            if(!resp.data) {
                defer.reject();
                console.log('Error while getting users');
                return;
            }
            defer.resolve(resp.data);
        }, function(e){
            defer.reject();
            console.log('Error while getting users');
        }); 
        return defer.promise;
    };
	return userService;
}]);