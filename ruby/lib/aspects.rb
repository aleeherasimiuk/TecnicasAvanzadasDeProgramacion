module Aspects

  def self.on(*args, &block)
    validate_arguments(*args,&block)
    
    classes_and_modules, objects, regexps = filter(args)
    matched_modules_classes = modules_by_regex regexps

    final_class_modules = classes_and_modules | matched_modules_classes
    objects.each { |it| it.singleton_class.include(LogicModule)}
    final_class_modules.each { |it| it.extend(LogicModule)}

  end

  private
  def self.validate_arguments(*args)
    raise ArgumentError.new "Origen vacío" if args.empty? or !block_given?
  end


  def self.filter(args)
    regexps = args.select { |it| it.is_a?(Regexp) }

    classes_and_modules = args.select { |it| it.is_a?(Module) }

    objects = args.reject {|it| classes_and_modules.union(regexps).include?(it) }

    return classes_and_modules, objects, regexps
  end

  def self.modules_by_regex regexps
    matched_modules_classes = []
    regexps.each { |it| matched_modules_classes << evaluate_matches(it)}
    matched_modules_classes.flatten.uniq          # Este uniq es porque puede ser que una clase entre en varias Reg. Exp.
  end

  def self.evaluate_matches regexp
    matched_classes = SystemGetter.get_all(Class).select { |it| regexp.match(it.to_s)}
    matched_modules = SystemGetter.get_all(Module).select { |it| regexp.match(it.to_s)}
    (matched_modules | matched_classes).flatten.uniq              #Este no se repitan entre modulos y clases ya que hay veces que pegan en las dos
  end

end

module LogicModule
end







