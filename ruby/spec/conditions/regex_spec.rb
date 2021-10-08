describe "Aspects Test" do

  let(:modules_by_regex) do
    Aspects.method(:modules_by_regex)
  end

  context "#modules_by_regex" do
    let(:foo_regex) { [/^Foo.*/] }
    let(:bar_regex) { [/.*Bar/] }
    let(:foo_bar_regex) { [/^(Bar|Fooma)$/] }
    let(:ker_regex) { [/^Ker/] }

    it "should return Foo" do
      expect(modules_by_regex.call(foo_regex)).to eq([Fooma])   #Lo cambie a Fooma para hacer el test del enunciado (todas las clases que empiezan con Foo)
    end

    it "should return Bar" do
      expect(modules_by_regex.call(bar_regex)).to eq([Bar])
    end

    it "should return Bar and Foo" do
      expect(modules_by_regex.call(foo_bar_regex)).to include(Fooma, Bar)
    end

    it "should not return Objects like Kernel or BasicObject" do
      expect(modules_by_regex.call(ker_regex)).to eq([Kermit])
    end
  end
end
