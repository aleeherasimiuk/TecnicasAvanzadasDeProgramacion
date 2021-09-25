# Conditions
class BaseCondition
  def validate
    raise NotImplementedError.new
  end
end

class NameCondition < BaseCondition
  def initialize regex
    @regex = regex
  end

  def validate method
    @regex.match(method.original_name.to_s)
  end
end

class ParametersCondition < BaseCondition
  def initialize (params_count, block)
    @params_count = params_count
    @check_block = block
  end

  def validate method
    filtered = method.parameters.select do |param|
      @check_block.call(param)
    end

    filtered.length == @params_count
  end
end

class NegCondition < BaseCondition
  def initialize (condition)
    @condition = condition
  end

  def validate method
    !@condition.validate(method)
  end
end


def name regex
  NameCondition.new regex
end

def has_parameters count, extra = nil
  ParametersCondition.new count,
    case extra
      when Regexp
        proc { |param| extra.match(param[1]) }
      when :optional
        proc { |param| param.first == :opt }
      when :mandatory
        proc { |param| param.first == :req }
      else
        proc { true }
      end
end

def neg condition
  NegCondition.new condition
end






