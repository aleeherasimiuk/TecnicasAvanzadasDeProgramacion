describe "Aspects Test" do

  let(:filter){ Aspects.method(:filter) }
  let(:dummy_object) { DummyClass.new }
  let(:origins) do
    [DummyClass, DummyModule, dummy_object, A, Abc, /A./]
  end

  context "#filter_by" do
    subject do
      filter.call(origins)
    end

    it 'should return size 4 for classes and modules' do
      classes_and_modules, _, _ = subject
      expect(classes_and_modules.size).to eq 4
    end

    it 'should return 2 classes and 2 modules' do
      classes_and_modules, _, _ = subject
      expect(classes_and_modules).to eq([DummyClass, DummyModule, A, Abc])
    end

    it 'should return size 1 for objects' do
      _, objects, _ = subject
      expect(objects.size).to eq 1
    end

    it 'should return the expected object' do
      _, objects, _ = subject
      expect(objects).to eq([dummy_object])
    end

    it 'should return size 1 for regex' do
      _, _, regex = subject
      expect(regex.size).to eq 1
    end

    it 'should return the expected regex' do
      _, _, regex = subject
      expect(regex).to eq([/A./])
    end
  end
end
