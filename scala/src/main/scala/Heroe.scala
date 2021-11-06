case class Heroe(val _fuerza: Double, val velocidad: Int, val nivel: Int, val salud: Int, trabajo: Trabajo, personalidad: Personalidad, criterio: Criterio) {

  def estaMuerto: Boolean = salud <= 0

  def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item] = List.empty): Boolean = (puerta,trabajo) match {
    case (PuertaNormal(_),_) => true
    case (_, Ladron(habilidad)) if habilidad >= 20 => true
    case (PuertaCerrada(_),_) if cofre.contains(Llave) => true
    case (PuertaCerrada(_), Ladron(habilidad)) => habilidad >= 10 || cofre.contains(Ganzúas)
    case (PuertaEscondida(_), mago: Mago) => mago.conoceHechizo("Vislumbrar", this.nivel)
    case (PuertaEscondida(_), Ladron(habilidad)) => habilidad >= 6
    case (PuertaEncantada(_, hechizoPuerta), mago: Mago) => mago.conoceHechizo(hechizoPuerta, this.nivel)
    case (puertaComp: PuertaCompuesta, _) => puedePuertaCompuesta(puertaComp, cofre)
    case _ => false
  }

  def morirse(): Heroe = bajarSalud(salud)

  def subirDeNivel(): Heroe = this.copy(nivel = this.nivel + 1)

  def bajarSalud(cantidad: Int): Heroe = this.copy(salud = this.salud - cantidad)

  def puedePuertaCompuesta(puertaComp: PuertaCompuesta, cofre: List[Item]): Boolean =
    puertaComp.puertas.foldRight(true) {(puerta,valorDeLaAnt) => this.sabeAbrirPuerta(puerta, cofre) && valorDeLaAnt }

  def fuerza(): Double = trabajo match {
    case Guerrero => this._fuerza + this.nivel * (this._fuerza * 0.2)
    case _ => this._fuerza
  }

  def leAgradaGrupo(unGrupo: Grupo): Boolean = {
    personalidad.leAgradaElGrupo(unGrupo)
  }

}