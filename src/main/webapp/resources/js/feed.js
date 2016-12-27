/**
 * Created by Orlov on 30.11.2016.
 */

var app = angular.module('feedApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache', 'angular-carousel', 'flow']);

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

    $http.get('/users/loggedUser')
        .then(function(response) {
            $scope.loggedUser = response.data;
        });

    $http.get("/users/loggedUser/bookmarks")
        .then(function (response) {
            $scope.favs = response.data;
        });


    $scope.createEditIdeaDialog = function(ev,ideaId)  {

        $scope.ideaId = ideaId;

        $http.get("/ideas/" + ideaId)
            .then(function(response){
                $scope.editIdeaName = response.data.ideaName;
                $scope.editIdeaText = response.data.ideaText;
                $scope.editIdeaTags = [];
                angular.forEach(response.data.tags, function(tag) {
                    console.log(tag);
                    $scope.editIdeaTags.push(tag.tagName);
                });
                $scope.editIdeaTags = $scope.editIdeaTags.toString();
                console.log($scope.editIdeaTags);
                $scope.editedIdeaAttachments = [];
                angular.forEach(response.data.attachments, function(attachment) {
                    $scope.editedIdeaAttachments.push(attachment.attachmentData);
                });
                console.log($scope.editedIdeaAttachments);
                $mdDialog.show({
                    controller: DialogController,
                    contentElement: '#editIdeaDialog',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true
                });
            });
    };

    $scope.createPostIdeaDialog = function(ev)  {

        $mdDialog.show({
            controller: DialogController,
            contentElement: '#postIdeaDialog',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
    };

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


    /*toggle menu class active*/
    $scope.isActiveFeed = true;
    $scope.isActiveMyIdeas = false;

    /*My ideas filter*/

    $scope.filterByMyIdeas = function() {
        $http.get('/users/loggedUser')
            .then(function(response) {
                $scope.loggedUser = response.data;
                var loggedUser = response.data.username;
                var loggedUserUrl = "/users/"+loggedUser+"/ideas";
                $http.get(loggedUserUrl).success(
                    function(response) {
                        $scope.ideas = response;
                        $scope.isActiveFeed = false;/*toggle menu class active*/
                        $scope.isActiveMyIdeas = true;/*toggle menu class active*/
                    }
                );
            });
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

    $scope.remove = function(ideaId) {
        $http.delete('/ideas/'+ideaId)
            .then(function(response) {
                updateFeed.getUpdatedFeed();
            });
    }

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
            })
    };

    $scope.addToFavorites = function (fav, ideaId) {

        if(fav == true){
            $http.post('/ideas/'+ideaId +'/bookmark', fav)
                .then(function (response) {
                    if (response.status == 200) {
                        updateFeed.getUpdatedFeed();
                    }
                    else {
                        alert("Failed!")
                    }
                });
        } else {
            $http.delete('/ideas/'+ideaId +'/bookmark', fav)
                .then(function (response) {
                    if (response.status == 200) {
                        updateFeed.getUpdatedFeed();
                    }
                    else {
                        alert("Failed!")
                    }
                });
        }

    };

    $scope.filterByFav = function(favs) {
        $scope.ideas = favs;
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


app.controller('headerCtrl', function ($http, $scope) {

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

    $http.get('/users/loggedUser')
        .then(function(response) {
            $scope.currentuser = response.data;
        });
});

app.controller('postCtrl', function ($http, $scope, $mdDialog, updateFeed) {

    $scope.imageStrings = [];

    $scope.processFiles = function(files){
        angular.forEach(files, function(flowFile, i){
            var fileReader = new FileReader();
            fileReader.onload = function (event) {
                var uri = event.target.result;
                $scope.imageStrings[i] = uri;
            };
            fileReader.readAsDataURL(flowFile.file);
        });
    };

    $scope.postIdea = function() {

        if ($scope.ideaTags != null) {
            var tags = $scope.ideaTags.split([',']);
        }
        else {
            tags = [];
        }

        var ideaDTO = {
            "ideaName" : $scope.ideaName,
            "ideaText" : $scope.ideaText,
            "tags" : tags,
            "attachments" : $scope.imageStrings
        };
        var config = {
            headers: {'Content-Type': 'application/json' }
        };
        $http.post('/ideas', ideaDTO, config)
            .then(function (response) {
                if (response.status == 200) {
                    $mdDialog.cancel();
                    updateFeed.getUpdatedFeed();
                }
                else {
                    alert("Failed!")
                }
            });
    };
});

app.controller('editCtrl', function ($http, $scope, $mdDialog, updateFeed) {

    $scope.imageStrings = [];

    $scope.removeAttachment = function(attachment) {
        var index = $scope.editedIdeaAttachments.indexOf(attachment);
        $scope.editedIdeaAttachments.splice(index, 1);
    };

    $scope.processFiles = function(files){
        angular.forEach(files, function(flowFile, i){
            var fileReader = new FileReader();
            fileReader.onload = function (event) {
                console.log(flowFile.file);
                var uri = event.target.result;
                $scope.imageStrings[i] = uri;
            };
        });
    };

    $scope.editIdea = function(ideaId) {

        var uploadAttachments = $scope.imageStrings.concat($scope.editedIdeaAttachments);

        if ($scope.editIdeaTags != null) {
            var tags = $scope.editIdeaTags.split([',']);
        }
        else {
            var tags = [];
        }
        var ideaDTO = {
            "ideaName" : $scope.editIdeaName,
            "ideaText" : $scope.editIdeaText,
            "tags" : tags,
            "attachments" : $scope.imageStrings
        };
        var config = {
            headers: {'Content-Type': 'application/json' }
        };
        $http.put('/ideas/'+ideaId, ideaDTO, config)
            .then(function (response) {
                if (response.status == 200) {
                    alert("Posted!");
                    $mdDialog.cancel();
                    updateFeed.getUpdatedFeed();
                }
                else {
                    alert("Failed!")
                }
            });
    }
});
