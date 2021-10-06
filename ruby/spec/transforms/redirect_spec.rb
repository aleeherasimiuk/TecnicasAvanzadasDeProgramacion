describe "Transforms" do

  context "#redirect" do
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

    it "should raise error when tries to redirect to an object that doesnt understand the method" do

      A.singleton_class.define_method(:saludar) { |it| "Buenos dias #{it}"}

        Aspects.on C do
          transform([:saludar]) do
            redirect_to(A)
          end
        end

      expect(C.new.saludar("Mundo")).to eq("Buenos dias Mundo")

    end
  end
end