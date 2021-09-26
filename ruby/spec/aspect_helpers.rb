module AspectHelper
  def includes_module_in_class?(clazz, mod)
    clazz.singleton_class.included_modules.include?(mod)
  end

  def class_includes_logic_module?(clazz)
    includes_module_in_class?(clazz, LogicModule)
  end
end
