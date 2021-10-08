module HelperMethods

  def get_methods
    by_type(-> {self.instance_methods}, -> {self.methods})
  end

  def get_unbound_method(method)
    by_type(->{self.instance_method method}, ->{self.method method})
  end

  def get_old_method_name(method)
    "__#{method.to_s}_old__"
  end

  def by_type(module_block, object_block)
    if is_a? Module
      module_block.call
    else
      object_block.call
    end
  end

  def methods_name(method_to_transform)
    old_method_name = get_old_method_name method_to_transform

    [method_to_transform, old_method_name]
  end

  def redefine_method(method)
    old_name = (@__transformed__[method].last_method_name + "_" if !@__transformed__[method].nil?)|| get_old_method_name(method)
    old_method = get_unbound_method method

    if @__transformed__[method].nil?
      @__transformed__[method] = Transformed.new(method, old_method, old_name)
    else
      @__transformed__[method].last_method_name = old_name
    end

    by_type(-> {module_transform(old_name, old_method)}, -> {object_transform(old_name, old_method)})

    [method, old_name]
  end
end

