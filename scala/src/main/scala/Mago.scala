class Mago(fuerza: Double, velocidad: Int, nivel: Int, salud: Int, hechizos: List[Hechizo]) extends Heroe(fuerza, velocidad, nivel, salud) {
    
    override def fuerza(): Double = fuerza + nivel * (fuerza * 0.2) 

    def conoceHechizo(_hechizo: String): Boolean = hechizos.exists(it => it.nombre.equalsIgnoreCase(_hechizo) &&  nivel > it.nivelRequerido)

    override def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item]): Boolean = puerta match {
        case PuertaEscondida if conoceHechizo("Vislumbrar") => true
        case PuertaEncantada(hechizo) if conoceHechizo(hechizo) => true
        case _ => super.sabeAbrirPuerta(puerta, cofre)
    }
}
case class Hechizo(val nombre: String, val nivelRequerido: Int)