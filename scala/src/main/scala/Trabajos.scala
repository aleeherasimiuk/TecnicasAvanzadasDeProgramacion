sealed trait Trabajo

case object Guerrero extends Trabajo

case class Ladron(habilidad: Int) extends Trabajo

case class Mago(hechizos: List[Hechizo] = List.empty) extends Trabajo {
    def conoceHechizo(_hechizo: String, nivel: Int): Boolean = hechizos.exists(it => it.nombre.equalsIgnoreCase(_hechizo) &&  nivel > it.nivelRequerido)
}

case class Hechizo(val nombre: String, val nivelRequerido: Int)
