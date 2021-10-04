describe "Transforms" do

  before(:each) do
    class Saludador

      def saludar(nombre1, nombre2, nombre3)
        "Hola #{nombre1}, #{nombre2}, #{nombre3}"
      end

      def despedir(nombre1 = "Carlos", nombre2 = "Pedro", nombre3)
        "Adios #{nombre1}, #{nombre2}, #{nombre3}"
      end

      def hola_y_chau(nombre1: "Carlos", nombre2: "Pedro")
        "Hola #{nombre1}, Adiós #{nombre2}"
      end

      def hola_a_todos(*nombres)
        "Hola #{nombres.join(', ')}"
      end
    
    end
  end

  after(:each) do
    Object.send(:remove_const, :Saludador)
  end


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
    it "should say 'Hola' to carlos as first parameter" do

      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Carlos, Roberto, Pablo")
      
    end
  
    it "should say 'Hola' to carlos as second param" do
    
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Carlos, Pablo")
      
    
    end
  
    it "should say 'Hola' to carlos as third param" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre3: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Carlos")
      
    end
  
    it "should say 'Hola' to carlos as third param but passing 2 params" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre3: "Carlos")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Pepe", "Roberto")).to eq("Hola Pepe, Roberto, Carlos")
      
    end
  
    it "should say 'Hola' to carlos and pepe as first and second param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos", nombre2: "Pepe")
        }
      end
  
      saludador = Saludador.new
  
      expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Roberto")
      
    end
  
    it "should say 'Hola' to carlos and pepe as second and third param" do
        Aspects.on(Saludador) do
          transform([:saludar]){
            inject(nombre2: "Carlos", nombre3: "Pepe")
          }
        end
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Juan, Carlos, Pepe")
        
    end
  
    it "should say 'Hola' to carlos and pepe as second and third param but passing only 1 param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos", nombre3: "Pepe")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.saludar("Juan")).to eq("Hola Juan, Carlos, Pepe")
      
    end
  
    it "should say 'Hola' to Carlos, Pepe, and Pablo without passing any param" do
      
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.saludar).to eq("Hola Carlos, Pepe, Pablo")
      
    end
  
    it "should say 'Hola' to Carlos, Pepe, and Pablo passing three params" do
  
      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Pablo")
      
    end


    it "should say 'Adios' to Roberto, Pedro and Peter" do
        
        Aspects.on(Saludador) do
          transform([:despedir]){
            inject(nombre1: "Roberto")
          }
        end
      
        saludador = Saludador.new
      
        expect(saludador.despedir(nombre1 = "Raul", nombre2 = "María", nombre3 = "Peter")).to eq("Adios Roberto, María, Peter")
    end

    it "should say 'Adios' to Roberto, Pedro and Peter passing only one param" do
        Aspects.on(Saludador) do
          transform([:despedir]){
            inject(nombre3: "Roberto")
          }
        end
      
        saludador = Saludador.new
      
        expect(saludador.despedir("Raul", "Pablo")).to eq("Adios Raul, Pablo, Roberto")
    end

    it "should say 'Adios' to Roberto, Raul and María when injectin 2 optional parameters" do
      Aspects.on(Saludador) do
        transform([:despedir]){
          inject(nombre1: "Roberto", nombre2: "Raul")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.despedir("Raul", "Pablo", "María")).to eq("Adios Roberto, Raul, María")
    end

    xit "should say 'Hola' and 'Adios' to Roberto and María when injecting named parameters" do
      
      Aspects.on(Saludador) do
        transform([:hola_y_chau]){
          inject(nombre1: "Roberto")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.hola_y_chau(nombre1: "Raul", nombre2: "María")).to eq("Hola Roberto, Adiós María")
    end

    it "should say 'Hola' to Roberto only" do
      
      Aspects.on(Saludador) do
        transform([:hola_a_todos]){
          inject(nombres: ["Roberto"])
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.hola_a_todos("Raul", "Maria", "Pedro")).to eq("Hola Roberto")
    end


  end


  context "should pass the proc result" do
    

    it "should say 'Hola' to the argument as the third param but with Perez surname" do
      
      my_proc = proc{|receptor, mensaje, arg_anterior| arg_anterior + " Perez"}

      Aspects.on(Saludador) do
        transform([:saludar]){
          inject(nombre3: my_proc)
        }
      end
    
      saludador = Saludador.new
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Pablo Perez")
    end

  end

  context "should raise error" do
    it "should throw ArgumentError when Carlos are passed as first param and then we pass 2 params" do
    
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


    xit "should not say 'Adios' when not passing optional params" do
        
      Aspects.on(Saludador) do
        transform([:despedir]){
          inject(nombre1: "Roberto")
        }
      end
    
      saludador = Saludador.new
    
      expect(saludador.despedir(nombre3 = "Peter")).to raise_error(ArgumentError)
      
    end
  end

  context "redirect" do
    it "should redirect the message for an Object from class A to another from class B" do

      Aspects.on C do
        transform([:saludar]) do
          redirect_to(B.new)
        end
      end

      expect(C.new.saludar("Mundo")).to eq("Adiosín, Mundo")

    end

    it "should redirect the message for an Object from class A to another from class B with multiple parameters" do

      Aspects.on C do
        transform([:operar]) do
          redirect_to(B.new)
        end
      end

      expect(C.new.operar(5,5,5)).to eq(125)

    end

    it "should raise error when tries to redirect to an object that doesnt understand the method" do

      block = proc do
        Aspects.on C do
          transform([:saludar]) do
            redirect_to(A.new)
          end
        end
      end

      expect{ block.call }.to raise_error(NoMethodError)

    end


  end
  

end