angular.module('bcApp').directive('bcUserRow', ['userService', function(userService) {    
    var hasRole = function(roles, roleName){
        if(!roles) {
            return false;
        }
        return roles.indexOf(roleName) > -1;
    }
    var findUserIndex = function(users, id) {
        for(var i = 0; i < users.length; ++i) {
            if(users[i].id == id) {
                return i;
            }
        }
        return -1;
    }
    var cloneUser = function(user) {
        return jQuery.extend({}, user);
    }
    
    return {
        restrict: 'A',
        templateUrl: 'views/directives/user-row.html',
        scope: { 
            user : '=',
            users : '='
        },
        link: function(scope, element) {
            scope.user = cloneUser(scope.user);
            scope.editMode = false;
            scope.cancel = function(){
                scope.editMode = false;
                scope.user = cloneUser(scope.users[findUserIndex(scope.users, scope.user.id)]);
            };
            scope.edit = function(){
                scope.editMode = true;                
            };
            
            scope.save = function(){
                scope.editMode = false;
                var user = scope.user;
                userService.update(user).then(function(){
                    var index = findUserIndex(scope.users, user.id);
                    if(index > -1) {
                        scope.users[index] = cloneUser(user);
                    }
                }, function(e){
                    scope.user = cloneUser(scope.users[findUserIndex(scope.users, user.id)]);
                    alert('Error while updating user');
                });           
            };
            scope.delete = function(){
                var user = scope.user;
                userService.delete(user).then(function(){
                    var index = findUserIndex(scope.users, user.id);
                    if(index > -1) {
                        scope.users.splice(index, 1);
                    }
                    element.remove();
                    scope.$destroy();
                }, function(){
                    alert('Error while deleting user');
                });
            };
        }
    };
}]);