# Conditions
def name regex
  Proc.new { |method| regex.match(method.original_name.to_s) }
end

def has_parameters count, extra = 'both'
  Proc.new do |method|
    method.arity == count
  end
end

def neg condition
  Proc.new do |method|
    !condition.call(method)
  end
end




