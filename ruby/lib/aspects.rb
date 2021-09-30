module Aspects
  def self.on(*args, &block)
    classes_and_modules, objects, regexps = filter args
    matched_modules_classes = modules_by_regex regexps

    final_class_modules = classes_and_modules.union matched_modules_classes
    validate_arguments(final_class_modules | objects, &block)

    objects.each do |it|
      it.singleton_class.include(LogicModule)
      it.instance_eval &block
    end

    final_class_modules.each do |it|
      it.extend(LogicModule)
      it.module_eval(&block)
    end

    #modules = get_by_type final_class_modules, Module
    #modules.each { |it| it.module_eval(&block) }

    #classes = get_by_type final_class_modules, Class    # Se podria hacer mas polimorfico maybe (?
    #classes.each { |it| it.class_eval(&block) }
  end

  private

  def self.validate_arguments(origins)
    raise ArgumentError.new 'Origen vacío' if origins.empty? || !block_given?
  end

  def self.filter(args)
    regexps = get_by_type args, Regexp

    classes_and_modules = get_by_type args, Module

    objects = args.reject {|it| classes_and_modules.union(regexps).include?(it) }

    return classes_and_modules, objects, regexps
  end

  def self.get_by_type(list, type)
    list.select { |it| it.is_a?(type) }
  end

  def self.modules_by_regex(regexps)
    regexps
      .flat_map { |regex| evaluate_matches regex }
      .uniq # Este uniq es porque puede ser que una clase entre en varias Reg. Exp.
  end

  def self.evaluate_matches(regexp)
    Module.constants
          .select { |sym| regexp.match?(sym.to_s) }
          .map { |sym| Kernel.const_get sym } # pasar de symbol a Clase/Modulo
          .reject { |matched| [Object, BasicObject, Kernel, NilClass, Class, Module].include? matched }
  end
end
