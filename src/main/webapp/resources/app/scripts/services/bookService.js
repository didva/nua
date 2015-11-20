angular.module('bcApp').factory('bookService', ['$q', '$http', 'appUrl', function($q, $http, appUrl) {
	var bookServiceURL = appUrl + 'rest/books';
	var bookService = {};

	bookService.get = function(bookId) {
		var defer = $q.defer();
        $http.get(bookServiceURL + '/' + bookId).then(function(resp){
            if(!resp.data) {
            	defer.reject();
                console.log('Error while getting book');
                return;
            }
            defer.resolve(resp.data);
        }, function(e){
        	defer.reject();
            console.log('Error while getting book');
        }); 
		return defer.promise;
	};

    bookService.search = function(skip, limit, searchText) {
        var defer = $q.defer();
        var url = bookServiceURL + '?skip=' + skip + '&limit=' + limit;
        if(searchText) {
            url += '&searchText=' + searchText;
        }
        $http.get(url).then(function(resp){
            if(!resp.data) {
                defer.reject();
                console.log('Error while searching books');
                return;
            }
            defer.resolve(resp.data);
        }, function(e) {
            defer.reject();
            console.log('Error while searching books');
        });
        return defer.promise;
    }

	return bookService;
}]);