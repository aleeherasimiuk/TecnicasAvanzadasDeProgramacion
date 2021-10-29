class Mago(_fuerza: Double,_velocidad: Int, _nivel: Int, _salud: Int) extends Heroe(_fuerza, _velocidad, _nivel, _salud) {
    override def fuerza(): Double = fuerza + nivel * (fuerza * 0.2) 

    def sabeAbrirPuerta(puerta: Puerta): Boolean = puerta match {
        case PuertaNormal => true
        case _ => false
    }
}