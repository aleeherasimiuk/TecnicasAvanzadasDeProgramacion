describe "Aspects Test" do

  describe "#methods" do
    
    it "Aspect should respond to :on" do
      expect(Aspects.respond_to?(:on)).to be true
    end

    it "El aspecto no debe permitir no tener parámetros" do
     expect do
       Aspects.on{}
     end.to raise_error(ArgumentError)
    end
  end

end


