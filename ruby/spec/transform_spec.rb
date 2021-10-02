describe "Transforms" do


  context "methods" do
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

  context "should work" do
    xit "should say 'Hola' to carlos as first parameter" do

      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Carlos, Roberto, Pablo")
      
    end
  
    xit "should say 'Hola' to carlos as second param" do
    
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Carlos, Pablo")
      
    
    end
  
    xit "should say 'Hola' to carlos as third param" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre3: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Carlos")
      
    end
  
    xit "should say 'Hola' to carlos as third param but passing 2 params" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre3: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto")).to eq("Hola Pepe, Roberto, Carlos")
      
    end
  
    xit "should say 'Hola' to carlos and pepe as first and second param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos", nombre2: "Pepe")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Roberto")
      
    end
  
    xit "should say 'Hola' to carlos and pepe as second and third param" do
        Aspects.on(Saludador) do
          transform([:saludar]){
            inject(nombre2: "Carlos", nombre3: "Pepe")
          }
        end
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Juan, Carlos, Pepe")
        
    end
  
    xit "should say 'Hola' to carlos and pepe as second and third param but passing only 1 param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos", nombre3: "Pepe")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.saludar("Juan")).to eq("Hola Juan, Carlos, Pepe")
      
    end
  
    xit "should say 'Hola' to Carlos, Pepe, and Pablo without passing any param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.saludar).to eq("Hola Carlos, Pepe, Pablo")
      
    end
  
    xit "should say 'Hola' to Carlos, Pepe, and Pablo passing three params" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Pablo")
      
    end
  end

  context "should raise error" do
    xit "should throw ArgumentError when Carlos are passed as first param and then we pass 2 params" do
    
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos")
        }
      end
    
      saludador = Saludador.new
    
      expect{saludador.saludar("Juan","Peter")}.to raise_error(ArgumentError) 
      
    end
  
    it "should throw ArgumentError when Carlos are passed as second param and then we pass 2 params" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos")
        }
      end
    
      saludador = Saludador.new
    
      expect{saludador.saludar("Juan","Peter")}.to raise_error(ArgumentError) 
      
    end
  
    it "should throw ArgumentError when Carlos are passed as third param and then we pass only one param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre3: "Carlos")
        }
      end
    
      saludador = Saludador.new
    
      expect{saludador.saludar("Juan")}.to raise_error(ArgumentError) 
      
    end
  
    it "should throw ArgumentError when Carlos are passed as second param and then we pass no params" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos")
        }
      end
    
      saludador = Saludador.new
    
      expect{saludador.saludar()}.to raise_error(ArgumentError) 
      
    end
  end
  

end