module Aspects

  def self.on(*args, &block)
    validate_arguments(*args,&block)

    args.singleton_class.define_method :filter_by, Proc.new { |type| 
      self.select { |it| it.is_a?(type) } 
    }
    
    objects = args.filter_by Object
    classes = args.filter_by Class
    modules = args.filter_by Module
    regexps = args.filter_by Regexp
    
    objects.each { |it| it.singleton_class.include(LogicModule)}
    classes.each { |it| it.include(LogicModule)}
    modules.each { |it| it.include(LogicModule)}

  end

  private
  def self.validate_arguments(*args)
    raise ArgumentError.new "Origen vacío" if args.empty? or !block_given?
  end
end

module LogicModule
end







