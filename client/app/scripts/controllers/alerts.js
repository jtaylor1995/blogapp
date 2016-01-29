'use strict';

angular.module('clientApp')
 .controller('AlertsCtrl', function ($scope, alertService) {
     $scope.alerts = alertService.get();
 });
