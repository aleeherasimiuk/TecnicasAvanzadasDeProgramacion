describe "Transforms" do

  before(:each) do

    class Calculator

      def sum(a, b)
        @x = a + b
      end

    end

  end

  after(:each) do
    Object.send(:remove_const, :Calculator)
  end
  
  context "methods" do
    it "should respond to #after" do
      Aspects.on(Calculator) do
        transform [:sum] do
        end
      end

      expect(Calculator).to respond_to(:after)
    end
  end
  
    
  context "#after" do

    def do_transform(origin)
      Aspects.on(origin) do
        transform([:sum]) do
          after do |instance, *args|
            @x * 2
          end
        end
      end
    end

    it "should return double of sum using after on a class" do
      
      do_transform(Calculator)

      calculator = Calculator.new

      expect(calculator.sum(1, 2)).to eq(6)

    end


    it "should return double of sum using after on an instance" do
      
      calculator = Calculator.new

      do_transform(calculator)


      expect(calculator.sum(1, 2)).to eq(6)

    end

    it "should not affect another instance of the same class" do
      
      calculator = Calculator.new

      do_transform(calculator)

      calculator2 = Calculator.new

      expect(calculator.sum(1, 2)).to eq(6)
      expect(calculator2.sum(1, 2)).to eq(3)
    end

    it "should not raise error even if block is empty" do

      Aspects.on(Calculator) do
        transform([:sum]) do
          after do |instance, *args|
          end
        end
      end

      expect {
        Calculator.new.sum(1, 2)
      }.to_not raise_error
      
      #expect(Calculator.new.sum(1, 2)).to eq(3) TODO:
    end
  end


  context "#instead_of" do

    def do_transform(origin)
      Aspects.on(origin) do
        transform([:sum]) do
          instead_of do |instance, *args|
            (args[0] + args[1]) * 2
          end
        end
      end
    end


    it "should return double of sum using instead_of on a class" do
      
      do_transform(Calculator)

      calculator = Calculator.new

      expect(calculator.sum(1, 2)).to eq(6)

    end

    it "should return double of sum using instead_of on an instance" do

      calculator = Calculator.new

      do_transform(calculator)

      expect(calculator.sum(1, 2)).to eq(6)

    end

    it "should not affect another instance of the same class" do

      calculator = Calculator.new

      do_transform(calculator)

      calculator2 = Calculator.new

      expect(calculator.sum(1, 2)).to eq(6)
      expect(calculator2.sum(1, 2)).to eq(3)
    end

  end

end