angular.module('bcApp').directive('bcProfile', ['authService', function(authService){
    return {
        restrict: 'E',
        templateUrl: 'views/directives/profile.html',
        link: function(scope) {
        	scope.isAuth = authService.isAuthencticated();

        	scope.authService = authService;
            scope.$watch('authService.isAuthencticated()', function(newValue){
                scope.isAuth = newValue;
            });
        }
    };
}]);