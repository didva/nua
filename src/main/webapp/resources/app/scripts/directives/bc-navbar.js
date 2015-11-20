angular.module('bcApp').directive('bcNavbar', function(authService){
    return {
        restrict: 'E',
        templateUrl: 'views/directives/navbar.html',
    };
});