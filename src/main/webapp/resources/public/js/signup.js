/**
 * Created by Orlov on 25.11.2016.
 */
angular.module("signupApp", [])
    .controller("signupController", function ($scope, $http) {

        $scope.Register = function () {
            var data = {
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                username: $scope.username,
                password: $scope.password
            };

            var config = {
                headers : {
                    'Content-Type': 'application/json',
                    "X-Login-Ajax-call": 'true'
                }
            }
            $http.post('/registration', data, config)
                .success(function (data, status, headers, config) {
                    $scope.PostDataResponse = status;
                })
                .error(function (data, status, header, config) {
                    $scope.ResponseDetails = "Data: " + data +
                        "<hr />status: " + status +
                        "<hr />headers: " + header +
                        "<hr />config: " + config;
                });
        };

    });