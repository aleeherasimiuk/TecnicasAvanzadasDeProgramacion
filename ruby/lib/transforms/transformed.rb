class Transformed

  attr_reader :method_name, :unbound_original
  attr_accessor :last_method_name

  def initialize(method_name, unbound_original, last_method_name)
    @method_name = method_name
    @unbound_original = unbound_original
    @last_method_name = last_method_name
  end

end