import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class SituacionesSpec extends AnyFlatSpec with GivenWhenThen {
  behavior of "Testeando situaciones"

  val mago = Heroe(5, 6, 12, 20, Mago(List.empty), Bigote)
  val ladron = Heroe(5, 10, 10, 20, Ladron(3), Bigote)
  val grupo = Grupo(List(mago, ladron))

  it should "Una situacion que no hace nada no afecta al grupo" in {
    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = NoPasaNada(grupo)

    Then("No afecta al grupo")
    grupo.equals(grupoDespuesSituacion) shouldBe true
  }

  it should "Una situacion de tesoro perdido, les da el item" in {
    Given("Un tesoro con un item")
    val tesoroPerdidoConItem = TesoroPerdido(Banana)

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = tesoroPerdidoConItem(grupo)

    Then("El grupo tiene el item")
    grupoDespuesSituacion.tieneItem(Banana) shouldBe true
  }

  it should "Una situacion de muchosMuchos dardos, todos los integrantes pierden 10 de vida" in {
    Given("Un grupo para comparar igual al dado pero con 10 menos de vida todos sus integrantes")
    val mago2 = Heroe(5, 6, 12, 10, Mago(List.empty), Bigote)
    val ladron2 = Heroe(5, 10, 10, 10, Ladron(3), Bigote)
    val grupo2 = Grupo(List(mago2, ladron2), List.empty)

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = MuchosMuchosDardos(grupo)

    Then("El grupo pierde 10 de vida")
    grupoDespuesSituacion shouldBe grupo2
  }

  it should "Una situacion de trampas de leones, muere el más lento´" in {
    Given("Un grupo para comparar sin el héroe mas lento")
    val magoSinVida = mago.copy(salud = 0)
    val grupoMagoMuerto = Grupo(List(magoSinVida, ladron))

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = TrampaDeLeones(grupo)

    Then("El mago lento muere")
    grupoMagoMuerto.equals(grupoDespuesSituacion) shouldBe true
  }

  it should "Una situacion de trampas de leones, de 2 con la misma velocidad muere el primero" in {
    Given("Un grupo con dos con la misma velocidad")
    val magoConIgualVelocidad = mago.copy(velocidad = 10)
    val grupoIgualVelocidad = Grupo(List(magoConIgualVelocidad, ladron))

    When("transcurre la situacion")
    val grupoDespuesSituacion = TrampaDeLeones(grupoIgualVelocidad)

    Then("Solo muere uno")
    grupoDespuesSituacion.integrantes.count(!_.estaMuerto) shouldBe 1
  }

  //Tests de criterios

  it should "Un heroe con un criterio de introvertido le agrada un grupo de hasta 3 miembros" in {
    Given("Un grupo de 2 miembros")
    val grupo2 = Grupo(List(mago, ladron, ladron))

    Given("Un heroe introvertido")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Introvertido)

    Then("Al heroe no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe true
  }

  it should "Un heroe con un criterio de introvertido no le agrada un grupo de mas de 3 miembros" in {
    Given("Un grupo de 2 miembros")
    val grupo2 = Grupo(List(mago, ladron, mago, ladron))

    Given("Un heroe introvertido")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Introvertido)

    Then("Al heroe no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }

  it should "Un heroe con un criterio de bigotes le agrada un grupo donde no hay ladrones" in {
    Given("Un grupo sin ladrón")
    val grupo2 = Grupo(List(mago))

    Given("Un heroe introvertido")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Bigote)

    Then("Al heroe le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe true
  }

  it should "Un heroe con un criterio de bigotes no le agrada un grupo donde hay ladrones" in {
    Given("Un grupo con un ladrón")
    val grupo2 = Grupo(List(mago, ladron))

    Given("Un heroe bigote")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Bigote)

    Then("Al heroe no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }
  
  it should "Un heroe con un criterio de interesado le agrada un grupo donde hay un item que le interesa" in {

    Given("Un grupo de 2 miembros con una Banana")
    val grupo2 = Grupo(List(mago, ladron), List(Banana))

    Given("Un heroe interesado en una Banana")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Interesado(Banana))

    Then("Al heroe le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe true

  }

  it should "Un heroe con un criterio de interesado no le agrada un grupo donde no hay un item que le interesa" in {
    Given("Un grupo con una GTX")
    val grupo2 = Grupo(List(mago, ladron), List(PlacaDeVideoGTX))

    Given("Un heroe interesado en una Banana")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Interesado(Banana))

    Then("Al heroe no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }

  it should "Un heroe con un criterio de loquito no le agrada ningun grupo" in {
    Given("Un grupo de 2 miembros")
    val grupo2 = Grupo(List(mago, ladron))

    Given("Un heroe loquito")
    val ladron2 = Heroe(20, 10, 10, 20, Ladron(3), Loquito)

    Then("Al heroe no le cae bien")
    ladron2.leAgradaGrupo(grupo2) shouldBe false
  }

  //Test se agradan

  it should "Un encuentro con un guerrero interesado en bananas tiene que entrar a un grupo con una banana, cuyo lider es bigote" in {
    Given("Un grupo de 2 miembros")
      val _mago   = mago.copy(salud = 30)
      val _grupo  = Grupo(List(_mago, mago), List(Banana))
      val _enemigo = Heroe(velocidad = 10, _fuerza = 20, nivel = 10, salud = 20, trabajo = Guerrero, criterio = Interesado(Banana))

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = Encuentro(_enemigo)(_grupo)

    Then("El grupo pierde 10 de salud")
    grupoDespuesSituacion.integrantes.contains(_enemigo) shouldBe true
  }
  
  //Test cuando pelean

  it should "Un encuentro con un guerrero de 20 de fuerza deberia perder 10 de salud cada integrante" in {
    Given("Un grupo de 2 miembros")
      val _mago   = mago.copy(salud = 30)
      val _ladron = ladron.copy(salud = 30)
      val _grupo  = Grupo(List(_mago, _ladron))
      val _enemigo = Heroe(velocidad = 10, _fuerza = 20, nivel = 10, salud = 20, trabajo = Mago(), criterio = Bigote)

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = Encuentro(_enemigo)(_grupo)

    Then("El grupo pierde 10 de salud")
    grupoDespuesSituacion shouldBe grupo
  }
  
  it should "Un encuentro con un guerrero de 5 de fuerza sale victorioso el grupo subiendo todos de nivel" in {
    Given("Un grupo de 2 miembros")
      val _mago   = mago.copy(nivel = 11)
      val _ladron = ladron.copy(nivel = 9)
      val _grupo  = Grupo(List(_mago, _ladron))
      val _enemigo = Heroe(velocidad = 10, _fuerza = 5, nivel = 10, salud = 20, trabajo = Mago(), criterio = Bigote)

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = Encuentro(_enemigo)(_grupo)

    Then("El grupo pierde 10 de salud")
    grupoDespuesSituacion shouldBe grupo
  }  

}
