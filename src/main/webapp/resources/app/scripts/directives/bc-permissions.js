angular.module('bcApp').directive('bcPermissions', ['authService', function(authService){
    return {
        restrict: 'E',
        templateUrl: 'views/directives/permissions.html',
        scope: { user : '=' },
        link : function(scope){        	
        	scope.$watch(function() {return scope.user;}, function(){
	        	if(scope.user.roles) {
		        	scope.user.isAdmin = scope.user.roles.indexOf('ROLE_ADMIN') > -1;
		        	scope.user.isUser = scope.user.roles.indexOf('ROLE_USER') > -1;
	        	}
        	});
        	scope.changedUser = function() {
        		scope.user = angular.copy(scope.user);
        		var index = scope.user.roles.indexOf('ROLE_USER');
        		if(scope.user.isUser && index < 0) {
        			scope.user.roles.push('ROLE_USER');
        		} else if(index > -1) {
        			scope.user.roles.splice(index, 1);
        		}
        	};
        	scope.changedAdmin = function() {
        		scope.user = angular.copy(scope.user);
        		var index = scope.user.roles.indexOf('ROLE_ADMIN');
        		if(scope.user.isAdmin && index < 0) {
        			scope.user.roles.push('ROLE_ADMIN');
        		} else if(index > -1) {
        			scope.user.roles.splice(index, 1);
        		}
        	}
        }
    };
}]);