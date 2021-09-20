describe "Aspects Test" do

  let(:modules_by_regex){
    Aspects.method(:modules_by_regex)
  }

  class Fooma
  end

  module Bar
  end

  context "#modules_by_regex" do

    it "test classes and modules are on the system" do
      expect(SystemGetter.get_all(Class).include? Fooma).to be true
      expect(SystemGetter.get_all(Module).include? Bar).to be true
    end

    it "should return Foo" do
      expect(modules_by_regex.call([/^Foo.*/])).to eq([Fooma])   #Lo cambie a Fooma para hacer el test del enunciado (todas las clases que empiezan con Foo)
    end

    it "should return Bar" do
      expect(modules_by_regex.call([/.*Bar/])).to eq([Bar])
    end

    it "should return Bar and Foo" do
      expect(modules_by_regex.call([/^(Bar|Fooma)$/])).to eq([Bar, Fooma])
    end

  end


end
