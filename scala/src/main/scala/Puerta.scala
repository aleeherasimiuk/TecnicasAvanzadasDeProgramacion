sealed trait Puerta
case object PuertaCerrada extends Puerta
case object PuertaNormal extends Puerta
case object PuertaEscondida extends Puerta
case class PuertaEncantada(hechizo: String) extends Puerta
case class PuertaCompuesta(puertas: List[Puerta]) extends Puerta
