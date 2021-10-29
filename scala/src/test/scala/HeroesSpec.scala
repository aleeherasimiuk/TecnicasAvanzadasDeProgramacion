import org.scalatest.matchers.should.Matchers._
import org.scalatest.freespec.{AnyFreeSpec}

class HeroesSpec extends AnyFreeSpec {

  "Puertas" - {

    "un ladrón" - {

      "con habilidad de 15 puede abrir una puerta cerrada" in {
        val ladron = new Ladron(fuerza = 20, velocidad = 6, nivel = 12, salud = 20, habilidad = 15)
        ladron.sabeAbrirPuerta(PuertaCerrada) shouldBe true
      }

      "con habilidad de 3 no puede abrir una puerta escondida" in {
        val ladron = new Ladron(20,6,12,20,3)
        ladron.sabeAbrirPuerta(PuertaEscondida) shouldBe false
      }

      "con habilidad 25 puede abrir una puerta compuesta con todos los tipos de puerta" in {
        val ladron = new Ladron(20,6,12,20,25)
        ladron.sabeAbrirPuerta(PuertaCompuesta(List(PuertaNormal, PuertaCerrada, PuertaEscondida, PuertaEncantada("Alohomora")))) shouldBe true
      }

      "con habilidad 19 no puede abrir una puerta compuesta con una puerta normal y una puerta encantada con 'Avada Kedavra'" in {
        val ladron = new Ladron(20,6,12,20,19)
        ladron.sabeAbrirPuerta(PuertaCompuesta(List(PuertaNormal, PuertaEncantada("Avada Kedavra")))) shouldBe false
      }
    }

    "un mago" - {
      "con 'Vislumbrar' puede abrir una puerta escondida" in {
        val mago = new Mago(fuerza = 5, velocidad = 3, nivel = 6, salud = 100, hechizos = List(Hechizo("Vislumbrar", 3)))
        mago.sabeAbrirPuerta(PuertaEscondida) shouldBe true
      }

      "sin 'Vislumbrar' no puede abrir una puerta escondida" in {
        val mago = new Mago(5,3,6,100,List())
        mago.sabeAbrirPuerta(PuertaEscondida) shouldBe false
      }

      "con 'Ábrete, sésamo' puede abrir una puerta encantada con 'Ábrete, sésamo'" in {
        val mago = new Mago(fuerza = 5, velocidad = 3, nivel = 6, salud = 100, hechizos = List(Hechizo("Ábrete, sésamo", 3)))
        mago.sabeAbrirPuerta(PuertaEncantada("Ábrete, sésamo")) shouldBe true
      }

      "con 'Alohomora' no puede abrir una puerta con 'Aberto'" in {
        val mago = new Mago(fuerza = 5, velocidad = 3, nivel = 6, salud = 100, hechizos = List(Hechizo("Alohomora", 3)))
        mago.sabeAbrirPuerta(PuertaEncantada("Aberto")) shouldBe false
      }

      "con 'Alohomora' y 'Vislumbrar' puede abrir una puerta compuesta que esta escondida y tiene un hechizo de 'Alohomora'" in {
        val mago = new Mago(fuerza = 5, velocidad = 3, nivel = 6, salud = 100, hechizos = List(Hechizo("Alohomora", 3),Hechizo("Vislumbrar", 3)))
        mago.sabeAbrirPuerta(PuertaCompuesta(List( PuertaEscondida, PuertaEncantada("Alohomora")))) shouldBe true
      }

      "con 'Alohomora' al nivel 16 no puede abrir una puerta encantada con 'Alohomora' porque todavía no lo aprendió" in {
        val mago = new Mago(fuerza = 5, velocidad = 3, nivel = 3, salud = 100, hechizos = List(Hechizo("Alohomora", 16)))
        mago.sabeAbrirPuerta(PuertaEncantada("Alohomora")) shouldBe false
      }
    }

    "un guerrero" - {
      "sabe abrir una puerta normal" in {
        val guerrero = new Guerrero(fuerza = 5, velocidad = 3, nivel = 6, salud = 100)
        guerrero.sabeAbrirPuerta(PuertaNormal) shouldBe true
      }

      "no sabe abrir una puerta cerrada" in {
        val guerrero = new Guerrero(fuerza = 5, velocidad = 3, nivel = 6, salud = 100)
        guerrero.sabeAbrirPuerta(PuertaCerrada) shouldBe false
      }

      "no sabe abrir una puerta encantada" in {
        val guerrero = new Guerrero(fuerza = 5, velocidad = 3, nivel = 6, salud = 100)
        guerrero.sabeAbrirPuerta(PuertaEncantada("Alohomora")) shouldBe false
      }

      "no sabe abrir una puerta escondida" in {
        val guerrero = new Guerrero(fuerza = 5, velocidad = 3, nivel = 6, salud = 100)
        guerrero.sabeAbrirPuerta(PuertaEscondida) shouldBe false
      }

      "no sabe abrir una puerta compuesta" in {
        val guerrero = new Guerrero(fuerza = 5, velocidad = 3, nivel = 6, salud = 100)
        guerrero.sabeAbrirPuerta(PuertaCompuesta(List(PuertaNormal, PuertaCerrada, PuertaEscondida, PuertaEncantada("Alohomora")))) shouldBe false
      }
    }
  }
}
