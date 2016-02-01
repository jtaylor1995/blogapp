'use strict';

angular.module('clientApp')
  .controller('MainCtrl', function ($scope, $http) {
    $scope.getPosts = function() {
      $http.get('app/posts')
        .success(function(data) {
          $scope.posts = data;
        });
    };

    $scope.getPosts();

  });
