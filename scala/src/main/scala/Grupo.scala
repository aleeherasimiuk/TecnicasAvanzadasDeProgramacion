import scala.util.Try
import scala.util.Failure
import scala.util.Success
import HeroesFunciones.{pasarPor, puntaje}
case class Grupo( integrantes: List[Heroe], cofre: List[Item] = List.empty, puertasDescubiertas: List[Puerta] = List.empty, puertasAbiertas: List[Puerta] = List.empty) {

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

//Llevar a funcion
  def proximaPuerta(): Option[Puerta] = {

    val puertasPosibles = this.puertasDescubiertas.filter(this.sabeAbrirPuerta(_))  //Capaz una funcion en la que se cambie de estado
    val criterioDelLider = this.lider.criterio

    //Delegarlo a los criterios
    (criterioDelLider, puertasPosibles) match {
      case (_, Nil) => None
      case (Heroico,  puertas :+ puerta ) => Some(puerta) // La última?
      case (Ordenado, puerta  :: puertas) => Some(puerta)
      case (Vidente, puertas) => Some(puertas.maxBy(puerta => pasarPor(this, puerta).map(grupo => puntaje(this, grupo)).getOrElse(0)))
      case _ => None
    }

  }

}
