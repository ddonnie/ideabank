/**
 * Created by Orlov on 25.11.2016.
 */
angular.module("signupApp", [])
    .controller("signupCtrl", ['$scope', '$http', function ($scope, $http) {

        $scope.register = function () {

            var data = {
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
            $http.post('/registration', data, config)
                .then(function (response) {
                    if (response.status == 200) {
                        alert("Wohoo!")
                    }
                    else {
                        alert("Failed!")
                    }
                });
        };

    }]);