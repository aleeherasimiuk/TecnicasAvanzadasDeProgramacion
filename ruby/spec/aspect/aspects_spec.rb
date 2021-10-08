describe "Aspects Test" do

  context "#on" do
    let(:test_object) { Object.new }

    it "should exist" do
      expect(Aspects.respond_to?(:on)).to be true
    end

    context 'Aspects params validations' do

      it "should pass when valid object and block are given" do
        expect { Aspects.on(test_object) {} }
      end

      it "should not be able to allow no parameters" do
        expect { Aspects.on {} }.to raise_error(EmptyOriginError)
      end

      it "should not be able to allow no block" do
        expect { Aspects.on Object }.to raise_error(NotBlockGivenError)
      end

      it "should not be able to allow no parameters nor block" do
        expect { Aspects.on }.to raise_error(EmptyOriginError)
      end

      it "should not let use regex that not match with anything" do
        expect { Aspects.on(/PatitoColorDeCafe/) {} }.to raise_error(EmptyOriginError)
      end

      it "should not raise error when one regex dont match, but another does" do
        expect { Aspects.on(/PatitoColorDeCafe/, /^Ar.*/) {} }.not_to raise_error  # se espera que no lance error
      end
    end

    context 'check origin of LogicModule' do
      let(:dummyObject) { DummyClass.new }

      context 'when some classes and modules use the Aspects framework' do
        subject do
          Aspects.on(DummyClass, DummyModule, dummyObject) {}
        end

        it 'should not include the affected objects before any positive test' do
          expect(class_includes_logic_module?(DummyModule)).to be false
          expect(class_includes_logic_module?(DummyClass)).to be false
          expect(class_includes_logic_module?(dummyObject)).to be false
        end

        it 'should include the module on the DummyClass' do
          subject
          expect(class_includes_logic_module?(DummyClass)).to be true
        end

        it 'should include the module on the DummyModule' do
          subject
          expect(class_includes_logic_module?(DummyModule)).to be true
        end

        it "should include LogicModule on each origin" do
          subject
          expect(class_includes_logic_module?(dummyObject)).to be true
        end

        it 'should not include the LogicModule on another object of the same class as the object in the Aspects' do
          aspected = DummyClass.new
          not_aspected = DummyClass.new

          Aspects.on(aspected) {}

          expect(class_includes_logic_module?(aspected)).to be true
          expect(class_includes_logic_module?(not_aspected)).to be false
        end
      end
    end
  end
end
