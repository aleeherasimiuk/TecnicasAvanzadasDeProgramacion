case class Heroe(val _fuerza: Double, val _velocidad: Int, val _nivel: Int, val _salud: Int, trabajo: Trabajo) {

  def estaMuerto: Boolean = _salud <= 0

  def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item] = List.empty): Boolean = (puerta,trabajo) match {
    case (PuertaNormal,_) => true
    case (_, Ladron(habilidad)) if habilidad >= 20 => true
    case (PuertaCerrada,_) if cofre.contains(Llave) => true
    case (PuertaCerrada, Ladron(habilidad)) => habilidad >= 10 || cofre.contains(Ganzúas)
    case (PuertaEscondida, mago: Mago) => mago.conoceHechizo("Vislumbrar", this._nivel)
    case (PuertaEscondida, Ladron(habilidad)) => habilidad >= 6
    case (PuertaEncantada(hechizoPuerta), mago: Mago) => mago.conoceHechizo(hechizoPuerta, this._nivel)
    case (puertaComp: PuertaCompuesta, _) => puedePuertaCompuesta(puertaComp, cofre)
    case _ => false
  }

  def subirDeNivel(): Heroe = this.copy(_nivel = this._nivel + 1)

  def puedePuertaCompuesta(puertaComp: PuertaCompuesta, cofre: List[Item]): Boolean =
    puertaComp.puertas.foldRight(true) {(puerta,valorDeLaAnt) => this.sabeAbrirPuerta(puerta, cofre) && valorDeLaAnt }

  def fuerza(): Double = trabajo match {
    case Guerrero => this._fuerza + this._nivel * (this._fuerza * 0.2)
    case _ => this._fuerza
  }

}