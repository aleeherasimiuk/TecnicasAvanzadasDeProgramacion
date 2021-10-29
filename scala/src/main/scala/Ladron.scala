class Ladron(fuerza: Double,velocidad: Int, nivel: Int, salud: Int, val habilidad: Int) extends Heroe(fuerza, velocidad, nivel, salud) {
  override def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item]): Boolean = puerta match {
      case _ if habilidad >= 20 => true
      case PuertaCerrada => habilidad >= 10 || cofre.contains(Ganzúas)  /* TODO: ó si el grupo tiene ganzua*/ 
      case PuertaEscondida => habilidad >= 6

      case _ => super.sabeAbrirPuerta(puerta, cofre)
  }
}