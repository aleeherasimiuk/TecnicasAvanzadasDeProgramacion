object HeroesFunciones {

  def seAgradan(unHeroe: Heroe, grupo: Grupo): Boolean = {
    val grupoConHeroe = grupo.agregarIntegrante(unHeroe)
    unHeroe.leAgradaGrupo(grupo) && grupo.lider.leAgradaGrupo(grupoConHeroe)
  }

  def pelear(unHeroe: Heroe, grupo: Grupo): Grupo = if (grupo.fuerzaTotal() > unHeroe.fuerza()) grupo.subirNivel() else grupo.recibirDanio(unHeroe.fuerza())

  /*
  def recorrerCalabozo(habitaciones: List[Habitacion], grupo: Grupo): Option[Grupo] = {
    habitaciones.foldRight(grupo)((habitacion, grupo) => )
  }*/
}
