describe "Aspects Test" do

  let(:modules_by_regex){
    Aspects.method(:modules_by_regex)
  }

  class Foo
  end

  module Bar
  end

  context "#modules_by_regex" do

    xit "should return Foo" do
      expect(modules_by_regex.call(/^Foo$/)).to eq([Foo])
    end

    xit "should return Bar" do
      expect(modules_by_regex.call(/^Bar$/)).to eq([Bar])
    end

    xit "should return Bar and Foo" do
      expect(modules_by_regex.call(/^(Bar|Foo)$/)).to eq([Bar, Foo])
    end

  end


end
