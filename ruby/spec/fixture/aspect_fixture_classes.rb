class DummyClass; end
module DummyModule; end
class A; end
module Abc; end
class EmptyClass; end
class Fooma; end
module Bar; end
class Kermit; end

class ClassWithMethods
  def method1(p1, p2, p3)
  end

  def method2(p1, p2, p3 = '4', p4 = '3', p5)
  end

  def method3(p1, q2, y3, z4 = nil)
  end

  def method4
  end

  def method5(a1 = "")
  end
end

class Y

  def saludar
    "Hola"
  end
end

class C
  def saludar(x)
    "Hola, ". + x
  end

  def operar(a,b,c)
    a + b + c
  end
end

class B
  def saludar(x)
    "Adiosín, " + x
  end

  def operar(a,b,c)
    a * b * c
  end
end


