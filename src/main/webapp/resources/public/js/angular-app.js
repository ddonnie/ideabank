/**
 * Created by Orlov on 25.11.2016.
 */
angular.module("mainApp", [])
    .controller("signupController", function ($scope, $http) {

        $scope.SendData = function () {
            var data = {
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                login: $scope.login,
                password: $scope.password
            };

            var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            }
            $http.post('/users', data, config)
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