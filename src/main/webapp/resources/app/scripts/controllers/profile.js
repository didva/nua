angular.module('bcApp').controller('BcProfileController', ['userService', '$scope', 'authService', '$compile', function(userService, $scope, authService, $compile) {
	$scope.user = authService.getCurrentUser();
    $scope.$watch(authService.getCurrentUser, function(newValue){
        $scope.user = newValue;
    });

    $scope.changePassword = function() {
    	if($scope.newPassword != $scope.newPasswordVerification) {
    		alert('Пароли не совпадают');
    		return;
    	}
    	
		userService.changePassword($scope.user, $scope.newPassword).then(function() {
			var el = document.createElement('bc-closable');
			el.setAttribute('text', 'Пароль успешно изменен!');
			var infobox = angular.element(el);
			$compile( infobox )( $scope );
			angular.element($('.profile--change-password-info')).append(infobox);
		}, function(){
			alert('Error while changing password');
		});
    }
}]);