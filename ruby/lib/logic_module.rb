module LogicModule
  include TransformsModule
  include ConditionsModule

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


  private

  def validate_conditions(conditions)
    raise ArgumentError.new 'Condiciones vacías' if conditions.empty?
  end

  def get_methods
    get_by_type(-> {self.instance_methods}, -> {self.methods})
  end

  def get_unbound_method(method)
    get_by_type(->{self.instance_method method}, ->{self.method method})
  end

  def get_old_method_name(method)
    "__#{method.to_s}_old__"
  end

  def get_by_type(module_block, object_block)
    if is_a? Module
      module_block.call
    else
      object_block.call
    end
  end


end
