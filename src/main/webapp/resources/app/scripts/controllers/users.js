angular.module('bcApp').controller('BcUsersController', ['userService', '$scope', '$compile', function(userService, $scope, $compile) {
	$scope.users = {};
	$scope.newUser = {};
	var getUsers = function() {
	    userService.getAll().then(function(data){
	        $scope.users = data;
	    });
	};
	getUsers();
	$scope.addUser = function() {
		$scope.newUser.roles = [];
		if($scope.newUser.isAdmin) {
			$scope.newUser.roles.push('ROLE_ADMIN');
		}
		if($scope.newUser.isUser) {
			$scope.newUser.roles.push('ROLE_USER');	
		}

		userService.create($scope.newUser).then(function(data) {
			var el = document.createElement('bc-closable');
			el.setAttribute('text', 'Пользователь успешно добавлен с именем:' + data.username +' и паролем: ' + data.password);
			$scope.newUser.id = data.id;
			$scope.users.push($scope.newUser);
			$scope.newUser = {};
			var infobox = angular.element(el);
			$compile( infobox )( $scope );
			angular.element($('.users-add-form--info-box')).append(infobox);
		}, function(){
			alert('Error while adding user');
		});
	}
}]);