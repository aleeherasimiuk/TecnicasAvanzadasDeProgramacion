import org.scalatest.matchers.should.Matchers._
import org.scalatest.freespec.{AnyFreeSpec}

class HeroesSpec extends AnyFreeSpec {

  "Puertas" - {

    "ladrón" - {

      "un ladron con habilidad de 15 puede abrir una puerta cerrada" in {
        val ladron = new Ladron(fuerza = 20, velocidad = 6, nivel = 12, salud = 20, habilidad = 15)
        ladron.sabeAbrirPuerta(PuertaCerrada) shouldBe true
      }

      "un ladron con habilidad de 3 no puede abrir una puerta escondida" in {
        val ladron = new Ladron(20,6,12,20,3)
        ladron.sabeAbrirPuerta(PuertaEscondida) shouldBe false
      }
    }
  }
}
