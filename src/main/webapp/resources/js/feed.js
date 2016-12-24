/**
 * Created by Orlov on 30.11.2016.
 */
var app = angular.module('feedApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache', 'angular-carousel']);

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

app.controller('feedCtrl', function($http, $scope, updateFeed, $mdDialog) {

    updateFeed.getUpdatedFeed();

    /*show_slider*/
    $scope.isActiveSlider = true;
    $scope.displayToggle = function () {
        $scope.isActiveSlider = !$scope.isActiveSlider;
    };
/*Tag filter*/
    $scope.filterByTag = function(tag) {
        var tagUrl = "/tags/"+tag;
        $http.get(tagUrl).success(
            function(response) {
                $scope.ideas = response;
            }
        );
    };
/*user filter*/
    $scope.filterByUser = function(user) {
        var userUrl = "/users/"+user+"/ideas";
        $http.get(userUrl).success(
            function(response) {
                $scope.ideas = response;
            }
        );
    };



    $scope.commentIdea = function(ideaId) {
        var commentData = {
            commentText: this.commentText
        };
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
    /*counter*/
    $scope.sizeOf = function(obj) {
        return Object.keys(obj || {}).length;
    };

    /*$scope.deleteIdea = function(ideaId) {
     ideatideleteurl = '/ideas/'+ideaId;
     $http.delete(ideatideleteurl).
     then(function() {
     updateFeed.getUpdatedFeed();
     });
     }*/

    $scope.remove = function(ideaId) {
        $http.delete('/ideas/'+ideaId)
            .then(function(response) {
                updateFeed.getUpdatedFeed();
            });
    }

    $http.get('/users/me')
        .then(function(response) {
            $scope.me = response.data;
        });

    $scope.editIdea = function(ev, ideaId)  {
        $mdDialog.show({
            controller: DialogController,
            contentElement: '#createIdeaEditDialog',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });

        var ideaUrl = "/ideas/" + ideaId;
        $http.get(ideaUrl)
            .then(function(response){
                $scope.editIdeaName = response.data.ideaName;
                $scope.editIdeaText = response.data.ideaText;

                var tagsArray = response.data.tags;
                $scope.editIdeaTags = '';
                tagsArray.forEach(function (tag, index) {
                    if(index == tagsArray.length - 1){
                        $scope.editIdeaTags += tag.tagName;
                    } else {
                        $scope.editIdeaTags += tag.tagName + ',';
                    }
                });

            })

    };

    $scope.removed = function(commentId, ideaId) {
        $http.delete('/ideas/'+ideaId+'/comments/'+commentId)
            .then(function(response) {
                updateFeed.getUpdatedFeed();
            });
    };

    $scope.like = function(userMark,ideaId) {

        var ideadata = {
            mark: userMark
        };

        var config = {
            headers: {
                'Content-Type': 'application/json'
            }
        }
        $http.post('/ideas/'+ideaId+'/vote', ideadata, config)
            .then(function (response) {
                if (response.status == 200) {
                    updateFeed.getUpdatedFeed();
                }
                else {
                    alert("Failed!")
                }
            });
    };
    $scope.orderOptions = {
        field: '-creationDate'
    };
    $scope.reverseOptions = {};
    $scope.setOrder = function(field, reverse) {
        if ($scope.orderOptions.field != field) {
            $scope.reverseOptions[field] = reverse;
        } else {
            $scope.reverseOptions[field] = !$scope.reverseOptions[field];
        }
        $scope.orderOptions.field = field;

        return false;
    }
});

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

    $http.get('/users/me')
        .then(function(response) {
            $scope.currentuser = response.data;
        });
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
    }]);


/*directive for picture preview in add idea form
 .directive("fileinput", [function() {
 return {
 scope: {
 fileinput: "=",
 filepreview: "="
 },
 link: function (scope, element) {
 element.bind("change", function (changeEvent) {
 scope.fileinput = changeEvent.target.files[0];
 var reader = new FileReader();
 reader.onload = function (loadEvent) {
 scope.$apply(function () {
 scope.filepreview = loadEvent.target.result;
 });
 }
 reader.readAsDataURL(scope.fileinput);

 });
 }
 }}]);*/
