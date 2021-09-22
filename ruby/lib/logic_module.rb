module LogicModule
  def where(*conditions)
    validate_conditions(conditions)
    methods = get_methods
    filtered_methods = methods.select do |method|
      conditions.all? do |cond|
        cond.call(method)
      end
    end
  end


  private

  def validate_conditions conditions
    raise ArgumentError.new 'Condiciones vacías' if conditions.empty?
  end

  def get_methods
    self.instance_methods
  end

  
end




