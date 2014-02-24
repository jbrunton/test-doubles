require 'minitest/autorun'
require 'minitest/unit'
require 'mocha/mini_test'

require './greeter'

describe Mocha do
  describe "it mocks things" do
    before do
      @greeter = Minitest::Mock.new
    end
    
    it "allows expectations to be set" do
      @greeter.expect :greet, 'Hello, world!', ['world']
      @greeter.greet('world')
      @greeter.verify
    end
    
    it "doesn't provide a stubbing or faking mechanism, but it's ruby, so.." do
      def @greeter.greet(subject)
        "Good day, world!"
      end
      @greeter.greet('world').must_equal 'Good day, world!'
    end
  end
end
