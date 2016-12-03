/**
 * Created by Orlov on 30.11.2016.
 */

angular.module('feedApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache'])
    .controller('feedCtrl', function($http, $scope) {

        $http.get('/ideas')
            .then(function(response) {
                $scope.ideas = response.data;
            });

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
    });
