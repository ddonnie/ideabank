/**
 * Created by Orlov on 30.11.2016.
 */

angular.module('signApp', ['ngMaterial', 'ngMessages', 'material.svgAssetsCache'])
    .controller('signCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.login = function () {
            var logdata = 'username=' + $scope.username + '&password=' + $scope.password;
            var config = {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Login-Ajax-call': 'true'
                }
            };
            $http.post('/authenticate', logdata, config)
                .then(function (response) {
                    if (response.data == 'ok') {
                        /*alert("Woohoo!");*/
                        window.location.replace('/resources/feed.html');
                    }
                    else {
                        alert("Access denied!");
                    }
                });
        };

        $scope.register = function () {

            var regdata = {
                firstName: $scope.firstname,
                lastName: $scope.lastname,
                username: $scope.username,
                password: $scope.password
            };

            var config = {
                headers: {
                    'Content-Type': 'application/json',
                }
            }
            $http.post('/registration', regdata, config)
                .then(function (response) {
                    if (response.status == 200) {
                        alert("Wohoo!")
                        window.location.replace('/resources/feed.html');
                    }
                    else {
                        alert("Failed!")
                    }
                });
        };

    }]);
