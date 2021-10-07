describe "Transforms" do

  before(:each) do
    class Saludador
      def saludar(nombre1, nombre2, nombre3)
        "Hola #{nombre1}, #{nombre2}, #{nombre3}"
      end
    end
  end

  after(:each) do
    Object.send(:remove_const, :Saludador)
  end
  
  context "injecting params twice" do

    it "should respond to aspected method concatenated with __prefix and _old__ sufix and an adittional _ for each transform for two transforms" do
      
      Aspects.on(Saludador) do
        transform([:saludar]) {}
        transform([:saludar]) {}
      end
  
      expect(Saludador.instance_method(:__saludar_old__)).not_to be_nil
      expect(Saludador.instance_method(:__saludar_old___)).not_to be_nil
  
    end

    it "should respond to aspected method concatenated with __prefix and _old__ sufix and an adittional _ for each transform for three transforms" do
      
      Aspects.on(Saludador) do
        transform([:saludar]) {}
        transform([:saludar]) {}
        transform([:saludar]) {}
      end
  
      expect(Saludador.instance_method(:__saludar_old__)).not_to be_nil
      expect(Saludador.instance_method(:__saludar_old___)).not_to be_nil
      expect(Saludador.instance_method(:__saludar_old____)).not_to be_nil
  
    end

    it "should say 'Hola' to Carlos, Pepe and Pablo when injecting params by two inject" do

      Aspects.on(Saludador) do

        transform [:saludar] do
          inject(nombre1: "Carlos")
        end

        transform [:saludar] do
          inject(nombre2: "Pepe")
        end

      end
      expect(Saludador.new.saludar("Roberto", "José", "Pablo")).to eq("Hola Carlos, Pepe, Pablo")
    end

    it "should say 'Hola' to Carlos, Pepe and Pablo when injecting params by three inject" do
    
      Aspects.on(Saludador) do

        transform [:saludar] do
          inject(nombre1: "Carlos")
        end

        transform [:saludar] do
          inject(nombre2: "Pepe")
        end

        transform [:saludar] do
          inject(nombre3: "Pablo")
        end

      end
      expect(Saludador.new.saludar("Roberto", "José", "María")).to eq("Hola Carlos, Pepe, Pablo")
    end

  end

end