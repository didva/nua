angular.module('bcApp').directive('bcLanguages', ['$translate', '$cookies', function($translate, $cookies){
    return {
        restrict: 'E',
        templateUrl: 'views/directives/languages.html',
        link: function(scope) {
            scope.$translate = $translate;
            scope.setLanguage = function(lang){
                $cookies.put('language', lang);
                $translate.use(lang);
            }
        }
    };
}]);