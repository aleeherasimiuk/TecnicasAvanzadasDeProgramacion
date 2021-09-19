module Aspects

  def self.on(*args, &block)
    validate_arguments(*args,&block)
    
    regexps = filter_by(Regexp, *args)
    classes = filter_by(Class,  *args)

    # Esto falla porque las clases también son objetos, las regexp tambień.
    modules = filter_by(Module, *args)
    objects = filter_by(Object, *args)
    
    objects.each { |it| it.singleton_class.include(LogicModule)}
    classes.each { |it| it.include(LogicModule)}
    modules.each { |it| it.include(LogicModule)}

  end

  private
  def self.validate_arguments(*args)
    raise ArgumentError.new "Origen vacío" if args.empty? or !block_given?
  end


  def self.filter_by(type, *args)
    args.select { |it| it.is_a?(type) }
  end
end

module LogicModule
end







