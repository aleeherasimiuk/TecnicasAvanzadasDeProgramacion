describe "Aspects Test" do

  let(:filter){
    Aspects.method(:filter)
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

    it "should return 2 classes and 2 modules" do

      classes_and_modules, _, _ = filter.call(origins)

      expect(classes_and_modules.size).to eq 4
    end

    it "should return 1 object" do
      _, objects, _ = filter.call(origins)
      expect(objects.size).to eq 1
    end

    it "should return 1 regex" do

      _, _, regex = filter.call(origins)

      expect(regex.size).to eq 1
    end
  end

end
