import Grupo.Items

case class Heroe(_fuerza: Double, velocidad: Int, nivel: Int, salud: Int, trabajo: Trabajo, personalidad: Personalidad, criterio: Criterio) {

  def estaMuerto: Boolean = salud <= 0

  def sabeAbrirPuerta(puerta: Puerta, cofre: Items = List.empty): Boolean = (puerta,trabajo) match {
    case (PuertaNormal(_, _), _) => true
    case (_, Ladron(habilidad)) if habilidad >= 20 => true
    case (PuertaCerrada(_, _), _) if cofre.contains(Llave) => true
    case (PuertaCerrada(_, _), Ladron(habilidad)) => habilidad >= 10 || cofre.contains(Ganzuas)
    case (PuertaEscondida(_, _), mago: Mago) => mago.conoceHechizo("Vislumbrar", this.nivel)
    case (PuertaEscondida(_, _), Ladron(habilidad)) => habilidad >= 6
    case (PuertaEncantada(_, hechizoPuerta, _), mago: Mago) => mago.conoceHechizo(hechizoPuerta, this.nivel)
    case (puertaComp: PuertaCompuesta, _) => puedePuertaCompuesta(puertaComp, cofre)
    case _ => false
  }

  def morirse(): Heroe = bajarSalud(salud)

  def subirDeNivel(): Heroe = this.copy(nivel = this.nivel + 1)

  def bajarSalud(cantidad: Int): Heroe = this.copy(salud = this.salud - cantidad)

  def puedePuertaCompuesta(puertaComp: PuertaCompuesta, cofre: Items): Boolean =
    puertaComp.puertas.foldRight(true) {(puerta,valorDeLaAnt) => this.sabeAbrirPuerta(puerta, cofre) && valorDeLaAnt }

  def fuerza(): Double = trabajo match {
    case Guerrero => this._fuerza + this.nivel * (this._fuerza * 0.2)
    case _ => this._fuerza
  }

  def leAgradaGrupo(unGrupo: Grupo): Boolean = {
    personalidad.leAgradaElGrupo(unGrupo)
  }

}

object Heroe {

  def seAgradan(unHeroe: Heroe, grupo: Grupo): Boolean = {
    val grupoConHeroe = grupo.agregarIntegrante(unHeroe)
    unHeroe.leAgradaGrupo(grupo) && grupo.lider.leAgradaGrupo(grupoConHeroe)
  }

  def pelear(unHeroe: Heroe, grupo: Grupo): Grupo = if (grupo.fuerzaTotal() > unHeroe.fuerza()) grupo.subirNivel() else grupo.recibirDanio(unHeroe.fuerza())
}