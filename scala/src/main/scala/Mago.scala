class Mago(fuerza: Double, velocidad: Int, nivel: Int, salud: Int, hechizos: List[Hechizo]) extends Heroe(fuerza, velocidad, nivel, salud) {
    
    override def fuerza(): Double = fuerza + nivel * (fuerza * 0.2) 

    def conoceHechizo(_hechizo: String): Boolean = hechizos.exists(it => it.nombre.equalsIgnoreCase(_hechizo) &&  nivel > it.nivelRequerido)

    def sabeAbrirPuerta(puerta: Puerta): Boolean = puerta match {
        case PuertaNormal => true
        case PuertaEscondida if conoceHechizo("Vislumbrar") => true
        case PuertaEncantada(hechizo) if conoceHechizo(hechizo) => true
        case PuertaCompuesta(puertas) => puertas.foldRight(true) {(puerta,valorDeLaAnt) => this.sabeAbrirPuerta(puerta) && valorDeLaAnt }
        case _ => false
    }
}
case class Hechizo(val nombre: String, val nivelRequerido: Int)