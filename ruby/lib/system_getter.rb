class SystemGetter
  def self.get_all(type)
    ObjectSpace.each_object(type).to_a
  end
end
