require_relative './base'

module Neg
  def neg(condition)
    NegCondition.new condition
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