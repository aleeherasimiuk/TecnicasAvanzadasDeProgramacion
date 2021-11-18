import Habitaciones._
import Situaciones._

sealed trait Puerta{

  //def habitacion : Habitacion = HabitacionDummy
  def situacion: Situacion = NoPasaNada
  def puertas: List[Puerta]
  def esSalida: Boolean = false
}
case class PuertaCerrada(habitacion: Habitacion = HabitacionDummy, esSalida: Boolean = false) extends Puerta
case class PuertaNormal(habitacion: Habitacion = HabitacionDummy, esSalida: Boolean = false) extends Puerta
case class PuertaEscondida(habitacion: Habitacion = HabitacionDummy, esSalida: Boolean = false) extends Puerta
case class PuertaEncantada(habitacion: Habitacion = HabitacionDummy, hechizo: String, esSalida: Boolean = false) extends Puerta
case class PuertaCompuesta(habitacion: Habitacion = HabitacionDummy, puertas: List[Puerta], esSalida: Boolean = false) extends Puerta