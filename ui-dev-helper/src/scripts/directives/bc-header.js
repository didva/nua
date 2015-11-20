angular.module('bcApp').directive('bcHeader', ['authService', function(authService){
    return {
        restrict: 'E',
        templateUrl: 'views/directives/header.html'
    };
}]);