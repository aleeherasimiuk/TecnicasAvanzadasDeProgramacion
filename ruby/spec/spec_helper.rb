require 'rspec'
require 'pry'

require_relative '../lib/aspects'
require_relative '../lib/logic_module'
require_relative '../lib/conditions'

#Helpers
require_relative 'aspect_helpers'

# Include fixture classes
require_relative './fixture/aspect_fixture_classes'

RSpec.configure do |c|
  c.include AspectHelper
end
