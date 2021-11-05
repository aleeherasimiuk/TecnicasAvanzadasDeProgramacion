import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatest.freespec.AnyFreeSpec

trait HeroeBehaviour {
  this: AnyFlatSpec =>

  def noPuedeAbrirNingunaPuertaSoloLaNormal(heroe: => Heroe): Unit = {
    val puertaCompuesta = PuertaCompuesta(
      List(
        PuertaNormal,
        PuertaCerrada,
        PuertaEscondida,
        PuertaEncantada("Alohomora")
      )
    )

    it should "sabe abrir una puerta normal" in {
      heroe.sabeAbrirPuerta(PuertaNormal) shouldBe true
    }

    it should "no sabe abrir una puerta cerrada" in {
      heroe.sabeAbrirPuerta(PuertaCerrada) shouldBe false
    }

    it should "no sabe abrir una puerta encantada" in {
      heroe.sabeAbrirPuerta(PuertaEncantada("Alohomora")) shouldBe false
    }

    it should "no sabe abrir una puerta escondida" in {
      heroe.sabeAbrirPuerta(PuertaEscondida) shouldBe false
    }

    it should "no sabe abrir una puerta compuesta" in {
      heroe.sabeAbrirPuerta(puertaCompuesta) shouldBe false
    }

    it should "abre una puerta cerrada si el grupo tiene una llave" in {
      heroe.sabeAbrirPuerta(PuertaCerrada, List(Llave)) shouldBe true
    }
  }

  def puedeAbrirSoloLaEscondidaYNormal(heroe: => Heroe): Unit = {

    it should "sabe abrir una puerta normal" in {
      heroe.sabeAbrirPuerta(PuertaNormal) shouldBe true
    }

    it should "no sabe abrir una puerta cerrada" in {
      heroe.sabeAbrirPuerta(PuertaCerrada) shouldBe false
    }

    it should "no sabe abrir una puerta encantada" in {
      heroe.sabeAbrirPuerta(PuertaEncantada("Alohomora")) shouldBe false
    }

    it should "no sabe abrir una puerta escondida" in {
      heroe.sabeAbrirPuerta(PuertaEscondida) shouldBe true
    }
  }

}

class HeroesSpec extends AnyFlatSpec with GivenWhenThen with HeroeBehaviour {
  behavior of "Testeando heroe"

  val puertaEncantada = PuertaEncantada("Alohomora")
  val puertaCompuesta = PuertaCompuesta(
    List(
      PuertaNormal,
      PuertaCerrada,
      PuertaEscondida,
      PuertaEncantada("Alohomora")
    )
  )

  it should "Validaciones basicas" in {
    Given("un heroe")
    val heroe = Heroe(5, 3, 6, 100, Guerrero, Bigote)

    Then("validar que esta vivo")
    heroe.estaMuerto shouldBe false

    And("puede subir de nivel")
    heroe.subirDeNivel().nivel shouldBe 7

    And("deberia validar su trabajo")
    heroe.trabajo shouldBe Guerrero

  }

  //Guerrero
  val guerrero = new Heroe(5, 3, 6, 100, Guerrero, Bigote)

  "Un guerrero solamente puede abrir una puerta normal y una cerrada si el grupo tiene la llave" should behave like noPuedeAbrirNingunaPuertaSoloLaNormal(
    guerrero
  )

  it should "Un guerrero tiene distinta fuerza que un ladron con los mismo valores iniciales" in {
    Given("otro heroe con los mismos valores")
    val ladron = Heroe(5, 3, 6, 100, Ladron(2), Bigote)

    Then("el guerrero tiene una fuerza")
    guerrero.fuerza() shouldBe 11

    And("y el ladron otra")
    ladron._fuerza shouldBe 5
  }

  //Ladron
  val ladronConHabilidad: Int => Heroe = int =>
    Heroe(20, 6, 12, 20, Ladron(int), Bigote)

  val ladron2 = ladronConHabilidad(3)

  "Un ladron con habilidad de 3 solo puede abrir una normal" should behave like noPuedeAbrirNingunaPuertaSoloLaNormal(
    ladron2
  )

  val ladron = ladronConHabilidad(6)

  "Un ladron con habilidad de 6 solo puede abrir una puerta escondida y una normal" should behave like puedeAbrirSoloLaEscondidaYNormal(
    ladron
  )

  it should "Con habilidad de 15 puede abrir una puerta cerrada" in {
    Given("un ladron con habilidad 15")
    val ladron = ladronConHabilidad(15)

    Then("puede abrir una puerta cerrada")
    ladron.sabeAbrirPuerta(PuertaCerrada) shouldBe true
  }

