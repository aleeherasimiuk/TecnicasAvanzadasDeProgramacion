abstract class Heroe(val _fuerza: Double, val _velocidad: Int, val _nivel: Int, val _salud: Int) {

  def estaMuerto: Boolean = _salud <= 0

  def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item] = List.empty): Boolean = puerta match {
    case PuertaNormal => true
    case PuertaCerrada => cofre.contains(Llave)
    case PuertaCompuesta(puertas) => puertas.foldRight(true) {(puerta,valorDeLaAnt) => this.sabeAbrirPuerta(puerta, cofre) && valorDeLaAnt }
    case _ => false
  }

  def fuerza(): Double = fuerza
  def nivel(): Int = nivel

}