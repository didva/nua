angular.module('bcApp').directive('bcUsers', ['authService', function(authService){
    return {
        restrict: 'E',
        templateUrl: 'views/directives/users.html',
        link: function(scope) {
        	scope.isAdmin = authService.isAuthencticated();

        	scope.authService = authService;
        	scope.$watch('authService.isAdmin()', function(newValue){
				scope.isAdmin = newValue;
        	});
        }
    };
}]);