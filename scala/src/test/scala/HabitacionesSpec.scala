import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.Success
import scala.util.Try
import Aventura._
import Situaciones._
import Recorrido._
import Grupo.puntaje
import Habitaciones._
class HabitacionesSpec extends AnyFreeSpec{

  "Proximas puertas" - { 
    val guerrero = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Loquito, criterio = Heroico)
    val mago = Heroe(_fuerza = 5, velocidad = 8, nivel = 1, salud = 50, trabajo = Mago(List.empty), personalidad = Loquito, criterio = Vidente)
    val ladron = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(1), personalidad = Loquito, criterio = Ordenado)

    val primerPuerta = PuertaNormal(habitacion = Habitacion(situacion = MuchosMuchosDardos, puertas = List.empty))
    val puertaConMejorResultado = PuertaNormal(habitacion = Habitacion(situacion = NoPasaNada, puertas = List.empty))
    val ultimaPuerta = PuertaNormal(habitacion = Habitacion(situacion = TrampaDeLeones, puertas = List.empty))
    val puertaCerrada = PuertaCerrada(habitacion = Habitacion(situacion = NoPasaNada, puertas = List.empty))
    
    "Un grupo con un lider heroico" - {
      val grupoHeroico = Grupo(integrantes = List(guerrero, mago, ladron))
      "elige la ultima puerta de la lista" in {
        val recorrido = Recorrido(grupoHeroico, null, List(primerPuerta, puertaConMejorResultado, ultimaPuerta), List.empty)
        proximaPuerta(recorrido).get shouldBe (ultimaPuerta)
      }
    }

    "Un grupo con un lider ordenado" - {
      val grupoOrdenado = Grupo(integrantes = List(ladron, guerrero, mago))
      val recorrido = Recorrido(grupoOrdenado, null, List(primerPuerta, puertaConMejorResultado, puertaCerrada, ultimaPuerta), List.empty)
      "elige la primera puerta de la lista" in {
        proximaPuerta(recorrido).get shouldBe primerPuerta
      }
    }

    "Un grupo con un lider vidente" - {
      val grupoVidente = Grupo(integrantes = List(mago, ladron, guerrero))
      val recorrido = Recorrido(grupoVidente, null, puertasDescubiertas = List(ultimaPuerta, puertaConMejorResultado), puertasAbiertas = List.empty)
      val puerta = proximaPuerta(recorrido).get
      "elige la puerta con mejor resultado" in {
        puerta shouldBe puertaConMejorResultado
      }
    }

    "Un grupo sin llave al que solo le queda una puerta cerrada" - {
      val grupoSinLlave = Grupo(integrantes = List(guerrero, mago, ladron))
      val recorrido = Recorrido(grupoSinLlave, null, puertasDescubiertas = List(puertaCerrada), puertasAbiertas = List.empty)
      "no puede seguir cuando solo queda una puerta cerrada" in {
        proximaPuerta(recorrido).isEmpty shouldBe true
      }
    }
  }


  "Pasar puerta" - {

    val guerrero = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 5, trabajo = Guerrero, personalidad = Loquito, criterio = Heroico)
    val ladron = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 5, trabajo = Guerrero, personalidad = Loquito, criterio = Ordenado)

    val primerPuerta = PuertaNormal(habitacion = Habitacion(situacion = MuchosMuchosDardos, puertas = List.empty))
    val puertaSalida = PuertaNormal(esSalida = true)

    "Un grupo con poca salud cunado pase por la puerta no  va a poder lidiar con la situacion" in {
      val grupoNoPasable = Grupo(integrantes = List(guerrero, ladron))
      val recorrido = Recorrido(grupoNoPasable, null, puertasDescubiertas = List.empty, puertasAbiertas = List.empty)

      val aventura = pasarPor(recorrido, primerPuerta)
      aventura shouldBe a [Fracaso]
    }

    /*
    result should equal (3) // can customize equality
    result should === (3)   // can customize equality and enforce type constraints
    result should be (3)    // cannot customize equality, so fastest to compile
    result shouldEqual 3    // can customize equality, no parentheses required
    result shouldBe 3       // cannot customize equality, so fastest to compile, no parentheses required
    */

    "Un grupo con mucha salud cunado pase por la puerta va a poder lidiar con la situacion" in {
      val guerreroMasSaludable = guerrero.copy(salud = 40)

      val grupoPasable = Grupo(integrantes = List(guerreroMasSaludable))
      val recorrido = Recorrido(grupoPasable, null, puertasDescubiertas = List.empty, puertasAbiertas = List.empty)
      
      val aventura = pasarPor(recorrido, primerPuerta)
      aventura shouldBe a [Pendiente] // TODO: Debería ser Exitoso?
    }

    "Un grupo pasa por una puerta marcada como salida" in {

      val grupoPasable = Grupo(integrantes = List(guerrero))
      val recorrido = Recorrido(grupoPasable, null, puertasDescubiertas = List(puertaSalida), puertasAbiertas = List.empty)
      
      val aventura = pasarPor(recorrido, puertaSalida)
      aventura shouldBe a [Exito]
    }

  }

  "Puntaje" - {
    val _guerrero = Heroe(_fuerza = 40, velocidad = 10, nivel = 5, salud = 20, trabajo = Guerrero, personalidad = Loquito, criterio = Heroico)
    val _mago     = Heroe(_fuerza = 5, velocidad = 8, nivel = 1, salud = 50, trabajo = Mago(List.empty), personalidad = Loquito, criterio = Vidente)
    val _ladron   = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(1), personalidad = Loquito, criterio = Ordenado)
    
    val grupoOriginal = Grupo(integrantes = List(_guerrero, _ladron, _mago), cofre = List(Banana))
    val grupoFinal = grupoOriginal.copy(integrantes = List(_guerrero))

    "El puntaje entre 2 grupos varia ya que tiene distinta cantidad de muertos" in {
      puntaje(grupoOriginal, grupoFinal) shouldBe 6
    }

  }
  
}

