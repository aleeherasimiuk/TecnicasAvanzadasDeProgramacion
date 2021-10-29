class Ladron(fuerza: Double,velocidad: Int, nivel: Int, salud: Int, val habilidad: Int) extends Heroe(fuerza, velocidad, nivel, salud) {
  def sabeAbrirPuerta(puerta: Puerta): Boolean = puerta match {
      case _ if habilidad >= 20 => true
      case PuertaNormal => true
      case PuertaCerrada => habilidad >= 10  /* TODO: ó si el grupo tiene ganzua*/ 
      case PuertaEscondida => habilidad >= 6
      case PuertaCompuesta(puertas) => puertas.foldRight(true) {(puerta,valorDeLaAnt) => this.sabeAbrirPuerta(puerta) && valorDeLaAnt }

      case _ => false
  }
}
