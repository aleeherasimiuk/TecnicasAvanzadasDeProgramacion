require 'rspec'
require 'pry'

require_relative '../lib/aspects'
require_relative '../lib/util/helper_methods'
require_relative '../lib/transforms/redirect'
require_relative '../lib/transforms/inject'
require_relative '../lib/transforms/logic'
require_relative '../lib/transforms/transforms'
require_relative '../lib/logic_module'
require_relative '../lib/conditions/name'
require_relative '../lib/conditions/neg'
require_relative '../lib/conditions/parameters'




#Helpers
require_relative './aspect/aspect_helpers'

# Include fixture classes
require_relative './fixture/aspect_fixture_classes'

RSpec.configure do |c|
  c.include AspectHelper
end
