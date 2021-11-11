import scala.util.Try
import scala.util.Failure
import scala.util.Success
case class Grupo( integrantes: List[Heroe], cofre: List[Item] = List.empty) {

  def cantidadIntegrantes: Int = integrantes.size

  def lider: Heroe = integrantes.head

  def sabeAbrirPuerta(puerta: Puerta): Boolean =
    integrantes.exists(heroe => heroe.sabeAbrirPuerta(puerta, cofre))

  def lastimarIntegrantes(danio: Double): Grupo =
    this.copy(integrantes = this.integrantes.map(_.bajarSalud(danio.toInt)))

  def subirNivel(): Grupo =
    this.copy(integrantes = this.integrantes.map(_.subirDeNivel()))

  def obtenerItem(item: Item): Grupo =
    this.copy(cofre = item :: this.cofre)

  def tieneItem(item: Item): Boolean = this.cofre.contains(item)

  def matarIntegranteMasLento(): Grupo = {
    val integranteMasLento = integrantes.minBy(_.velocidad)
    val integrantesConElLentoMuerto = integrantes.map(integrante =>
      if (integrante.eq(integranteMasLento)) integrante.morirse()
      else integrante
    )

    this.copy( integrantes = integrantesConElLentoMuerto )
  }

  def recibirDanio(danio: Double): Grupo = lastimarIntegrantes(danio / cantidadIntegrantes)

  def fuerzaTotal(): Double = integrantes.map(_._fuerza).sum

  def tieneLadron(): Boolean = integrantes.exists(_.trabajo match {
    case Ladron(_) => true
    case _ => false
  })

  def agregarIntegrante(heroe: Heroe): Grupo =
    this.copy(integrantes = this.integrantes :+ heroe)

}

object Grupo {
  def puntaje(grupoOriginal: Grupo, grupoFinal: Grupo): Int = {
    val muertos: Int = Math.max(grupoOriginal.cantidadIntegrantes - grupoFinal.cantidadIntegrantes, 0)
    val vivos: Int   = grupoFinal.cantidadIntegrantes
    val _puntaje: Int = (vivos * 10) - (muertos * 5) + (grupoFinal.cofre.size) + grupoFinal.integrantes.maxBy(_.nivel).nivel
    return _puntaje
  }
}
