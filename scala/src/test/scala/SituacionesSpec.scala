import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class SituacionesSpec extends AnyFlatSpec with GivenWhenThen {
  behavior of "Testeando situaciones"

  val mago = Heroe(20, 6, 12, 20, Mago(List.empty), Bigote)
  val ladron = Heroe(20, 10, 10, 20, Ladron(3), Bigote)
  val grupo = Grupo(List(mago, ladron), List.empty)

  it should "Una situacion que no hace nada no afecta al grupo" in {
    When("cuando transcurre la situacion")
    val grupoDespuesSituacion = NoPasaNada(grupo)

    Then("No afecta al grupo")
    assert(grupo.equals(grupoDespuesSituacion))
  }



}