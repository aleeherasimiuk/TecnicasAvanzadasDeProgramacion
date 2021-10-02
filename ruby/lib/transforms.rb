module TransformsModule
  include GetModule

  def transform(methods_to_transform, &block)

    methods_to_transform.each do |method|

      old_name = get_old_method_name method
      old_method = get_unbound_method method

      get_by_type(-> {module_transform(old_name, old_method)}, -> {object_transform(old_name, old_method)})

      self.instance_variable_set(:@__method_to_transform__, method)
      yield

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

  def inject(**hash)
    method_name = @__method_to_transform__
    old_method_name = "__#{@__method_to_transform__.to_s}_old__"
    unbound_old_method = instance_method(old_method_name.to_sym)

    define_method(method_name) do |*args|

      method_params = unbound_old_method.parameters.map {|param| param[1]}

      new_args = method_params.zip([]).to_h

      i = 0
      new_args.each do |key, _|
        if !hash[key].nil?
          new_args[key] = hash[key]
        else
          new_args[key] = args[i]
        end
        i += 1
      end

      send(old_method_name, *new_args.reject{|_, value| value.nil?}.values)

    end

  end

end