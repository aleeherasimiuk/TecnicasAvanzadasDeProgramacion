class Ladron(fuerza: Double,velocidad: Int, nivel: Int, salud: Int, val habilidad: Int) extends Heroe(fuerza, velocidad, nivel, salud) {
  def sabeAbrirPuerta(puerta: Puerta): Boolean = puerta match {
      case PuertaNormal => true
      case PuertaCerrada => habilidad >= 10 /*ó si el grupo tiene ganzua*/ 
      case PuertaEscondida => habilidad >= 6
      case _ => false
  }
}
