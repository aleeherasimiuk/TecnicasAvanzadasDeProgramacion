class MethodNotImplementedError < StandardError;end

class BaseCondition
  def validate(method)
    raise MethodNotImplementedError.new
  end
end