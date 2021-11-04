case class Grupo( integrantes: List[Heroe], cofre: List[Item], puertasDescubiertas: List[Puerta] = List.empty, puertasAbiertas: List[Puerta] = List.empty ) {

  def cantidadIntegrantes: Int = integrantes.size

  def lider: Heroe = integrantes.head

  def sabeAbrirPuerta(puerta: Puerta): Boolean =
    integrantes.exists(heroe => heroe.sabeAbrirPuerta(puerta, cofre))

  def lastimarIntegrantes(danio: Double): Grupo =
    this.copy(integrantes = this.integrantes.map(_.bajarSalud(danio.toInt)))

  def subirNivel(): Grupo =
    this.copy(integrantes = this.integrantes.map(_.subirDeNivel()))

  def obtenerItem(item: Item): Grupo =
    this.copy(cofre = this.cofre ++ List(item))

  def tieneItem(item: Item): Boolean = this.cofre.contains(item)

  def matarIntegranteMasLento(): Grupo = {
    val integranteMasLento = integrantes.minBy(_._velocidad)
    val integrantesConElLentoMuerto = integrantes.map(integrante =>
      if (integrante.eq(integranteMasLento)) integrante.morirse()
      else integrante
    )

    this.copy( integrantes = integrantesConElLentoMuerto )
  }

  def recibirDanio(danio: Double): Grupo = lastimarIntegrantes(
    danio / cantidadIntegrantes
  )

  def fuerzaTotal(): Double = integrantes.map(_._fuerza).sum

  def tieneLadron(): Boolean = integrantes.exists(_.trabajo match {
    case l: Ladron => true
    case _ => false
  })

  def agregarIntegrante(heroe: Heroe): Grupo =
    this.copy(integrantes = this.integrantes ++ List(heroe))

  def entrarEnHabitacion(habitacion: Habitacion): Unit = {
    this.copy(
      puertasDescubiertas = puertasDescubiertas.concat(habitacion.puertas),
      integrantes = integrantes.filter(!_.estaMuerto)
    )
  }

}
