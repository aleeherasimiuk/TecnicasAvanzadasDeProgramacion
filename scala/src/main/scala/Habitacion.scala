trait Habitacion{
  def situacion: Situacion
  def puertas: List[Puerta]
}

case class HabitacionNormal(situacion: Situacion, puertas: List[Puerta]) extends Habitacion{

}

case class Salida(puerta: Puerta) extends Habitacion {
  def puertas: List[Puerta] = List(puerta)
  def situacion: Situacion = NoPasaNada
}
