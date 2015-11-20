angular.module('bcApp').directive('bcClosable', function() {
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'views/directives/closable.html',
        link: function(scope, element, attrs) {
            scope.text = attrs.text;
            scope.close = function(){                
                element.remove();
                scope.$destroy();
            };
        }
    };
});