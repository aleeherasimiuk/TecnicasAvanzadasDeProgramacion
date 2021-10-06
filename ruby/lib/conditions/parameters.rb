require_relative './base'

module ConditionsModule
  def has_parameters(count, extra = nil)

    return ArityCondition.new count unless extra

    dispatch = {
      Regexp: proc { |param| extra.match(param[1]) },
      optional: proc { |param| param.first == :opt },
      mandatory: proc { |param| param.first == :req }
    }

    extra_class_to_sym = extra.class.to_s.to_sym

    block_to_pass = dispatch[extra] || dispatch[extra_class_to_sym]

    ParametersCondition.new count, block_to_pass
  end
end

class ParametersCondition < BaseCondition

  def initialize (params_count, block)
    @params_count = params_count
    @check_block = block
  end

  def validate(method)
    filtered = method.parameters.select do |param|
      @check_block.call(param)
    end

    filtered.length == @params_count
  end

end

class ArityCondition < BaseCondition

  def initialize(arity_size)
    @arity_size = arity_size
  end

  def validate(method)
    method.parameters.size == @arity_size
  end

end
