module GetModule

=begin
  Me parecio bien desacoplar esta logica del Logic_module ya que tambien la usa el TransformModule. Sino el
  transformModule funcionaria igual porque lo tendria el Logic pero en otro contexto le requeriria a la clase que lo
  implemente que tenga esos metodos.
=end

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

