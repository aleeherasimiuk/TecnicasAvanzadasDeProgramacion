describe "Transforms" do

  before(:each) do

    class Calculator
      attr_accessor :x
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
          after do |instance, _, *args|
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
          after {|instance, result, *args| result}
        end
      end

      expect {
        Calculator.new.sum(1, 2)
      }.to_not raise_error

      expect(Calculator.new.sum(1, 2)).to eq(3)
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


  context "#before" do
    def do_transform(origin)
      Aspects.on(origin) do
        transform([:sum]) do
          before do |instance, cont, *args|
            instance.define_singleton_method :sum_plus_ten, proc {args.sum 10}
            new_args = args.map{ |arg| arg * 3 }
            cont.call(self, *new_args)
          end
        end
      end
    end

    it "should create a method = sum of original args plus ten" do
      do_transform(Calculator)

      calculator = Calculator.new

      calculator.sum(1, 2)

      expect(calculator.sum_plus_ten).to eq(13)
    end

    it "should triplicate args before pass to the actual method on Class" do
      do_transform(Calculator)

      calculator = Calculator.new

      expect(calculator.sum(1, 2)).to eq(9)
    end

    it "should triplicate args before pass to the actual method on instance" do
      calculator = Calculator.new

      do_transform(calculator)

      expect(calculator.sum(1, 2)).to eq(9)
    end

    it "should not afect another instances of the class" do
      calculator = Calculator.new

      do_transform(calculator)

      calculator2 = Calculator.new

      expect(calculator.sum(1, 2)).to eq(9)
      expect(calculator2.sum(1, 2)).to eq(3)
    end

    it "should execute automatic the original method if not call nor cancel is used" do
      calculator = Calculator.new

      Aspects.on(calculator) do
        transform([:sum]) do
          before do |instance, cont, *args|
            3
          end
        end
      end

      expect(calculator.sum(1, 2)).to eq(3)
    end

    it "should cancel the execution of original method" do
      calculator = Calculator.new

      Aspects.on(calculator) do
        transform([:sum]) do
          before do |instance, cont, *args|
            cont.cancel
          end
        end
      end

      expect(calculator.sum(1, 2)).to eq(nil)
    end

    it "should cancel the execution of original method with condition" do
      calculator = Calculator.new

      Aspects.on(calculator) do
        transform([:sum]) do
          before do |instance, cont, *args|
            if args.any? {|a| a > 10}
              cont.cancel
            end
          end
        end
      end

      expect(calculator.sum(53, 2)).to eq(nil)
      expect(calculator.sum(5,3)).to eq(8)
    end

    it "should cancel the auto execution of original method when using call" do
      calculator = Calculator.new

      Aspects.on(calculator) do
        transform([:sum]) do
          before do |instance, cont, *args|
            cont.call(instance, 4, 4)
          end
        end
      end

      expect(calculator.sum(1, 2)).to eq(8)
    end
  end
end