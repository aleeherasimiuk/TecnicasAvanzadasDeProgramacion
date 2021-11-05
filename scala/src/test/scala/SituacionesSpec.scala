import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class SituacionesSpec extends AnyFlatSpec with GivenWhenThen {
  behavior of "Una situación"

  val mago = Heroe(5, 6, 12, 20, Mago(List.empty), Bigote, criterio = Heroico)
  val ladron = Heroe(5, 10, 10, 20, Ladron(3), Bigote, criterio = Heroico)
  val grupo = Grupo(List(mago, ladron))

  it should "no hacer nada" in {
    When("transcurre la situacion 'NoPasaNada'")
    val grupoDespuesSituacion = NoPasaNada(grupo)

    Then("no afecta al grupo")
    grupo.equals(grupoDespuesSituacion) shouldBe true
  }

  it should "darles el item" in {
    Given("un tesoro con un item")
    val tesoroPerdidoConItem = TesoroPerdido(Banana)

    When("transcurre la situacion 'TesoroPerdido'")
    val grupoDespuesSituacion = tesoroPerdidoConItem(grupo)

    Then("el grupo tiene el item")
    grupoDespuesSituacion.tieneItem(Banana) shouldBe true
  }

  it should "perder 10 de vida todos los integrantes" in {
    Given("un grupo con -10 de salud con respecto al original")
    val mago2   = Heroe(5, 6, 12, 10, Mago(List.empty), Bigote, criterio = Heroico)
    val ladron2 = Heroe(5, 10, 10, 10, Ladron(3), Bigote, criterio = Heroico)
    val grupo2  = Grupo(List(mago2, ladron2), List.empty)

    When("transcurre la situacion 'MuchosMuchosDardos'")
    val grupoDespuesSituacion = MuchosMuchosDardos(grupo)

    Then("todo el grupo pierde 10 de vida")
    grupoDespuesSituacion shouldBe grupo2
  }

  it should "morir el más lento" in {
    Given("un grupo sin heroe lento")
    val magoSinVida = mago.copy(salud = 0)
    val grupoMagoMuerto = Grupo(List(magoSinVida, ladron))

    When("transcurre la situacion con 'TrampaDeLeones'")
    val grupoDespuesSituacion = TrampaDeLeones(grupo)

    Then("el mago lento muere")
    grupoMagoMuerto.equals(grupoDespuesSituacion) shouldBe true
  }

  it should "morir el primero" in {
    Given("un grupo con dos heroes con la misma velocidad")
    val magoConIgualVelocidad = mago.copy(velocidad = 10)
    val grupoIgualVelocidad = Grupo(List(magoConIgualVelocidad, ladron))

    When("transcurre la situacion 'TrampaDeLeones'")
    val grupoDespuesSituacion = TrampaDeLeones(grupoIgualVelocidad)

    Then("solo muere uno")
    grupoDespuesSituacion.integrantes.count(!_.estaMuerto) shouldBe 1
  }

  //Tests de criterios

  it should "agradar el grupo si tiene hasta 3 miembros y es introvertido" in {
    Given("un grupo de 2 miembros")
    val grupo2 = Grupo(List(mago, ladron, ladron))

    And("un heroe introvertido")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Introvertido, criterio = Heroico)

    Then("le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe true
  }

  it should "no agradar el grupo si tiene más de 3 miembros y es introvertido" in {
    Given("un grupo de 4 miembros")
    val grupo2 = Grupo(List(mago, ladron, mago, ladron))

    And("un heroe introvertido")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Introvertido, criterio = Heroico)

    Then("no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }

  it should "agradar el grupo si no tiene ladrones y es bigote" in {
    Given("un grupo sin ladrón")
    val grupo2 = Grupo(List(mago))

    And("un heroe introvertido")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Bigote, criterio = Heroico)

    Then("le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe true
  }

  it should "no agradar el grupo si tiene ladrones y es bigote" in {
    Given("un grupo con un ladrón")
    val grupo2 = Grupo(List(mago, ladron))

    And("un heroe bigote")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Bigote, criterio = Heroico)

    Then("no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }
  
  it should "agradar si el grupo tiene un item que le interesa y es interesado" in {

    Given("un grupo de 2 miembros con una Banana")
    val grupo2 = Grupo(List(mago, ladron), List(Banana))

    And("un heroe interesado en una 'Banana'")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Interesado(Banana), criterio = Heroico)

    Then("le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe true

  }

  it should "no agradar si el grupo no tiene un item que le interesa y es interesado" in {
    Given("un grupo con una 'GTX'")
    val grupo2 = Grupo(List(mago, ladron), List(PlacaDeVideoGTX))

    And("un heroe interesado en una 'Banana'")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Interesado(Banana), criterio = Heroico)

    Then("no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }

  it should "no agradar ningún grupo si es loquito" in {
    Given("un grupo")
    val grupo2 = Grupo(List(mago, ladron))

    And("un heroe loquito")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Loquito, criterio = Heroico)

    Then("no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }

  //Test se agradan

  it should "sumarse al grupo" in {
    Given("un grupo con lider bigote y una banana")
    val _mago   = mago.copy(salud = 30)
    val _grupo  = Grupo(List(_mago, mago), List(Banana))
    And("un guerrero interesado en una 'Banana'")
    val _enemigo = Heroe(velocidad = 10, _fuerza = 20, nivel = 10, salud = 20, trabajo = Guerrero, personalidad = Interesado(Banana), criterio = Heroico)

    When("transcurre la situacion 'Encuentro' con el enemigo")
    val grupoDespuesSituacion = Encuentro(_enemigo)(_grupo)

    Then("el grupo tiene al guerrero")
    grupoDespuesSituacion.integrantes.last shouldBe _enemigo
  }
  
  //Test cuando pelean

  it should "perder 10 de salud cada integrante" in {
    Given("Un grupo de 2 miembros")
    val _mago   = mago.copy(salud = 30)
    val _ladron = ladron.copy(salud = 30)
    val _grupo  = Grupo(List(_mago, _ladron))
    And("un mago con 20 de fuerza")
    val _enemigo = Heroe(velocidad = 10, _fuerza = 20, nivel = 10, salud = 20, trabajo = Mago(), personalidad = Bigote, criterio = Heroico)

    When("transcurre la situacion 'Encuentro' con el enemigo")
    val grupoDespuesSituacion = Encuentro(_enemigo)(_grupo)

    Then("el grupo pierde 10 de salud")
    grupoDespuesSituacion shouldBe grupo
  }
  
  it should "salir victorioso y subir todos de nivel" in {
    Given("un grupo de 2 miembros")
    val _mago   = mago.copy(nivel = 11)
    val _ladron = ladron.copy(nivel = 9)
    val _grupo  = Grupo(List(_mago, _ladron))
    And("un enemigo con 10 de fuerza")
    val _enemigo = Heroe(velocidad = 10, _fuerza = 5, nivel = 10, salud = 20, trabajo = Mago(), personalidad = Bigote, criterio = Heroico)

    When("transcurre la situacion")
    val grupoDespuesSituacion = Encuentro(_enemigo)(_grupo)

    Then("el grupo sube de nivel")
    grupoDespuesSituacion shouldBe grupo
  }  

}
