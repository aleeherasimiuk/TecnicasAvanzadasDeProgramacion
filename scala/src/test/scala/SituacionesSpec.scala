import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class SituacionesSpec extends AnyFlatSpec with GivenWhenThen {
  behavior of "Testeando situaciones"

  val mago = Heroe(20, 6, 12, 20, Mago(List.empty), Bigote)
  val ladron = Heroe(20, 10, 10, 20, Ladron(3), Bigote)
  val grupo = Grupo(List(mago, ladron))

  it should "Una situacion que no hace nada no afecta al grupo" in {
    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = NoPasaNada(grupo)

    Then("No afecta al grupo")
    assert(grupo.equals(grupoDespuesSituacion))
  }

  it should "Una situacion de tesoro perdido, les da el item" in {
    Given("Un tesoro con un item")
    val tesoroPerdidoConItem = TesoroPerdido(Banana)

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = tesoroPerdidoConItem(grupo)

    Then("El grupo tiene el item")
    assert(grupoDespuesSituacion.tieneItem(Banana))
  }

  it should "Una situacion de muchosMuchos dardos, todos los integrantes pierden 10 de vida" in {
    Given("Un grupo para comparar igual al dado pero con 10 menos de vida todos sus integrantes")
    val mago2 = Heroe(20, 6, 12, 10, Mago(List.empty), Bigote)
    val ladron2 = Heroe(20, 10, 10, 10, Ladron(3), Bigote)
    val grupo2 = Grupo(List(mago2, ladron2), List.empty)

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = MuchosMuchosDardos(grupo)

    Then("El grupo pierde 10 de vida")
    assert(grupo2.equals(grupoDespuesSituacion))
  }

  it should "Una situacion de trampas de leones, muere el más lento´" in {
    Given("Un grupo para comparar sin el héroe mas lento")
    val magoSinVida = mago.copy(_salud = 0)
    val grupoMagoMuerto = Grupo(List(magoSinVida, ladron))

    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = TrampaDeLeones(grupo)

    Then("El mago lento muere")
    assert(grupoMagoMuerto.equals(grupoDespuesSituacion))
  }

  it should "Una situacion de trampas de leones, de 2 con la misma velocidad muere el primero" in {
    Given("Un grupo con dos con la misma velocidad")
    val magoConIgualVelocidad = mago.copy(_velocidad = 10)
    val grupoIgualVelocidad = Grupo(List(magoConIgualVelocidad, ladron))

    When("transcurre la situacion")
    val grupoDespuesSituacion = TrampaDeLeones(grupoIgualVelocidad)

    Then("Solo muere uno")
    assert(grupoDespuesSituacion.integrantes.count(!_.estaMuerto) == 1)
  }



}
