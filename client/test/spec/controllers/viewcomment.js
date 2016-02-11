'use strict';

describe('Controller: ViewcommentCtrl', function () {

  // load the controller's module
  beforeEach(module('clientApp'));

  var ViewcommentCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ViewcommentCtrl = $controller('ViewcommentCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ViewcommentCtrl.awesomeThings.length).toBe(3);
  });
});
