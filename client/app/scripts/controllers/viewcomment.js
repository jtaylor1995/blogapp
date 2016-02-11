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
                $scope.comment = data;
              });
        };

        $scope.viewComment();

        $scope.likeComment = function() {
          var payload = {
            commentId: $scope.commentId
          };

                  $http.post('/app/likeComment', payload)
                    .error(function(data, status) {
                      if(status === 400) {
                        angular.forEach(data, function(value, key) {
                          alertService.add('danger', value.message);
                        });
                      } else if(status === 401) {
                        $location.path('/login');
                      } else if(status === 500) {
                        alertService.add('danger', 'Internal server error');
                      } else {
                        alertService.add('danger', data);
                      }
                    })
                    .success(function(data) {
                      if (data.success.message === "Cannot like a comment twice") {
                        alertService.add('warning', data.success.message);
                      } else {
                        alertService.add('success', data.success.message);
                      }
                      $scope.viewComment();
                    });
        };
  });

