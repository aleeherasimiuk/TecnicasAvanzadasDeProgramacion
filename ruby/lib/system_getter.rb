class SystemGetter
  def self.get_all(type)
    array = []
    ObjectSpace.each_object(type) { |it| array.push(it)}
    array
  end
end
