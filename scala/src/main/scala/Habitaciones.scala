import Situaciones._

package object Habitaciones {
  case class Habitacion(situacion: Situacion, puertas: List[Puerta])
  val HabitacionDummy = Habitacion(NoPasaNada, List.empty)
}
