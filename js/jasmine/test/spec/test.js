/*global describe, it */
'use strict';

(function () {
  describe ('Jasmine', function () {
    describe ('implements spies', function () {
      var greeter;
      
      beforeEach(function() {
        greeter = {
          greet: function(subject) {
            return "Hello, " + subject + "!";
          }
        };
      });
      
      it ('tracks spied method calls', function () {
        spyOn(greeter, 'greet');
        greeter.greet('world');
        expect(greeter.greet).toHaveBeenCalledWith('world');
      });
      
      it ('can create pure mock objects with multiple spies', function() {
        var mock = jasmine.createSpyObj('Greeter', ['greet']);
        mock.greet('world');
        expect(mock.greet).toHaveBeenCalledWith('world');
      });
    });
  });
})();
