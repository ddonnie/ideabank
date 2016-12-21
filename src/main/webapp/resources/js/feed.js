/**
 * Created by Orlov on 30.11.2016.
 */
var app = angular.module('feedApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

// app.directive('fileModel', [ '$parse', function($parse) {
//     return {
//         restrict : 'A',
//         link : function(scope, element, attrs) {
//             var model = $parse(attrs.fileModel);
//             var modelSetter = model.assign;
//             element.bind('change', function() {
//                 scope.$apply(function() {
//                     console.log(element[0].files[0]);
//                     modelSetter(scope, element[0].files[0]);
//                 });
//             });
//         }
//     };
// }]);

app.directive('fileModel', [ '$parse', function($parse) {
    return {
        restrict : 'A',
        link : function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var isMultiple = attrs.multiple;
            var modelSetter = model.assign;
            element.bind('change', function() {
                var values = [];
                console.log('files:');
                console.log(element[0].files);
                angular.forEach(element[0].files, function (item) {
                    console.log(item);
                    values.push(item);
                });
                scope.$apply(function () {
                    if (isMultiple) {
                        modelSetter(scope, values);
                    } else {
                        modelSetter(scope, values[0]);
                    }
                });
            });
        }
    };
}]);

app.service('fileUpload', ['$http', 'updateFeed', '$mdDialog', function($http, updateFeed, $mdDialog) {
    this.uploadFileToUrl = function(uploadUrl, ideaName, ideaText, tags, ideaAttachments) {

        var fd = new FormData();
        var procTags = [];
        if (tags != null) {
            procTags = tags.split([',']);
        };
        var ideaDTO = {
            "ideaName": ideaName,
            "ideaText": ideaText,
            "tags": procTags
        };
        console.log(ideaDTO);
        fd.append('ideaDTO', JSON.stringify(ideaDTO));
        angular.forEach(ideaAttachments, function(attachment) {
            fd.append('ideaAttachments', attachment);
        });

        $http.post(uploadUrl, fd, {
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            }
        }).success(function() {
            $mdDialog.cancel();
            updateFeed.getUpdatedFeed();
        }).error(function() {
            alert("Nope");
        });
    }
} ]);

app.service('updateFeed', ['$http', '$rootScope', function($http, $rootScope) {
    this.getUpdatedFeed = function() {
        $http.get("/ideas").success(
            function(response) {
                $rootScope.ideas = response;
            });
    }
}]);

app.controller('feedCtrl', function($http, $scope, updateFeed) {

    updateFeed.getUpdatedFeed();

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
                updateFeed.getUpdatedFeed();
            })
    };


    $scope.deleteIdea = function(ideaId) {
        ideatideleteurl = '/ideas/'+ideaId;
        $http.delete(ideatideleteurl).
        then(function() {
            updateFeed.getUpdatedFeed();
        });
    }

});

app.controller('headerCtrl', function ($http, $scope, $mdDialog) {

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

});

app.controller('postCtrl', [ '$scope', 'fileUpload',
    function($scope, fileUpload) {
        $scope.postIdea = function() {

            var ideaName = $scope.ideaName;
            var ideaText = $scope.ideaText;
            var tags = $scope.ideaTags;
            var ideaAttachments = $scope.ideaAttachments;
            console.log('file is ' + angular.toJson(ideaAttachments));
            var uploadUrl = "/ideas";
            fileUpload.uploadFileToUrl(uploadUrl, ideaName, ideaText, tags, ideaAttachments);
        };
    } ]);
