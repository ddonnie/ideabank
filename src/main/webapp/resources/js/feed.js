/**
 * Created by Orlov on 30.11.2016.
 */

angular.module('feedApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache'])
    .controller('feedCtrl', function($http, $scope, $mdDialog) {

        $http.get('/ideas')
            .then(function(response) {
                $scope.ideas = response.data;
            });


        $scope.commentIdea = function(ideaId) {
            var commentData = {
                commentText: this.commentText
            }
            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            var commentssurl = "/ideas/" + ideaId + "/comments";
            $http.post(commentssurl,commentData,config)
                .then(function(response) {
                    window.location.replace('/resources/feed.html');
                })
        };

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
        };


        $scope.createIdea = function(ev)  {
            $mdDialog.show({
                controller: DialogController,
                contentElement: '#createIdeaDialog',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true
            });
        };

        $scope.postIdea = function() {

            var ideaSplitTags = $scope.ideaTags.split([',']);

            var ideadata = {
                ideaName: $scope.ideaName,
                ideaText: $scope.ideaText,
                tags: ideaSplitTags
            };

            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
            $http.post('/ideas', ideadata, config)
                .then(function (response) {
                    if (response.status == 200) {
                        window.location.replace('/resources/feed.html');
                    }
                    else {
                        alert("Failed!")
                    }
                });
        };

        function DialogController($scope, $mdDialog) {
            $scope.hide = function() {
                $mdDialog.hide();
            };

            $scope.cancel = function() {
                $mdDialog.cancel();
            };

            $scope.answer = function(answer) {
                $mdDialog.hide(answer);
            };
        }


        $scope.isActive = false;
        $scope.activeButton = function() {
            $scope.isActive = !$scope.isActive;
        }

        $scope.remove = function(ideaId) {
            $http.delete('/ideas/'+ideaId)
                .then(function(response) {
                    window.location.replace('/resources/feed.html');
                });
        }

    });
