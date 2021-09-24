describe "Where tests" do
  let(:dummy_instance) { ClassWithMethods.new }
  subject do
    Aspects.on(ClassWithMethods, dummy_instance) {}
  end

  it "get methods should return instance methods" do
    subject
    expect(ClassWithMethods.method(:get_methods).call).to include(:method1, :method2)
  end

  context "Name condition" do
    it "should return method1 on name condition" do
      subject
      expect(ClassWithMethods.where name(/method1/)).to eq [:method1]
    end

    it "object should return method1 on name condition" do
      subject
      dummy_instance.define_singleton_method(:saludar) { "hola" }
      expect(dummy_instance.where name(/saludar/)).to eq [:saludar]
    end
  
    it "should verify multiple name conditions" do
      subject
      expect(ClassWithMethods.where name(/^m.*/), name(/thod[1-9]$/)).to include(:method2, :method1)
    end
  end

  context "Parameters condition" do
    it "should verify a certain amount of parameters" do
      subject
      expect(ClassWithMethods.where has_parameters(3)).to eq [:method1]
    end
  end

  context "Negate condition" do
    it "should take the opposite of a certain amount of parameters" do
      subject
      expect(ClassWithMethods.where neg(has_parameters(3))).to eq [:method2]
    end

    it "should take the opposite of a name condition" do
      subject
      expect(ClassWithMethods.where neg(name(/method1/))).to eq [:method2]
    end

  end

end

