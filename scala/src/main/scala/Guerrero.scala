class Guerrero(fuerza: Double,velocidad: Int, nivel: Int, salud: Int) extends Heroe(fuerza, velocidad, nivel, salud) {
    override def fuerza(): Double = fuerza + this.nivel * (fuerza * 0.2) 

    def sabeAbrirPuerta(puerta: Puerta): Boolean = puerta match {
        case PuertaNormal => true
        case _ => false
    }
}