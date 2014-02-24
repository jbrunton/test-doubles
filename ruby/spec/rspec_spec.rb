require 'spec_helper'
require './greeter'

describe "RSpec" do
  context "the basics" do
    let(:foo) { Object.new }
  
    it "can stub methods" do
      allow(foo).to receive(:bar).and_return(:baz)
      expect(foo.bar).to eq(:baz)
    end
  
    it "can mock methods" do
      expect(foo).to receive(:bar).with(:baz)
      foo.bar(:baz)
    end
  
    it "can fake methods" do
      allow(foo).to receive(:bar) do |arg|
        arg.to_s
      end
      expect(foo.bar(:baz)).to eq('baz')
    end
  end
  
  context "test doubles" do
    describe "double" do
      let(:greeter) { double(Greeter) }
      
      it "constructs an empty, pure mock" do
        expect{
          greeter.greet('world')
        }.to raise_error(
          RSpec::Mocks::MockExpectationError,
          'Double Greeter received unexpected message :greet with ("world")'
        )
      end
      
      it "allows methods to be stubbed, faked and mocked" do
        allow(greeter).to receive(:wave)
        expect(greeter).to receive(:gesture)
        greeter.gesture
      end
    end
    
    describe "instance_double" do
      it "constructs a pure mock" do
        greeter = instance_double(Greeter, :greet => 'Hello, world!')
        expect(greeter.greet('world')).to eq('Hello, world!')
      end
      
      it "only allows methods defined by the class to be stubbed or mocked" do
        greeter = instance_double(Greeter)
        expect{
          allow(greeter).to receive(:wave)
        }.to raise_error(RSpec::Mocks::MockExpectationError, /Greeter does not implement:\s*wave/)
      end
    end
    
    describe "partial mocks" do
      let(:greeter) { Greeter.new }

      it "can stub methods on partial mocks" do
        allow(greeter).to receive(:greet) { 'Hello, world!' }
        expect(greeter.greet).to eq('Hello, world!')
      end
      
      it "can mock methods on partial mocks" do
        expect(greeter).to receive(:greet)
        greeter.greet
      end
    end
    
    describe "spies" do
      it "allows expectations to be set on pure mocks" do
        greeter = instance_double(Greeter, :greet => 'Hello, world!')
        greeter.greet('world')
        expect(greeter).to have_received(:greet).with('world')
      end

      it "allows expectations to be set on partial mocks" do
        greeter = Greeter.new
        allow(greeter).to receive(:greet)
        greeter.greet('world')
        expect(greeter).to have_received(:greet).with('world')
      end
    end
  end
end
