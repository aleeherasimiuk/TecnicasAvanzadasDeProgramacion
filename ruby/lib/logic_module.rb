module LogicModule
  def where(*conditions)
    # TODO: [IDEA] - add a temp inst var to have easier expectations
    @__temp_filtered_methods__ = []
    validate_conditions(conditions)
    methods = get_methods
    filtered_methods = methods.select do |method|
      conditions.all? do |cond|
        cond.validate(get_unbound_method method)
      end
    end
    @__temp_filtered_methods__ = filtered_methods
    filtered_methods
  end

  def self.get_temp_filtered_methods
    @__temp_filtered_methods__
  end

  private

  def validate_conditions conditions
    raise ArgumentError.new 'Condiciones vacías' if conditions.empty?
  end

  def get_methods
    if is_a? Module
      self.instance_methods
    else
      self.methods
    end
  end

  def get_unbound_method method
    if is_a? Module
      self.instance_method method
    else
      self.method method
    end
  end
end
