require_relative './inject'
require_relative './redirect'

module TransformsModule
  include HelperMethods

  def transform(methods_to_transform, &block)

    methods_to_transform.each do |method|

      old_name = get_old_method_name method
      old_method = get_unbound_method method

      by_type(-> {module_transform(old_name, old_method)}, -> {object_transform(old_name, old_method)})

      self.instance_variable_set(:@__method_to_transform__, method)
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