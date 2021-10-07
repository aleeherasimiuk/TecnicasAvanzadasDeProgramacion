require_relative './inject'
require_relative './redirect'
require_relative './transformed'

module TransformsModule
  include HelperMethods

  def transform(methods_to_transform, &block)
    self.instance_variable_set(:@__transformed__, {}) if @__transformed__.nil?

    methods_to_transform.each do |method|

      old_name = (@__transformed__[method].last_method_name + "_" if !@__transformed__[method].nil?)|| get_old_method_name(method)
      old_method = get_unbound_method method

      if @__transformed__[method].nil?
        @__transformed__[method] = Transformed.new(method, old_method, old_name)
      else
        @__transformed__[method].last_method_name = old_name
      end

      by_type(-> {module_transform(old_name, old_method)}, -> {object_transform(old_name, old_method)})


      self.instance_variable_set(:@__method_to_transform__, [method, old_name])
      yield

    end

  end

  private
  def module_transform(old_name, old_method)
    define_method(old_name.to_sym, old_method)
    module_eval do
      private old_name
    end
  end

  def object_transform(old_name, old_method)
    define_singleton_method(old_name.to_sym, old_method)
    singleton_class.instance_eval do
      private old_name
    end
  end


end