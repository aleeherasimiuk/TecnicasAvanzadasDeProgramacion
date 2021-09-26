describe 'Aspects Test' do
  context 'instances' do
    let(:dummyInstance) { EmptyClass.new }

    subject do
      Aspects.on(EmptyClass) {}
    end

    before do
      LogicModule.define_method :test_method do
        'test'
      end
    end

    it 'should not respond to any method defined in module' do
      expect(EmptyClass.singleton_class.included_modules.include? LogicModule).to be false

      subject

      expect(EmptyClass.singleton_class.included_modules.include? LogicModule).to be true
      expect(dummyInstance.respond_to?(:test_method)).to be false
    end

    it 'should respond to the test method for the singleton class of EmptyClass' do
      subject
      expect(EmptyClass.respond_to?(:test_method)).to be true
    end

    it 'should return the expected result for EmptyClass#test_method' do
      subject
      expect(EmptyClass.test_method).to eq('test')
    end
  end
end
