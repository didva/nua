angular.module('bcApp').controller('BcMainController', ['$http', '$scope', function($http, $scope) {
    $http.get('http://localhost:8080/bookschecker-reborn/rest/main').success(function(data){
        $scope.generalInfo = data;
    });
}]);