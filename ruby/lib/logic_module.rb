require_relative './conditions/name'
require_relative './conditions/neg'
require_relative './conditions/parameters'

module LogicModule
  include TransformsModule
  include ConditionsModule

  def where(*conditions)
    @__temp_filtered_methods__ = []
    validate_conditions(conditions)
    methods = get_methods
    filtered_methods = methods.select do |method|
      conditions.all? do |cond|
        cond.validate(get_unbound_method method)
      end
    end
    @__temp_filtered_methods__ = filtered_methods
    filtered_methods
  end


  private

  def validate_conditions(conditions)
    raise ArgumentError.new 'Condiciones vacías' if conditions.empty?
  end

end
