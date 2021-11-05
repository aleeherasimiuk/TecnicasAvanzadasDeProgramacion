import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.Success
import scala.util.Try
class HabitacionesSpec extends AnyFreeSpec{

  // "Habitaciones" - {
  //   "Con 'NoPasaNada'" - {
  //     "El grupo es el mismo con una puerta más" in {
  //       val magoLoquito = Heroe(_fuerza=2, velocidad=1, nivel=10, salud=100, trabajo = Mago(), personalidad = Loquito, criterio = Heroico)
  //       val grupo = Grupo(List(magoLoquito))
  //       val habitacion = HabitacionNormal(PuertaNormal, NoPasaNada, List.empty)

  //       val grupoDespuesDeLaSituacion = grupo.pasarPor(habitacion)
  //       grupoDespuesDeLaSituacion.get.integrantes shouldBe grupo.integrantes
  //       grupoDespuesDeLaSituacion.get.puertasAbiertas shouldBe List(PuertaNormal)
  //     }

  //     "El grupo es el mismo aunque sean varias habitaciones" in {
  //       val magoLoquito  = Heroe(_fuerza=2, velocidad=1, nivel=10, salud=100, trabajo = Mago(), personalidad = Loquito, criterio = Heroico)
  //       val grupo        = Grupo(List(magoLoquito))
  //       val habitacion3  = HabitacionNormal(PuertaNormal, NoPasaNada, List.empty)
  //       val habitacion2  = HabitacionNormal(PuertaNormal, NoPasaNada, List.empty)
  //       val habitacion   = HabitacionNormal(PuertaNormal, NoPasaNada, List.empty)
  //       val habitaciones = List(habitacion, habitacion2, habitacion3)

  //       val grupoDespuesDeLaSituacion = habitaciones.foldLeft(Try(grupo))((grupo, habitacion) => grupo.flatMap(_.pasarPor(habitacion)))
  //       grupoDespuesDeLaSituacion.get.integrantes shouldBe grupo.integrantes
  //       grupoDespuesDeLaSituacion.get.puertasAbiertas shouldBe List(PuertaNormal, PuertaNormal, PuertaNormal)

  //     }
  //   }
    
  // }

  
}
