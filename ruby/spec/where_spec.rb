describe "Where tests" do
  let(:dummy_instance) { ClassWithMethods.new }
  subject do
    Aspects.on(ClassWithMethods) {}
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
  
    it "should verify multiple name conditions" do
      subject
      expect(ClassWithMethods.where name(/^m.*/), name(/thod[1-9]$/)).to include(:method2, :method1)
    end
  end

end
