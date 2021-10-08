module TransformsModule

  public
  def before(&block)

    method_name, old_method_name = @__method_to_transform__

    method_definition = Proc.new do |*args|

      old_method_proc = Proc.new do |instance, _, *args|
        instance.send(old_method_name, *args)
      end

      instance_exec(self, old_method_proc, *args, &block)
    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end

  def instead_of(&block)

    method_name, _ = @__method_to_transform__

    method_definition = Proc.new do |*args|
      instance_exec(self, *args, &block)
    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end

  def after(&block)

    method_name, old_method_name = @__method_to_transform__

    method_definition = Proc.new do |*args|

      send(old_method_name, *args)
      instance_exec(self, *args, &block)

    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end
end