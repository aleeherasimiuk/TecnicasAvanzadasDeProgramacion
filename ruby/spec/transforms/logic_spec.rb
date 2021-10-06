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
      Aspects.on(Calculator) {
        transform [:sum] do
        end
      }

      expect(Calculator.new).to respond_to(:after)
    end
  end
  
    
  context "#after" do
    it "should return double of sum" do
      
      Aspects.on(Calculator) do
        transform([:sum]) do
          after do |instance, *args|
            @x * 2
          end
        end
      end

      calculator = Calculator.new

      expect(calculator.sum(1, 2)).to eq(6)

      
    end
  end

end