module TransformsModule
  def redirect_to(object)
    method_name, _ = redefine_method(@__method_to_transform__)
    #Fail fast y no cuando se le envia el mensaje al objeto recien, que directamente no te deje redireccionar
    raise NoMethodError.new "undefined method #{method_name} for #{object.to_s}" unless object.respond_to? method_name
    method_definition = Proc.new do |*args|
      object.send(method_name, *args)
    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})
  end
end