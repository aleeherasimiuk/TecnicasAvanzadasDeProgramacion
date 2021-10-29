class Guerrero(fuerza: Double,velocidad: Int, nivel: Int, salud: Int) extends Heroe(fuerza, velocidad, nivel, salud) {
    override def fuerza(): Double = fuerza + this.nivel * (fuerza * 0.2) 
}