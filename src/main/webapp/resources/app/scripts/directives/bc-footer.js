angular.module('bcApp')
    .directive('bcFooter', function(){
        return {
            restrict: 'E',
            templateUrl: 'views/directives/footer.html'
        };
    });