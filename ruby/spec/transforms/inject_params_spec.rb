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

  context "injecting params on class" do
    context "#saludar" do

      def transform(**hash)
        Aspects.on(Saludador) do
          transform([:saludar]) do
            inject(**hash)
          end
        end
      end

      it "should say 'Hola' to carlos as first parameter" do

        transform(nombre1: "Carlos")
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Carlos, Roberto, Pablo")
        
      end
    
      it "should say 'Hola' to carlos as second param" do
      
        transform(nombre2: "Carlos")
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Carlos, Pablo")
        
      
      end
    
      it "should say 'Hola' to carlos as third param" do
    
        transform(nombre3: "Carlos")
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Carlos")
        
      end
    
      it "should say 'Hola' to carlos as third param but passing 2 params" do
    
        transform(nombre3: "Carlos")
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Pepe", "Roberto")).to eq("Hola Pepe, Roberto, Carlos")
        
      end
    
      it "should say 'Hola' to carlos and pepe as first and second param" do
        
        transform(nombre1: "Carlos", nombre2: "Pepe")
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Roberto")
        
      end
    
      it "should say 'Hola' to carlos and pepe as second and third param" do
          
        transform(nombre2: "Carlos", nombre3: "Pepe")
    
        saludador = Saludador.new
    
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Juan, Carlos, Pepe")
          
      end
    
      it "should say 'Hola' to carlos and pepe as second and third param but passing only 1 param" do
        
        transform(nombre2: "Carlos", nombre3: "Pepe")
      
        saludador = Saludador.new
      
        expect(saludador.saludar("Juan")).to eq("Hola Juan, Carlos, Pepe")
        
      end
    
      it "should say 'Hola' to Carlos, Pepe, and Pablo without passing any param" do
        
        transform(nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
      
        saludador = Saludador.new
      
        expect(saludador.saludar).to eq("Hola Carlos, Pepe, Pablo")
        
      end
    
      it "should say 'Hola' to Carlos, Pepe, and Pablo passing three params" do
    
        transform(nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
      
        saludador = Saludador.new
      
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Pablo")
        
      end
    end


    context "#despedir" do

      def transform(**hash)
        Aspects.on(Saludador) do
          transform([:despedir]) do
            inject(**hash)
          end
        end
      end

      it "should say 'Adios' to Roberto, Pedro and Peter" do
        
        transform(nombre1: "Roberto")
      
        saludador = Saludador.new
      
        expect(saludador.despedir(nombre1 = "Raul", nombre2 = "María", nombre3 = "Peter")).to eq("Adios Roberto, María, Peter")
      end

      it "should say 'Adios' to Roberto, Pedro and Peter passing only one param" do
          
          transform(nombre3: "Roberto")
        
          saludador = Saludador.new
        
          expect(saludador.despedir("Raul", "Pablo")).to eq("Adios Raul, Pablo, Roberto")
      end

      it "should say 'Adios' to Roberto, Raul and María when injectin 2 optional parameters" do
        
        transform(nombre1: "Roberto", nombre2: "Raul")
      
        saludador = Saludador.new
      
        expect(saludador.despedir("Raul", "Pablo", "María")).to eq("Adios Roberto, Raul, María")
      end
    end

    it "should not support named parameters with inject" do
      
      Aspects.on(Saludador) do
        transform([:hola_y_chau]){
          inject(nombre1: "Roberto")
        }
      end
    
      saludador = Saludador.new
    
      expect{saludador.hola_y_chau(nombre1: "Raul", nombre2: "María")}.to raise_error(NamedParametersNotSupported)
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


  context "injecting params on object" do

    context "#saludar" do
      def transform(origin, **hash)
        Aspects.on(origin) do
          transform([:saludar]) do
            inject(**hash)
          end
        end
      end
  
      it "should not affect another instance of the same class" do
        
        saludador = Saludador.new
        saludador2 = Saludador.new
  
        transform(saludador, nombre1: "Carlos")
      
        saludador3 = Saludador.new
        
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Peter, Roberto")
        expect(saludador2.saludar("Juan","Peter","Roberto")).to eq("Hola Juan, Peter, Roberto")
        expect(saludador3.saludar("Juan","Peter","Roberto")).to eq("Hola Juan, Peter, Roberto")
        
      end
      
      it "should say 'Hola' to carlos as first parameter" do
  
        saludador = Saludador.new
  
        transform(saludador, nombre1: "Carlos")
    
        expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Carlos, Roberto, Pablo")
        
      end
    
      it "should say 'Hola' to carlos as second param" do
      
        saludador = Saludador.new
  
        transform(saludador, nombre2: "Carlos")
    
    
        expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Carlos, Pablo")
        
      
      end
    
      it "should say 'Hola' to carlos as third param" do
    
        saludador = Saludador.new
  
        transform(saludador, nombre3: "Carlos")
    
        expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Carlos")
        
      end
    
      it "should say 'Hola' to carlos as third param but passing 2 params" do
    
        saludador = Saludador.new
  
        transform(saludador, nombre3: "Carlos")
    
        expect(saludador.saludar("Pepe", "Roberto")).to eq("Hola Pepe, Roberto, Carlos")
        
      end
    
      it "should say 'Hola' to carlos and pepe as first and second param" do
        
        saludador = Saludador.new
  
        transform(saludador, nombre1: "Carlos", nombre2: "Pepe")
  
    
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Roberto")
        
      end
    
      it "should say 'Hola' to carlos and pepe as second and third param" do
          saludador = Saludador.new
  
          transform(saludador, nombre2: "Carlos", nombre3: "Pepe")
      
          expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Juan, Carlos, Pepe")
          
      end
    
      it "should say 'Hola' to carlos and pepe as second and third param but passing only 1 param" do
        
        saludador = Saludador.new
  
        transform(saludador, nombre2: "Carlos", nombre3: "Pepe")
      
        expect(saludador.saludar("Juan")).to eq("Hola Juan, Carlos, Pepe")
        
      end
    
      it "should say 'Hola' to Carlos, Pepe, and Pablo without passing any param" do
        
        saludador = Saludador.new
  
        transform(saludador, nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
      
        expect(saludador.saludar).to eq("Hola Carlos, Pepe, Pablo")
        
      end
    
      it "should say 'Hola' to Carlos, Pepe, and Pablo passing three params" do
    
        saludador = Saludador.new
  
        transform(saludador, nombre1: "Carlos", nombre2: "Pepe", nombre3: "Pablo")
      
        expect(saludador.saludar("Juan","Peter","Roberto")).to eq("Hola Carlos, Pepe, Pablo")
        
      end
    end

    context "#despedir" do

      def transform(origin, **hash)
        Aspects.on(origin) do
          transform([:despedir]) do
            inject(**hash)
          end
        end
      end

      it "should say 'Adios' to Roberto, Pedro and Peter" do
        
        saludador = Saludador.new

        transform(saludador, nombre1: "Roberto")
      
        expect(saludador.despedir(nombre1 = "Raul", nombre2 = "María", nombre3 = "Peter")).to eq("Adios Roberto, María, Peter")
      end

      it "should say 'Adios' to Roberto, Pedro and Peter passing only one param" do
          saludador = Saludador.new

          transform(saludador, nombre3: "Roberto")
        
          expect(saludador.despedir("Raul", "Pablo")).to eq("Adios Raul, Pablo, Roberto")
      end

      it "should say 'Adios' to Roberto, Raul and María when injecting 2 optional parameters" do
        
        saludador = Saludador.new

        transform(saludador, nombre1: "Roberto", nombre2: "Raul")
      
        expect(saludador.despedir("Raul", "Pablo", "María")).to eq("Adios Roberto, Raul, María")
      end
    end

    it "should say 'Hola' to Roberto only" do
      
      saludador = Saludador.new

      Aspects.on(saludador) do
        transform([:hola_a_todos]){
          inject(nombres: ["Roberto"])
        }
      end
    
      expect(saludador.hola_a_todos("Raul", "Maria", "Pedro")).to eq("Hola Roberto")
    end

    
  end
end