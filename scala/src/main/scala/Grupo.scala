import scala.util.Try
import scala.util.Failure
import scala.util.Success
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

  def pasarPor(puerta: Puerta): Try[Grupo] = {
    
    if(!sabeAbrirPuerta(puerta)){
      return Failure(new Exception(s"El grupo no logró abrir: ${puerta}"))
    }

    val grupoPostSituacion = puerta.habitacion.situacion.apply(this)
    
    if(grupoPostSituacion.integrantes.forall(_.estaMuerto)){
      return Failure(new Exception(s"El grupo no logró pasar por la habitación: ${puerta.habitacion.situacion}"))
    }

    val nuevasPuertasDescubiertas = puertasDescubiertas ++ puerta.habitacion.puertas
    val nuevasPuertasAbiertas     = puertasAbiertas :+ puerta
    val nuevosIntegrantes         = grupoPostSituacion.integrantes.filter(!_.estaMuerto)
    val nuevoGrupo                = grupoPostSituacion.copy(
                                      puertasDescubiertas = nuevasPuertasDescubiertas, 
                                      puertasAbiertas = nuevasPuertasAbiertas,
                                      integrantes = nuevosIntegrantes
                                    )
    return Success(nuevoGrupo)
  }

  def proximaPuerta(): Option[Puerta] = {

    val puertasPosibles = this.puertasDescubiertas.filter(this.sabeAbrirPuerta(_))
    val criterioDelLider = this.lider.criterio

    (criterioDelLider, puertasPosibles) match {
      case (_, Nil) => None
      case (Heroico,  puertas :+ puerta ) => Some(puerta) // La última?
      case (Ordenado, puerta  :: puertas) => Some(puerta)
      case (Vidente, puertas) => Some(puertas.maxBy(puerta => this.pasarPor(puerta).map(_.puntaje(this)).getOrElse(0)))
      case _ => None
    }

  }

  def puntaje(grupoOriginal: Grupo): Int = {
    val muertos: Int = grupoOriginal.cantidadIntegrantes - cantidadIntegrantes
    val vivos: Int = cantidadIntegrantes - muertos
    val puntaje: Int = (vivos * 10) - (muertos * 5) + (this.cofre.size) + integrantes.maxBy(_.nivel).nivel
    return puntaje
  }

}
