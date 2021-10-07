module TransformsModule
  def redirect_to(object)
    method_name, _ = @__method_to_transform__
    #Fail fast y no cuando se le envia el mensaje al objeto recien, que directamente no te deje redireccionar
    raise NoMethodError.new "undefined method #{method_name} for #{object.to_s}" unless object.respond_to? method_name
    define_method(method_name) do |*args|
      object.send(method_name, *args)
    end

  end
end