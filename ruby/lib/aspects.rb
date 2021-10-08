class EmptyOriginError < StandardError; end
class NotBlockGivenError < StandardError; end

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
  end

  private

  def self.validate_arguments(origins)
    raise EmptyOriginError.new 'Origen vacío' if origins.empty?
    raise NotBlockGivenError.new unless block_given?
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
      .uniq
  end

  def self.evaluate_matches(regexp)
    Module.constants
          .select { |sym| regexp.match?(sym.to_s) }
          .map { |sym| Kernel.const_get sym }
          .reject { |matched| [Object, BasicObject, Kernel, NilClass, Class, Module].include? matched }
  end
end
