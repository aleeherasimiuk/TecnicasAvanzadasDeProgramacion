module AspectHelper
  def includes_module_in_klass?(klass, mod)
    klass.singleton_class.included_modules.include?(mod)
  end

  def klass_includes_logic_module?(klass)
    includes_module_in_klass?(klass, LogicModule)
  end
end