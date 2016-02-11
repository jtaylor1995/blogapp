'use strict';

angular.module('clientApp')
  .controller('ViewcommentCtrl', function ($scope, $http, $routeParams, alertService, $location, userService) {

        $scope.user = userService;
        $scope.params = $routeParams;
        $scope.commentId = $scope.params.commentId;

        $scope.viewComment = function() {
          $http.get('/app/comment/' + $scope.commentId)
              .error(function(data) {
                alertService.add('danger', data.error.message);
              })
              .success(function(data) {
                console.log("DATA" + data.comment);
                $scope.comment = data;
              });
        };

        $scope.viewComment();
  });

