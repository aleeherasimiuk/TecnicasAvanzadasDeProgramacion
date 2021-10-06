module TransformsModule
  def inject(**hash)
    method_name, old_method_name = methods_name @__method_to_transform__
    #unbound_old_method = instance_method(old_method_name.to_sym)
    unbound_old_method = get_unbound_method old_method_name.to_sym

    method_definition = Proc.new do |*args|


      ## Esto puede ser útil si queremos trabajar con named parameters

      #hashes = args.filter {|arg| arg.is_a? Hash}
      #positional_args = args.difference hashes

      # if args.first.is_a? Hash
      #   args.first.each do |key, value|
      #     puts "key: #{key}, value: #{value}"
      #   end
      # end

      method_params = unbound_old_method.parameters.map {|param| param[1]}

      new_args = method_params.zip([]).to_h

      i = 0
      new_args.each do |key, _|
        if !hash[key].nil?
          if !hash[key].is_a? Proc
            new_args[key] = hash[key]
          else                            ## Receptor?
            new_args[key] = hash[key].call(self, method_name, args[i])
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