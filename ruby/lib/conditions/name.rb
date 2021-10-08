require_relative './base'

module ConditionsModule
  def with_name(regex)
    NameCondition.new regex
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