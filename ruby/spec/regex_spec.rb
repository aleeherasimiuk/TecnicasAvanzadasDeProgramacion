describe "Aspects Test" do

  let(:modules_by_regex) do
    Aspects.method(:modules_by_regex)
  end

  context "#modules_by_regex" do
    let(:foo_regex) { [/^Foo.*/] }
    let(:bar_regex) { [/.*Bar/] }
    let(:foo_bar_regex) { [/^(Bar|Fooma)$/] }

    context 'SystemGetter' do
      it 'expects that the Bar module is included when querying the modules' do
        expect(SystemGetter.get_all(Module).include? Bar).to be true
      end

      it 'expects that the Fooma class is included when querying the classes' do
        expect(SystemGetter.get_all(Class).include? Fooma).to be true
      end
    end

    it "should return Foo" do
      expect(modules_by_regex.call(foo_regex)).to eq([Fooma])   #Lo cambie a Fooma para hacer el test del enunciado (todas las clases que empiezan con Foo)
    end

    it "should return Bar" do
      expect(modules_by_regex.call(bar_regex)).to eq([Bar])
    end

    it "should return Bar and Foo" do
      expect(modules_by_regex.call(foo_bar_regex)).to eq([Bar, Fooma])
    end
  end
end
