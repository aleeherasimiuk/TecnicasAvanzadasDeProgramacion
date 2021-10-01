# Conditions
class MethodNotImplementedError < StandardError;end

module ArityConditionModule
  def check_arity_type(foo)
    raise ArgumentError.new 'Paremeter condition can only receive as a first parameter an Integer' unless foo.is_a? Integer
  end
end

class BaseCondition
  def validate(method)
    raise MethodNotImplementedError.new
  end

  def validate_type_extra!(foo)
    raise MethodNotImplementedError.new
  end
end

class NameCondition < BaseCondition
  def initialize(regex)
    validate_type_extra! regex
    @regex = regex
  end

  def validate(method)
    @regex.match?(method.original_name.to_s)
  end

  def validate_type_extra!(foo)
    raise ArgumentError.new 'Name condition can only receive Regexp' unless foo.is_a? Regexp
  end
end

class ParametersCondition < BaseCondition

  include ArityConditionModule

  def initialize (params_count, block)
    validate_type_extra! params_count, block
    @params_count = params_count
    @check_block = block
  end

  def validate(method)
    filtered = method.parameters.select do |param|
      @check_block.call(param)
    end

    filtered.length == @params_count
  end

  def validate_type_extra!(foo, block)
    check_arity_type foo
    raise ArgumentError.new 'Invalid type (or symbol) of second argument' if block.nil?
  end
end

# TODO: Ver si ParametersCondition puede heredar de esta clase
class ArityCondition < BaseCondition

  include ArityConditionModule

  def initialize(arity_size)
    validate_type_extra! arity_size
    @arity_size = arity_size
  end

  def validate(method)
    method.parameters.size == @arity_size
  end

  def validate_type_extra!(foo)
    check_arity_type foo
  end
end

class NegCondition < BaseCondition
  def initialize(condition)
    validate_type_extra! condition
    @condition = condition
  end

  def validate(method)
    !@condition.validate(method)
  end

  def validate_type_extra!(foo)
    raise ArgumentError.new 'Neg condition can only receive conditions' unless foo.class.superclass == BaseCondition
  end
end


def name(regex)
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

