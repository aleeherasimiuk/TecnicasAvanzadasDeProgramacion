describe "General Tests" do

  before(:each) do

    class Calculator

      attr_accessor :pi

      def initialize
        @pi = 3.14
      end

      def sum(a, b)
        a + b
      end

      def sum_hipster(p1, p2)
        p1 - (-p2)
      end

      def circle_area(radius)
        @pi * radius**2
      end

    end

    class FakeCalc

      def sum(a, b)
        a - b
      end

      def sum_hipster(p1, p2)
        p1 - (-(-p2))
      end

      def circle_area(radius)
        "no"
      end

    end

  end

  after(:each) do
    Object.send(:remove_const, :Calculator)
    Object.send(:remove_const, :FakeCalc)
  end

  it "should inject 2 as first param in #sum and then redirect to Fake Calc instance" do
    calculator = Calculator.new
    fake_calc = FakeCalc.new
    Aspects.on(calculator) do
      transform(where(with_name /sum$/)) do
        redirect_to(fake_calc)
      end

      transform(where(with_name /sum$/)) do
        inject(a: 2)
      end

    end


    expect(calculator.sum(1, 1)).to eq(1)
  end

  it "should inject 10 as first param in #sum and then redirect to Fake Calc instance but after this method must return double of the result" do
    calculator = Calculator.new
    fake_calc = FakeCalc.new
    Aspects.on(calculator) do
      transform(where(with_name /sum/)) do
        redirect_to(fake_calc)
      end

      transform(where(with_name /sum/)) do
        inject(a: 10)
      end

      transform(where(with_name /sum/)) do
        after do |instance, result, *args|
          result * 2
        end
      end

    end
    expect(calculator.sum(752, 5)).to eq(10)
  end

  it "should inject 10 as first parameter, redirect to Fake Calc instance and before must duplicate each arg" do
    calculator = Calculator.new
    fake_calc = FakeCalc.new
    Aspects.on(calculator) do
      transform(where(with_name /sum/)) do
        redirect_to(fake_calc)
      end

      transform(where(with_name /sum/)) do
        instead_of do |instance, *args|
          new_args = args.map { |arg| arg * 2 }
          new_args[0] + new_args[1]
        end
      end

      transform(where(with_name /sum/)) do
        inject(a: 10)
      end

    end

    expect(calculator.sum(752, 5)).to eq(30)
  end

  it "should override pi to calculate a weird circle area" do
    calculator = Calculator.new

    Aspects.on(calculator) do
      transform(where(has_parameters(1, /radius/))) do

        before do |instance, cont, *args|
          instance.pi = 4
          cont.call(self, *args)
        end
        inject(radius: 5)
      end
    end

    expect(calculator.circle_area(1)).to eq(100)
  end

  it "should throw all the transformation with a final instead_of" do
    calculator = Calculator.new

    Aspects.on(calculator) do
      transform(where(has_parameters(1, /radius/))) do

        before do |instance, cont, *args|
          instance.pi = 4
          cont.call(self, *args)
        end
        inject(radius: 5)
        redirect_to(FakeCalc.new)
        instead_of do |instance, *args|
          27
        end
      end
    end

    expect(calculator.circle_area(56)).to eq(27)
  end

  it "should redirect a method that suffered an inject from an Object from class A to another from class B" do

    Aspects.on C do
      transform([:saludar]) do
        redirect_to(B.new)
        inject(x: "Pepe")
      end
    end

    expect(C.new.saludar("Mundo")).to eq("Adiosín, Pepe")
  end


end