import HeroesFunciones.{pelear, seAgradan}

object Aventura {


  case class Habitacion(situacion: Situacion, puertas: List[Puerta])

  type Situacion = Grupo => Grupo

  case object NoPasaNada extends Situacion {
    override def apply(grupo: Grupo): Grupo = grupo
  }

  case object MuchosMuchosDardos extends Situacion {
    override def apply(grupo: Grupo): Grupo = grupo.lastimarIntegrantes(10)
  }

  case class TesoroPerdido(item: Item) extends Situacion {
    override def apply(grupo: Grupo): Grupo = grupo.obtenerItem(item)
  }

  case object TrampaDeLeones extends Situacion {
    override def apply(grupo: Grupo): Grupo = grupo.matarIntegranteMasLento()
  }

  case class Encuentro(heroe: Heroe) extends Situacion {
    override def apply(grupo: Grupo): Grupo = if (seAgradan(heroe, grupo)) grupo.agregarIntegrante(heroe) else pelear(heroe, grupo)
  }
}
