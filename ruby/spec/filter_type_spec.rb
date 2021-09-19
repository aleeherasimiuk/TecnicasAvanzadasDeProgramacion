describe "Aspects Test" do

  let(:filter_by){
    Aspects.method(:filter_by)
  }

  let(:origins){
    class DummyClass
    end

    module DummyModule
    end

    class A
    end

    module Abc
    end

    object = DummyClass.new

    [DummyClass, DummyModule, object, A, Abc, /A./]
  }

  context "#filter_by" do

    it "should return 2 classes" do
      expect(filter_by.call(Class, *origins).size).to eq 2
    end

    xit "should return 1 object" do
      expect(filter_by.call(Object, *origins).size).to eq 1
    end

    xit "should return 2 modules" do
      expect(filter_by.call(Module, *origins).size).to eq 2
    end

    it "should return 1 regex" do
      expect(filter_by.call(Regexp, *origins).size).to eq 1
    end
  end

end
