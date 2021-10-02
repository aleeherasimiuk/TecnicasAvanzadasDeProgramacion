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


  def transform(methods_to_transform, &block)
    
    methods_to_transform.each do |method|
    
      old_name = get_old_method_name method
      old_method = get_unbound_method method

      get_by_type(-> {module_transform(old_name, old_method)}, -> {object_transform(old_name, old_method)})

      self.instance_variable_set(:@__method_to_transform__, method)
      yield
    
    end
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

  def module_transform(old_name, old_method)
    define_method(old_name.to_sym, old_method)
      module_eval do
        private old_name
    end
  end

  def object_transform(old_name, old_method)
    define_singleton_method(old_name.to_sym, old_method)
      singleton_class.instance_eval do
        private old_name
      end
  end
end
