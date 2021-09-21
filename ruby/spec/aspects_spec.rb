describe "Aspects Test" do

  context "#on" do
    let(:test_object) { Object.new }

    subject { Aspects.on(test_object) {} }

    it "should exist" do
      expect(Aspects.respond_to?(:on)).to be true
    end

    it "should pass when valid object and block are given" do
      subject
    end

    it "should not be able to allow no parameters" do
     expect do
       Aspects.on {}
     end.to raise_error(ArgumentError)
    end

    it "should not be able to allow no block" do
      expect { Aspects.on Object }.to raise_error(ArgumentError)
    end

    it "should not be able to allow no parameters nor block" do
      expect { Aspects.on }.to raise_error(ArgumentError)
    end

    context 'check origin of LogicModule' do
      let(:dummyObject) { DummyClass.new }

      context 'when some classes and modules use the Aspects framework' do
        subject do
          Aspects.on(DummyClass, DummyModule, dummyObject) {}
        end

        it 'should not include the affected objects before any positive test' do
          expect(klass_includes_logic_module?(DummyModule)).to be false
          expect(klass_includes_logic_module?(DummyClass)).to be false
          expect(klass_includes_logic_module?(dummyObject)).to be false
        end

        it 'should include the module on the DummyClass' do
          subject
          expect(klass_includes_logic_module?(DummyClass)).to be true
        end

        it 'should include the module on the DummyModule' do
          subject
          expect(klass_includes_logic_module?(DummyModule)).to be true
        end

        it "should include LogicModule on each origin" do
          subject
          expect(klass_includes_logic_module?(dummyObject)).to be true
        end
      end
    end
  end
end



