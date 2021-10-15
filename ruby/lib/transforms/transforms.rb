require_relative './inject'
require_relative './redirect'
require_relative './transformed'

module TransformsModule
  include HelperMethods

  public
  def transform(methods_to_transform, &block)
    self.instance_variable_set(:@__transformed__, {}) if @__transformed__.nil?

    methods_to_transform.each do |method|
      self.instance_variable_set(:@__method_to_transform__, method)
      yield
    end

  end
end