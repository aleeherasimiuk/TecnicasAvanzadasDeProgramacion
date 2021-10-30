sealed trait Trabajo {
}

case object Guerrero extends Trabajo

case class Ladron(habilidad: Int) extends Trabajo {
    /*override def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item]): Boolean = puerta match {
        case _ if habilidad >= 20 => true
        case PuertaCerrada => habilidad >= 10 || cofre.contains(Ganzúas)  /* TODO: ó si el grupo tiene ganzua*/
        case PuertaEscondida => habilidad >= 6

        case _ => super.sabeAbrirPuerta(puerta, cofre)
    }*/
}

case class Mago(hechizos: List[Hechizo]) extends Trabajo {

    def conoceHechizo(_hechizo: String, nivel: Int): Boolean = hechizos.exists(it => it.nombre.equalsIgnoreCase(_hechizo) &&  nivel > it.nivelRequerido)

    /*override def sabeAbrirPuerta(puerta: Puerta, cofre: List[Item]): Boolean = puerta match {
        case PuertaEscondida if conoceHechizo("Vislumbrar") => true
        case PuertaEncantada(hechizo) if conoceHechizo(hechizo) => true
        case _ => super.sabeAbrirPuerta(puerta, cofre)
    }*/
}

case class Hechizo(val nombre: String, val nivelRequerido: Int)
