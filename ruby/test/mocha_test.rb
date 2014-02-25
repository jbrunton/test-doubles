require 'minitest/autorun'
require 'minitest/unit'
require 'mocha/mini_test'

require './greeter'

describe Mocha do
  describe "it partially mocks and stubs things" do
    before do
      @greeter = Greeter.new
    end
    
    it "allows partial mocking" do
      @greeter.expects(:greet).with('world')
      @greeter.greet('world')
    end
    
    it "allows partial stubbing" do
      @greeter.stubs(:greet).with('world').returns('Good day, world!')
      @greeter.greet('world').must_equal('Good day, world!')
    end
  end
  
  describe "it provides pure mocks and stubs" do
    it "creates strict mocks by default" do
      @greeter = mock('Greeter')
      proc{
        @greeter.greet('world')
      }.must_raise(MiniTest::Assertion)
    end
    
    it "creates pure stubs" do
      @greeter = stub(:greet => 'Good day, world!')
      @greeter.greet('world').must_equal('Good day, world!')
    end
  end
end
