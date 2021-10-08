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

  context "should raise error when passing incorrect arguments injecting params to class" do
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
  end


  context "should raise error when passing incorrect arguments injecting params to object" do
    it "should throw ArgumentError when Carlos are passed as first param and then we pass 2 params" do
    
      saludador = Saludador.new
      Aspects.on(saludador) do
        transform([:saludar]){
          inject(nombre1: "Carlos")
        }
      end
    
      expect{saludador.saludar("Juan","Peter")}.to raise_error(ArgumentError) 
      
    end
  
    it "should throw ArgumentError when Carlos are passed as second param and then we pass 2 params" do
  
      saludador = Saludador.new
      Aspects.on(saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos")
        }
      end
    
      expect{saludador.saludar("Juan","Peter")}.to raise_error(ArgumentError) 
      
    end
  
    it "should throw ArgumentError when Carlos are passed as third param and then we pass only one param" do
      
      saludador = Saludador.new
      Aspects.on(saludador) do
        transform([:saludar]){
          inject(nombre3: "Carlos")
        }
      end
    
      expect{saludador.saludar("Juan")}.to raise_error(ArgumentError) 
      
    end
  
    it "should throw ArgumentError when Carlos are passed as second param and then we pass no params" do
  
      saludador = Saludador.new
      Aspects.on(saludador) do
        transform([:saludar]){
          inject(nombre2: "Carlos")
        }
      end
    
      expect{saludador.saludar()}.to raise_error(ArgumentError) 
      
    end
  end
end