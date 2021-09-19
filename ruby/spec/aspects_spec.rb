describe "Aspects Test" do

  context "#on" do

    it "should exist" do
      expect(Aspects.respond_to?(:on)).to be true
    end

    it "should pass when valid object and block are given" do
      Aspects.on(Object.new){}
    end

    it "should not be able to allow no parameters" do
     expect do
       Aspects.on {}
     end.to raise_error(ArgumentError)
    end

    it "should not be able to allow no block" do
      expect do
        Aspects.on Object
      end.to raise_error(ArgumentError)
    end

    it "should include LogicModule on the origin object" do
      a = Object.new
      Aspects.on(a){}
      expect(a.singleton_class.included_modules.include? LogicModule).to be true
    end


    it "should include LogicModule on each origin" do

      class DummyClass
      end

      module DummyModule
      end

      dummyObject = DummyClass.new
      
      Aspects.on(DummyClass, DummyModule, dummyObject) {}

      expect(DummyClass.included_modules.include? LogicModule).to be true
      expect(DummyModule.included_modules.include? LogicModule).to be true
      expect(dummyObject.singleton_class.included_modules.include? LogicModule).to be true
    end
  end
end



