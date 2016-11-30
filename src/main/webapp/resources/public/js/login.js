/**
 * Created by Orlov on 28.11.2016.
 */
angular.module('loginApp', [])
    .controller('loginCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.login = function () {
            var data = 'username='+$scope.username+'&password='+$scope.password;
            var config = {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Login-Ajax-call': 'true'
                }
            };
            $http.post('/authenticate', data, config)
                .then(function (response) {
                    if (response.data == 'ok') {
                        alert("Woohoo!");
                        window.location.replace('/resources/feed.html');
                    }
                    else {
                        alert("Access denied!");
                    }
                });
        }
    }]);