module Aspects

  def self.on(*args, &block)
    validate_arguments(*args,&block)
    
    classes_and_modules, objects, regex = filter(args)
    
    objects.each { |it| it.singleton_class.include(LogicModule)}
    classes_and_modules.each { |it| it.extend(LogicModule)}

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

end

module LogicModule
end







