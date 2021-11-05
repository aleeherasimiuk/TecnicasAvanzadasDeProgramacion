sealed trait Criterio {
  def leAgradaElGrupo(grupo: Grupo): Boolean
}

case object Introvertido extends Criterio {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = grupo.cantidadIntegrantes <= 3
}

case object Bigote extends Criterio {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = !grupo.tieneLadron
}

case class Interesado(item: Item) extends Criterio {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = grupo.tieneItem(item)
}

case object Loquito extends Criterio {
  override def leAgradaElGrupo(grupo: Grupo): Boolean = false
}
