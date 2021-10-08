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

  context "injecting procs to class" do
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

  context "injecting procs to object" do
    it "should say 'Hola' to the argument as the third param but with Perez surname" do
      
      my_proc = proc{|receptor, mensaje, arg_anterior| arg_anterior + " Perez"}

      saludador = Saludador.new
      Aspects.on(saludador) do
        transform([:saludar]){
          inject(nombre3: my_proc)
        }
      end
      
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Pablo Perez")
    end

    it "should not affect another instance of the same class" do

      my_proc = proc{|receptor, mensaje, arg_anterior| arg_anterior + " Perez"}

      saludador = Saludador.new
      Aspects.on(saludador) do
        transform([:saludar]){
          inject(nombre3: my_proc)
        }
      end
      
      saludador2 = Saludador.new
      expect(saludador.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Pablo Perez")
      expect(saludador2.saludar("Pepe", "Roberto", "Pablo")).to eq("Hola Pepe, Roberto, Pablo")
    end

  end
end