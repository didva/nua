angular.module('bcApp').controller('BcBookletController', ['pageService', 'bookService', '$scope', '$routeParams', function(pageService, bookService, $scope, $routeParams) {
	var width = 430;
	var maxIndex = 0;
	var getImg = function(imgBase64) {
		return '<img class="booklet-viewer--page" src="data:image/jpg;base64,'  + imgBase64 + '" />';
	}
	var addPage = function(num) {
		pageService.getPage($routeParams.bookId, num).then(function(data){
			$(".booklet-viewer").booklet('add', "end", getImg(data.content));
			$('.booklet-viewer--page').css('width', width);
		}, function() { 
			alert('Not allowed');
		});
	}
	bookService.get($routeParams.bookId).then(function(data){
        $scope.book = data;
		pageService.getPage($routeParams.bookId, 1).then(function(data) {
	        var booklet = $(".booklet-viewer").append(getImg(data.content));
			var config = {
				closed: true,
				autoCenter: true,
				arrows: true,			
				width:  width * 2,
				height: 580,
				change: function(event, data) {
					if(maxIndex < data.index) {
						maxIndex = data.index;
						if($scope.book.pageCount >= (maxIndex + 2)) {
							addPage(maxIndex + 2);
							if($scope.book.pageCount >= (maxIndex + 3)) {
								addPage(maxIndex + 3);						
							}
						}
					}
				}
			};
			booklet.booklet(config);
			addPage(2);
			addPage(3);
		}, function() { 
			alert('Not allowed');
		});
    });		
}]);