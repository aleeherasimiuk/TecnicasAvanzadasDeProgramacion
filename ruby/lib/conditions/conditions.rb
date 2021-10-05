require_relative './name'
require_relative './parameters'
require_relative './neg'

# Conditions
module ConditionsModule
  include Name
  include Parameters
  include Neg
end

