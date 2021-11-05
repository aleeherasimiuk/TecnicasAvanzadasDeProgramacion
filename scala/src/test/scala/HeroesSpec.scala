import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatest.freespec.AnyFreeSpec

trait HeroeBehaviour {
  this: AnyFlatSpec =>

  def noPuedeAbrirNingunaPuertaSoloLaNormal(heroe: => Heroe): Unit = {
    val puertaCompuesta = PuertaCompuesta(puertas = 
      List(
        PuertaNormal(),
        PuertaCerrada(),
        PuertaEscondida(),
        PuertaEncantada(hechizo = "Alohomora")
      )
    )

    it should "saber abrir una puerta normal" in {
      heroe.sabeAbrirPuerta(PuertaNormal()) shouldBe true
    }

    it should "no saber abrir una puerta cerrada" in {
      heroe.sabeAbrirPuerta(PuertaCerrada()) shouldBe false
    }

    it should "no saber abrir una puerta encantada" in {
      heroe.sabeAbrirPuerta(PuertaEncantada(hechizo = "Alohomora")) shouldBe false
    }

    it should "no saber abrir una puerta escondida" in {
      heroe.sabeAbrirPuerta(PuertaEscondida()) shouldBe false
    }

    it should "no saber abrir una puerta compuesta" in {
      heroe.sabeAbrirPuerta(puertaCompuesta) shouldBe false
    }

    it should "abrir una puerta cerrada si el grupo tiene una llave" in {
      heroe.sabeAbrirPuerta(PuertaCerrada(), List(Llave)) shouldBe true
    }
  }

  def puedeAbrirSoloLaEscondidaYNormal(heroe: => Heroe): Unit = {

    it should "saber abrir una puerta normal" in {
      heroe.sabeAbrirPuerta(PuertaNormal()) shouldBe true
    }

    it should "no saber abrir una puerta cerrada" in {
      heroe.sabeAbrirPuerta(PuertaCerrada()) shouldBe false
    }

    it should "no saber abrir una puerta encantada" in {
      heroe.sabeAbrirPuerta(PuertaEncantada(hechizo = "Alohomora")) shouldBe false
    }

    it should "no saber abrir una puerta escondida" in {
      heroe.sabeAbrirPuerta(PuertaEscondida()) shouldBe true
    }
  }

}

class HeroesSpec extends AnyFlatSpec with GivenWhenThen with HeroeBehaviour {
  behavior of "Un heroe"

  val puertaEncantada = PuertaEncantada(hechizo = "Alohomora")
  val puertaCompuesta = PuertaCompuesta(puertas = 
    List(
      PuertaNormal(),
      PuertaCerrada(),
      PuertaEscondida(),
      PuertaEncantada(hechizo = "Alohomora")
    )
  )

  it should "estar vivo subir de nivel y ser un guerrero" in {
    Given("un guerrero bigote con 100 de salud y nivel 6")
    val heroe = Heroe(_fuerza = 5, velocidad = 3, nivel = 6, salud = 100, trabajo = Guerrero, personalidad = Bigote, criterio = Heroico)

    Then("está vivo")
    heroe.estaMuerto shouldBe false

    When("sube de nivel")
    val nuevoHeroe = heroe.subirDeNivel()

    Then("es nivel 7")
    heroe.subirDeNivel().nivel shouldBe 7

    And("es guerrero")
    heroe.trabajo shouldBe Guerrero

  }

  //Guerrero
  val guerrero = new Heroe(_fuerza = 5, velocidad = 3, nivel = 6, salud = 100, trabajo = Guerrero, personalidad = Bigote, criterio = Heroico)

  "Un guerrero solamente puede abrir una puerta normal y una cerrada si el grupo tiene la llave" should behave like noPuedeAbrirNingunaPuertaSoloLaNormal(
    guerrero
  )

  it should "tener más fuerza que un ladrón" in {
    Given("un ladrón con la misma fuerza inicial")
    val ladron = Heroe(5, 3, 6, 100, Ladron(2), Bigote, criterio = Heroico)

    Then("el guerrero tiene una fuerza")
    guerrero.fuerza shouldBe 11

    And("y el ladron otra")
    ladron.fuerza shouldBe 5
  }

  //Ladron
  val ladronConHabilidad: Int => Heroe = int =>
    Heroe(20, 6, 12, 20, Ladron(int), Bigote, criterio = Heroico)

  val ladron2 = ladronConHabilidad(3)

  "Un ladron con habilidad de 3 solo puede abrir una normal" should behave like noPuedeAbrirNingunaPuertaSoloLaNormal(
    ladron2
  )

  val ladron = ladronConHabilidad(6)

  "Un ladron con habilidad de 6 solo puede abrir una puerta escondida y una normal" should behave like puedeAbrirSoloLaEscondidaYNormal(
    ladron
  )

  it should "poder abrir una puerta cerrada" in {
    Given("un ladron con habilidad 15")
    val ladron = ladronConHabilidad(15)

    Then("puede abrir una puerta cerrada")
    ladron.sabeAbrirPuerta(PuertaCerrada()) shouldBe true
  }

