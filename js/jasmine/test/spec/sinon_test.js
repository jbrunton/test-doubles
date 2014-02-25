/*global describe, it */
'use strict';

(function () {
  describe ('Sinon', function () {
    describe ('implements spies', function () {
      it ('tracks spied method calls', function () {
        var greet = sinon.spy();
        greet('world');
        expect(greet.calledWith('world')).toBeTruthy();
      });
      
      it ('implements stubs', function() {
        var greet = sinon.stub().returns('Good day, world!');
        expect(greet('world')).toBe('Good day, world!');
      });
      
      it ('creates mocks to verify interactions', function() {
        var greeter = {
          greet: function(subject) {
            return "Hello, " + subject + "!";
          }
        };
        
        var mock = sinon.mock(greeter);
        
        mock.expects('greet').returns('Good day, world!');
        
        // note we call 'greet' on the original object, not the mock
        greeter.greet('world');
        
        mock.verify();
      });
    });
  });
})();
