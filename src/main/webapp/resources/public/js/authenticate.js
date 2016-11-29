/**
 * Created by Orlov on 28.11.2016.
 */
angular.module("authenticateApp", [])
    .controller("authenticateController", function ($scope, $http) {

        $scope.Authenticate = function () {
            var data = {
                username: $scope.username,
                password: $scope.password
            };

            var config = {
                headers : {
                    'Content-Type': 'application/json',
                    "X-Login-Ajax-call": 'true'
                }
            }
            $http.post('/authenticate', data, config)
                .then(function(response) {
                    if (response.data == 'OK') {
                        window.location.replace('/resources/feed.html');
                    }
                    else {
                        alert("Access denied");
                    }
                });
        };

    });