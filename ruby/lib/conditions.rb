# Conditions
def name regex
  Proc.new { |method| regex.match(method.to_s) }
end

def has_parameters count, extra = 'both'
  Proc.new do |method|
    self.instance_method(method).arity
  end
end