  it should "no poder abrir una puerta escondida" in {
    Given("un ladron con hablidad 3")
    val ladron = ladronConHabilidad(3)

    Then("no puede abrir puerta escondida")
    ladron.sabeAbrirPuerta(PuertaEscondida()) shouldBe false
  }

  it should "poder abrir una puerta compuesta con todos los tipos de puerta" in {
    Given("un ladron con habilidad 25")
    val ladron = ladronConHabilidad(25)

    Then("puede abrir puerta compuesta")
    ladron.sabeAbrirPuerta(puertaCompuesta) shouldBe true
  }

  it should "no poder abrir una puerta compuesta con todos los tipos de puerta" in {
    Given("un ladron con habilidad 19")
    val ladron = ladronConHabilidad(19)

    Then("no puede abrir puerta compuesta")
    ladron.sabeAbrirPuerta(puertaCompuesta) shouldBe false
  }

  it should "no poder abrir una puerta cerrada sin ganzuas" in {
    Given("un ladron con habilidad 6")
    val ladron = ladronConHabilidad(6)

    Then("puede abrir una puerta cerrada con ganzuas")
    ladron.sabeAbrirPuerta(PuertaCerrada(), List(Ganzúas)) shouldBe true

    And("no sin ellas")
    ladron.sabeAbrirPuerta(PuertaCerrada()) shouldBe false
  }

  //Mago
  val magoConHechizos: List[Hechizo] => Heroe = listaHechizos => Heroe(20, 6, 12, 20, Mago(listaHechizos), Bigote, criterio = Heroico)

  it should "poder abrir una puerta escondida con 'Vislumbrar'" in {
    Given("un mago con 'Vislumbrar'")
    val mago = magoConHechizos(List(Hechizo("Vislumbrar", 3)))

    And("un mago sin 'Vislumbrar'")
    val mago2 = magoConHechizos(List.empty)

    Then("quien tiene vislumbrar puede abrir una escondida")
    mago.sabeAbrirPuerta(PuertaEscondida()) shouldBe true

    And("quien no tiene vislumbrar no puede abrirla")
    mago2.sabeAbrirPuerta(PuertaEscondida()) shouldBe false

  }

  it should "poder abrir una puerta encantada con su hechizo correspondiente" in {
    Given("un mago con hechizo")
    val mago = magoConHechizos(List(Hechizo("Alohomora", 2), Hechizo("Abrakadabra", 20)))

    Then("puede abrir puerta encantada")
    mago.sabeAbrirPuerta(puertaEncantada) shouldBe true

    And("no sabe abrir una puerta de la que no conoce su hechizo")
    mago.sabeAbrirPuerta(PuertaEncantada(hechizo = "Ábrete, sésamo")) shouldBe false  

    And("tampoco sabe abrir una puerta la cual todavia no tiene el nivel")
    mago.sabeAbrirPuerta(PuertaEncantada(hechizo = "Abrakadabra")) shouldBe false

  }

  it should "poder abrir una puerta compuesta de puerta escondida y encantada" in {
    Given("un mago con 'Alohomora' y 'Vislumbrar'")
    val mago = magoConHechizos(List(Hechizo("Alohomora", 3), Hechizo("Vislumbrar", 3)))

    And("una puerta escondia")
    val puertaEscondida = PuertaEscondida()

    And("una puerta encantada con 'Alohomora'")
    val puertaEncantada = PuertaEncantada(hechizo = "Alohomora")

    Then("puede abrir la puerta compuesta")
    mago.sabeAbrirPuerta(PuertaCompuesta(puertas = List(puertaEscondida, puertaEncantada))) shouldBe true
  }

  //Grupo
  it should "poder abrir la puerta cerrada" in {
    Given("un grupo con un mago y un guerrero que tiene una 'Llave'")
    val mago = magoConHechizos(List(Hechizo("Vislumbrar", 3)))
    val grupo = Grupo(List(mago, guerrero), List(Llave))

    Then("puede abrir puerta cerrada")
    grupo.sabeAbrirPuerta(PuertaCerrada()) shouldBe true
  }

  it should "poder abrir la puerta cerrada con ganzúas" in {
    Given("un grupo con ladron y 'Ganzúas'")
    val ladron = ladronConHabilidad(2)
    val grupo = Grupo(List(ladron), List(Ganzúas))

    Then("puede abrir puerta cerrada")
    grupo.sabeAbrirPuerta(PuertaCerrada()) shouldBe true
  }

  it should "no poder abrir la puerta cerrada" in {
    Given("un grupo con un mago sin hechizos y un ladron sin 'Ganzúas'")
    val mago = magoConHechizos(List())
    val ladron = ladronConHabilidad(2)
    val grupo = Grupo(List(mago, ladron), List.empty)

    Then("no puede abrir una puerta cerrada")
    grupo.sabeAbrirPuerta(PuertaCerrada()) shouldBe false
  }

}
