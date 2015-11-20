angular.module('bcApp').controller('BcBookController', ['pageService', 'bookService', '$scope', '$routeParams', '$location', function(pageService, bookService, $scope, $routeParams, $location) {
	var currentPage = parseInt($location.search().currentPage);
	if(!currentPage || currentPage < 1) {
		currentPage = 1;
	}
	var getImgSrc = function(imgBase64) {
		return 'data:image/jpg;base64,'  + imgBase64;
	}
	pageService.getPage($routeParams.bookId, currentPage).then(function(data) {		
		$(".book-viewer").iviewer({
			src : getImgSrc(data.content),
			onStartLoad : function(){
				$('.book-viewer').hide();
				$('.book-viwer--not-allowed').hide();
				$('.book-viewer--spinner').show();
			},
			onFinishLoad : function(){
				$('.book-viewer--spinner').hide();
				$('.book-viwer--not-allowed').hide();
				$('.book-viewer').show();
			},
			onErrorLoad : function() {
				$('.book-viewer--spinner').hide();
				$('.book-viwer--not-allowed').show();
			}
		});
	}, function() { 
		alert('Not allowed');
	});

	bookService.get($routeParams.bookId).then(function(data){
        $scope.book = data;
        $scope.book.currentPage = currentPage;
    });
	$scope.pageChanged = function() {
		$location.search("currentPage", $scope.book.currentPage);
	};
}]);