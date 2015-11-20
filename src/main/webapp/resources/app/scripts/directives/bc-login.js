angular.module('bcApp').directive('bcLogin', ['authService', function(authService){    
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'views/directives/login.html',
        link: function(scope) {
            var loginWrapper = $('.login-wrapper');
            scope.header = loginWrapper.closest('.header-wrapper').length > 0;
            scope.footer = loginWrapper.closest('.footer-wrapper').length > 0;

        	scope.isAuth = authService.isAuthencticated();
        	scope.$watch(authService.isAuthencticated, function(newValue){
				scope.isAuth = newValue;
        	});
            scope.login = function() {
                authService.login(scope.username, scope.password);
            } 	
        }
    };
}]);