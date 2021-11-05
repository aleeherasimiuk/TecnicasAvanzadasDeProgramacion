sealed trait Puerta{
  def habitacion : Habitacion
}
case class PuertaCerrada(habitacion: Habitacion = null) extends Puerta
case class PuertaNormal(habitacion: Habitacion = null) extends Puerta
case class PuertaEscondida(habitacion: Habitacion = null) extends Puerta
case class PuertaEncantada(habitacion: Habitacion = null, hechizo: String) extends Puerta
case class PuertaCompuesta(habitacion: Habitacion = null, puertas: List[Puerta]) extends Puerta
