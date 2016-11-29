/**
 * Created by Orlov on 28.11.2016.
 */
angular.module("feedApp", [])
    .controller("feedController", function ($scope, $http) {

        $scope.Logout = function() {
            $http.post('logout', {}).finally(function() {
                $scope.authenticated = false;
                $location.path("/");
            });
        };

    });