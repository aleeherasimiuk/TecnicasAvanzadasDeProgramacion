describe "Aspects Test" do
  
  context "instances" do

    it "should not respond to any method defined in module" do
      
      class EmptyClass
      end

      expect(EmptyClass.singleton_class.included_modules.include? LogicModule).to be false
      
      LogicModule.define_method :test_method do
        "test"
      end

      Aspects.on(EmptyClass) do
      end

      expect(EmptyClass.singleton_class.included_modules.include? LogicModule).to be true

      dummyInstance = EmptyClass.new
      expect(dummyInstance.respond_to?(:test_method)).to be false
    end

  end

end