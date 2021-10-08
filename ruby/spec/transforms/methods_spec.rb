describe "Transforms" do
  context "methods" do
    it "LogicModule instances should respond to transform" do

      Aspects.on(ClassWithMethods) {}
  
      expect(ClassWithMethods).to respond_to(:transform)
      
    end
  
    it "should respond to aspected method concatenated with __prefix and _old__ sufix" do
      
      Aspects.on(ClassWithMethods) do
        transform([:method1]) { after {} }
      end
  
      expect(ClassWithMethods.instance_method(:__method1_old__)).not_to be_nil
  
    end
  end
end