/**
 * Created by Orlov on 30.11.2016.
 */
/**
 * Created by Orlov on 25.11.2016.
 */
angular.module("feedApp", [])
    .controller("feedCtrl", ['$scope', '$http', function ($scope, $http) {

        $scope.logout = function () {
            $http.post('/logout')
                .then(function (response) {
                    if (response.status == 200) {
                        window.location.reload();
                    }
                    else {
                        alert("Logout failed!");
                    }
                });
        }

    }]);