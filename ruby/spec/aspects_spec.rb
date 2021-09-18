describe "Aspects Test" do

  context "#on" do

    class DummyClass
    end

    module DummyModule
    end

    let(:dummyObject) { DummyClass.new }
    
    it "should exist" do
      expect(Aspects.respond_to?(:on)).to be true
    end

    it "should not be able to allow no parameters" do
     expect do
       Aspects.on{}
     end.to raise_error(ArgumentError)
    end

    xit "should not be able to allow no block" do
      expect do
        Aspects.on DummyClass
      end.to raise_error(ArgumentError)
    end

    xit "should include Aspect on the origin" do
      
      Aspects.on DummyClass {}
      expect(DummyClass.included_modules.include? Aspect).to be true
    end

    xit "should include Aspect on each origin" do
      
      Aspects.on DummyClass DummyModule dummyObject {}

      expect(DummyClass.included_modules.include? Aspect).to be true
      expect(DummyModule.included_modules.include? Aspect).to be true
      expect(dummyObject.singleton_class.included_modules.include? Aspect).to be true
    end
  end
end



