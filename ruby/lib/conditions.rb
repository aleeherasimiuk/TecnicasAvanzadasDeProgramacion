# Conditions
module ConditionsModule

  def with_name(regex)
    NameCondition.new regex
  end

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

  def neg(condition)
    NegCondition.new condition
  end
end


class MethodNotImplementedError < StandardError;end

class BaseCondition
  def validate(method)
    raise MethodNotImplementedError.new
  end
end

class NameCondition < BaseCondition
  def initialize(regex)
    @regex = regex
  end

  def validate(method)
    @regex.match?(method.original_name.to_s)
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

class NegCondition < BaseCondition
  def initialize(condition)
    @condition = condition
  end

  def validate(method)
    !@condition.validate(method)
  end

end

