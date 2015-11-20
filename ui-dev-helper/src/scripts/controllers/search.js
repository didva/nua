angular.module('bcApp').controller('BcSearchController', ['$scope', '$location', function($scope, $location) {
	$scope.search = function(){
		$location.path('/catalogue/' + $scope.searchText);
		$scope.searchText = '';
	}
}]);