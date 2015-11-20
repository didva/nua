angular.module('bcApp').factory('pageService', ['$q', '$http', 'appUrl', function($q, $http, appUrl) {
	var pageServiceURL = appUrl + 'rest/pages/';
	var pageService = {};

	pageService.getPage = function(bookId, pageId) {
        var defer = $q.defer();
        $http.get(pageServiceURL + bookId + '/' + pageId).then(function(resp){
            if(!resp.data) {
                defer.reject();
                console.log('Error while getting page');
                return;
            }
            defer.resolve(resp.data);
        }, function(e){
            defer.reject();
            console.log('Error while getting page');
        }); 
        return defer.promise;
	};
	return pageService;
}]);