  it should "Con habilidad de 3 no puede abrir una puerta escondida" in {
    Given("un ladron con hablidad 3")
    val ladron = ladronConHabilidad(3)

    Then("no puede abrir puerta escondida")
    ladron.sabeAbrirPuerta(PuertaEscondida) shouldBe false
  }

  it should "Con habilidad 25 puede abrir una puerta compuesta con todos los tipos de puerta" in {
    Given("un ladron con habilidad 25")
    val ladron = ladronConHabilidad(25)

    Then("puede abrir puerta compuesta")
    ladron.sabeAbrirPuerta(puertaCompuesta) shouldBe true
  }

  it should "Con habilidad 19 no puede abrir una puerta compuesta con todos los tipos de puerta" in {
    Given("un ladron con habilidad 25")
    val ladron = ladronConHabilidad(19)

    Then("puede abrir puerta compuesta")
    ladron.sabeAbrirPuerta(puertaCompuesta) shouldBe false
  }

  it should "Con habilidad 6 no puede abrir una puerta cerrada sin ganzuas" in {
    Given("un ladron con habilidad 6")
    val ladron = ladronConHabilidad(6)

    Then("puede abrir una puerta cerrada con ganzuas")
    ladron.sabeAbrirPuerta(PuertaCerrada, List(Ganzúas)) shouldBe true

    And("No sin ellas")
    ladron.sabeAbrirPuerta(PuertaCerrada) shouldBe false
  }

  //Mago
  val magoConHechizos: List[Hechizo] => Heroe = listaHechizos => Heroe(20, 6, 12, 20, Mago(listaHechizos), Bigote)

  it should "Con 'Vislumbrar' puede abrir una puerta escondida" in {
    Given("mago con dislumbrar")
    val mago = magoConHechizos(List(Hechizo("Vislumbrar", 3)))

    Given("mago sin dislmbrar")
    val mago2 = magoConHechizos(List())

    Then("puede abrir una escondida")
    mago.sabeAbrirPuerta(PuertaEscondida) shouldBe true

    And("sin el no puede abrirla")
    mago2.sabeAbrirPuerta(PuertaEscondida) shouldBe false

  }

  it should "Puede abrir una puerta encantada con su hechizo correspondiente" in {
    Given("mago con hechizo")
    val mago =
      magoConHechizos(List(Hechizo("Alohomora", 2), Hechizo("Abrakadabra", 20)))

    Then("puede abrir puerta encantada")
    mago.sabeAbrirPuerta(puertaEncantada) shouldBe true

    And("no sabe abrir una puerta de la que no conoce su hechizo")
    mago.sabeAbrirPuerta(PuertaEncantada("Ábrete, sésamo")) shouldBe false  

    And("tampoco sabe abrir una puerta la cual todavia no tiene el nivel")
    mago.sabeAbrirPuerta(PuertaEncantada("Abrakadabra")) shouldBe false

  }

  it should "Con 'Alohomora' y 'Vislumbrar' puede abrir una puerta compuesta que esta escondida y tiene un hechizo de 'Alohomora'" in {
    Given("mago con 'Alohomora' y 'Vislumbrar'")
    val mago =
      magoConHechizos(List(Hechizo("Alohomora", 3), Hechizo("Vislumbrar", 3)))

    Then(
      "puede abrir puerta compuesta que esta escondida y tiene hechizo de 'Alohomora'"
    )
    
    mago.sabeAbrirPuerta(
      PuertaCompuesta(List(PuertaEscondida, PuertaEncantada("Alohomora")))
    ) shouldBe true
    
  }

  //Grupo
  it should "con un mago y un guerrero y una llave pueden abrir una puerta cerrada" in {
    Given("Ungrupo con un mago y un guerrero")
    val mago = magoConHechizos(List(Hechizo("Vislumbrar", 3)))
    val grupo = Grupo(List(mago, guerrero), List(Llave))

    Then("pueden abrir puerta cerrada")
    grupo.sabeAbrirPuerta(PuertaCerrada) shouldBe true
  }

  it should "con únicamente un ladrón con ganzúas puede abrir una puerta cerrada" in {
    Given("un grupo con ladron")
    val ladron = ladronConHabilidad(2)
    val grupo = Grupo(List(ladron), List(Ganzúas))

    Then("pueden abrir puerta cerrada")
    grupo.sabeAbrirPuerta(PuertaCerrada) shouldBe true
  }

  it should "con un mago sin hechizos y un ladrón sin ganzúas no pueden abrir una puerta cerrada" in {
    Given("un grupo con un mago sin hechizos y un ladron")
    val mago = magoConHechizos(List())
    val ladron = ladronConHabilidad(2)
    val grupo = Grupo(List(mago, ladron), List.empty)

    Then("No pueden abrir una puerta cerrada")
    grupo.sabeAbrirPuerta(PuertaCerrada) shouldBe false
  }

}
