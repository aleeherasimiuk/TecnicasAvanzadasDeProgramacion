describe "Aspects Test" do


  let(:dummyObject) { DummyClass.new }

  context "#on" do

    it "should exist" do
      expect(Aspects.respond_to?(:on)).to be true
    end

    it "should pass when valid object and block are given" do
      Aspects.on dummyObject do #nose pq anda pasando el bloque como do end, y no con llaves
        #algo
      end
    end

    it "should not be able to allow no parameters" do
     expect do
       Aspects.on do 
      end
     end.to raise_error(ArgumentError)
    end

    it "should not be able to allow no block" do
      expect do
        Aspects.on DummyClass
      end.to raise_error(ArgumentError)
    end

    it "should include LogicModule on the origin" do
      
      Aspects.on dummyObject do
        #algo
      end
      expect(dummyObject.singleton_class.included_modules.include? LogicModule).to be true
    end


    it "should include LogicModule on each origin" do

      class DummyClass2
      end

      module DummyModule
      end
      
      Aspects.on DummyClass2, DummyModule, dummyObject do
        #algo
      end

      expect(DummyClass2.singleton_class.included_modules.include? LogicModule).to be true
      expect(DummyModule.singleton_class.included_modules.include? LogicModule).to be true
      expect(dummyObject.singleton_class.included_modules.include? LogicModule).to be true
    end

    it "should not include LogicModule" do

      class DummyClass3
      end

      module DummyModule2
      end

      Aspects.on DummyClass3, dummyObject do
        #algo
      end

      expect(DummyModule2.singleton_class.included_modules.include? LogicModule).to be false
    end

    it "should not include LogicModule34" do

      expect(dummyObject.singleton_class.included_modules.include? LogicModule).to be false
    end

  end
end



