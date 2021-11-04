sealed trait Criterio {
  def leAgradaGrupo(grupo: Grupo): Boolean
}

case object Introvertido extends Criterio {
  override def leAgradaGrupo(grupo: Grupo): Boolean = grupo.cantidadIntegrantes <= 3
}

case object Bigote extends Criterio {
  override def leAgradaGrupo(grupo: Grupo): Boolean = !grupo.tieneLadron
}

case object Loquito extends Criterio {
  override def leAgradaGrupo(grupo: Grupo): Boolean = false
}
