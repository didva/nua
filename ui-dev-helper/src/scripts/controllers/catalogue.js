angular.module('bcApp').controller('BcCatalogueController', ['bookService', '$scope', '$routeParams', '$location', '$route', function(bookService, $scope, $routeParams, $location, $route) {
	var currentPage = parseInt($location.search().currentPage);
	if(!currentPage || currentPage < 1) {
		currentPage = 1;
	}
	var numPerPage = 10;
	$scope.items = { 
		maxSize : 5,		
		currentPage : currentPage,
		numPerPage : numPerPage,
		total : currentPage * numPerPage
	}
	$scope.$on('$routeUpdate',function(e) {
		if(!$location.search().currentPage) {
   			$route.reload();
   		}
	});
	$scope.searchText = $routeParams.searchText;
	var getBooks = function(skip, limit) {
	    bookService.search(skip, limit, $scope.searchText).then(function(data){
	        $scope.books = data.books;
	        $scope.items.total = data.total;
	    });
	};
	var getBegin = function() {
		return ($scope.items.currentPage - 1) * $scope.items.numPerPage;
	};

	$scope.pageChanged = function() {
		var begin = getBegin();
		$location.search("currentPage", $scope.items.currentPage);
		getBooks(begin, $scope.items.numPerPage);
	};
	getBooks(getBegin(), $scope.items.numPerPage);
}]);