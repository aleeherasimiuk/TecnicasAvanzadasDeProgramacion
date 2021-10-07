module TransformsModule
  def inject(**hash)
    method_name, old_method_name = @__method_to_transform__
    unbound_old_method = @__transformed__[method_name].unbound_original#get_unbound_method old_method_name.to_sym

    method_definition = Proc.new do |*args|

      method_params = unbound_old_method.parameters.map {|param| param[1]}

      puts "method_params: #{method_params}"

      new_args = method_params.zip([]).to_h

      i = 0
      new_args.each do |key, _|
        if !hash[key].nil?
          if !hash[key].is_a? Proc
            new_args[key] = hash[key]
          else                            
            new_args[key] = instance_exec(self, method_name, args[i], &hash[key])
          end
        else
          new_args[key] = args[i]
        end
        i += 1
      end

      send(old_method_name, *new_args.reject{|_, value| value.nil?}.values)

    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})
  end
end