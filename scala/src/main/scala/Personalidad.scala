sealed trait Personalidad {
  def leAgradaElGrupo(grupo: Grupo): Boolean
}

case object Introvertido extends Personalidad {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = grupo.cantidadIntegrantes <= 3
}

case object Bigote extends Personalidad {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = !grupo.tieneLadron
}

case class Interesado(item: Item) extends Personalidad {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = grupo.tieneItem(item)
}

case object Loquito extends Personalidad {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = false
}
