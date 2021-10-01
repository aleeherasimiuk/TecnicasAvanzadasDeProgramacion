describe "Transforms" do

  
  it "LogicModule instances should respond to transform" do

    Aspects.on(ClassWithMethods) {}

    expect(ClassWithMethods).to respond_to(:transform)
    
  end

  it "should respond to aspected method concatenated with __prefix and _old__ sufix" do
    
    Aspects.on(ClassWithMethods) do
      transform([:method1]) {}
    end

    expect(ClassWithMethods.instance_method(:__method1_old__)).not_to be_nil

  end


  it "should do the same thing old method and actual method on a class" do
  
    Aspects.on(Y) do
      transform([:saludar]) {}
    end

    expect(Y.new.saludar).to eq("Hola")
    expect(Y.new.method(:__saludar_old__).call).to eq("Hola")
    expect(Y.new.methods).not_to include(:__saludar_old__)

  end


  it "should do the same thing old method and actual method on a method" do
    

    my_object = Y.new

    Aspects.on(my_object) do
      transform([:saludar]) {}
    end

    expect(my_object.saludar).to eq("Hola")
    expect(my_object.method(:__saludar_old__).call).to eq("Hola")
    expect(my_object.methods).not_to include(:__saludar_old__)

  end



end