'use strict';

angular.module('clientApp')
    .controller('ViewpostCtrl', function ($scope, $http, $routeParams, alertService, $location, userService) {

      $scope.user = userService;
      $scope.params = $routeParams;
      $scope.postId = $scope.params.postId;

      $scope.viewPost = function() {
        $http.get('/app/post/' + $scope.postId)
            .error(function(data) {
              alertService.add('danger', data.error.message);
              console.log("In error")
            })
            .success(function(data) {
              console.log("in success")
              $scope.post = data;
            });
      };

      $scope.viewPost();
  });
