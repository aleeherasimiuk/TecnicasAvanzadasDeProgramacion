case class Grupo(integrantes: List[Heroe], cofre: List[Item]) {

  def lider: Heroe = integrantes.head

  def sabeAbrirPuerta(puerta: Puerta): Boolean = integrantes.exists(heroe => heroe.sabeAbrirPuerta(puerta, cofre))

  def lastimarIntegrantes(danio: Double): Grupo = this.copy(integrantes = this.integrantes.map(_.bajarSalud(danio.toInt)))

  def subirNivel(): Grupo = this.copy(integrantes = this.integrantes.map(_.subirDeNivel()))

  def obtenerItem(item: Item): Grupo = this.copy(cofre = this.cofre ++ List(item))

  def matarIntegranteMasLento(): Grupo = {
    val integranteMasLento = integrantes.minBy(_._velocidad)
    integranteMasLento.morirse()         //Podria ser inmutable ??
    this
  }

  def recibirDanio(danio: Double): Grupo = lastimarIntegrantes(danio / cantidadIntegrantes)

  def fuerzaTotal(): Double = integrantes.map(_._fuerza).sum

  def tieneLadron(): Boolean = integrantes.exists(_.trabajo == Ladron)

  def agregarIntegrante(heroe: Heroe): Grupo = this.copy(integrantes = this.integrantes ++ List(heroe))

  def entrarEnHabitacion(habitacion: Habitacion): Unit = {
    this.copy(puertasDescubiertas = puertasDescubiertas.concat(habitacion.puertas))
  }

}
