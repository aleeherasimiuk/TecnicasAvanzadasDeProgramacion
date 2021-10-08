class Callback
  def initialize(method_name)
    @should_auto_call = true
    @method_name = method_name
  end

  def call(instance, *args)
    cancel
    instance.send(@method_name, *args)
  end

  def cancel
    @should_auto_call = false
    nil
  end

  private
  def auto_call(instance, *args)
    if !@should_auto_call
      @should_auto_call = true
      return
    end
    instance.send(@method_name, *args)
  end
end

module TransformsModule

  public
  def before(&block)

    method_name, old_method_name = redefine_method(@__method_to_transform__)

    method_definition = Proc.new do |*args|

      callback = Callback.new old_method_name

      before_val = instance_exec(self, callback, *args, &block)
      callback.send(:auto_call, self, *args) || before_val # si auto_call no se ejecuta, devolver lo del bloque original
    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end

  def instead_of(&block)

    method_name, _ = redefine_method(@__method_to_transform__)

    method_definition = Proc.new do |*args|
      instance_exec(self, *args, &block)
    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end

  def after(&block)

    method_name, old_method_name = redefine_method(@__method_to_transform__)

    method_definition = Proc.new do |*args|

      result = send(old_method_name, *args)
      instance_exec(self, result, *args, &block)

    end

    by_type(-> {define_method(method_name, &method_definition)}, -> {define_singleton_method(method_name, &method_definition)})

  end
end