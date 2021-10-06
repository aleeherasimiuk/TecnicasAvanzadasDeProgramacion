module TransformsModule

  public
  # def before &block

  #   method_name = @__method_to_transform__
  #   old_method_name = get_old_method_name @__method_to_transform__

  #   method_definition = Proc.new do |*args|

  #     old_method_proc = -> do |receptor, ,*args|
  #       instance_exec *args
  #     end

  #     block.call(self, *args)
  #   end

  #   if is_a? Module
  #     define_method(method_name, &method_definition)
  #   else
  #     define_singleton_method(method_name, &method_definition)
  #   end

  # end

  def instead_of &block

    method_name = @__method_to_transform__
    old_method_name = get_old_method_name @__method_to_transform__

    method_definition = Proc.new do |*args|
      instance_exec(self, *args, &block)
    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end

  def after &block

    method_name = @__method_to_transform__
    old_method_name = get_old_method_name @__method_to_transform__

    method_definition = Proc.new do |*args|

      send(old_method_name, *args)
      instance_exec(self, *args, &block)

    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end
end