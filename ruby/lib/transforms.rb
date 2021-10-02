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