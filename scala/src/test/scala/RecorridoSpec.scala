import Habitaciones.Habitacion
import Situaciones.{MuchosMuchosDardos, NoPasaNada, TrampaDeLeones}
import Recorrido.grupoRecorreCalabozo
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class RecorridoSpec extends AnyFreeSpec{

  "Recorrido" - {
    val guerrero = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Loquito, criterio = Heroico)
    val mago = Heroe(_fuerza = 5, velocidad = 8, nivel = 1, salud = 50, trabajo = Mago(List.empty), personalidad = Loquito, criterio = Vidente)
    val ladron = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(11), personalidad = Loquito, criterio = Ordenado)

    val ultimaPuerta = PuertaNormal(habitacion = Habitacion(situacion = TrampaDeLeones, puertas = List.empty))
    val puertaCerrada = PuertaCerrada(habitacion = Habitacion(situacion = NoPasaNada, puertas = List.empty))
    val puertaSalida = PuertaNormal(habitacion = Habitacion(situacion = NoPasaNada, puertas = List.empty), esSalida = true)
    val primerPuerta = PuertaNormal(habitacion = Habitacion(situacion = MuchosMuchosDardos, puertas = List(puertaCerrada, puertaSalida)))

    val grupo = Grupo(integrantes = List(guerrero, mago, ladron))

    "Un grupo y una aventura pasable con puerta de salida" - {
      val calabozo = Calabozo(primerPuerta)
      "Se recorre de manera exitosa" in {
        val aventuraFinal = grupoRecorreCalabozo(grupo, calabozo)
        aventuraFinal shouldBe a [Exito]
      }
    }
  }

}
