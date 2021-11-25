import Grupo.puntaje
case class Calabozo(entrada: Puerta)
case class Recorrido(grupo: Grupo, calabozo: Calabozo, puertasDescubiertas: List[Puerta], puertasAbiertas: List[Puerta])

object Recorrido{
  def apply(grupo: Grupo, calabozo: Calabozo): Recorrido =
    Recorrido(grupo = grupo, calabozo = calabozo, puertasDescubiertas = List.empty, puertasAbiertas = List.empty)

  def proximaPuerta(recorrido: Recorrido): Option[Puerta] = {
    recorrido.grupo.lider.criterio.proximaPuerta(recorrido)
  }  

  def mejorGrupo(grupos: List[Grupo], calabozo: Calabozo): Option[Grupo] = {
    if(grupos.isEmpty) return None

    val grupoDespuesDelCalabozo = (grupo: Grupo) => grupoRecorreCalabozo(grupo, calabozo).recorrido.grupo

    val gruposDespuesDelCalabozo = grupos.map(grupoDespuesDelCalabozo)
    val tuplasGrupos = grupos.zip(gruposDespuesDelCalabozo)
    val mejorGrupo = tuplasGrupos.maxBy(tupla => puntaje(tupla._1, tupla._2))
    Some(mejorGrupo._1)
  } 

  def abrirPuerta(recorrido: Recorrido, puerta: Puerta): Aventura = {

    val _puertasDescubiertas: List[Puerta] = recorrido.puertasDescubiertas ++ puerta.habitacion.puertas 
    val _puertasAbiertas = recorrido.puertasAbiertas :+ puerta

    Pendiente(recorrido.copy(puertasDescubiertas = _puertasDescubiertas, puertasAbiertas = _puertasAbiertas))
  }

  def intentarSalir(recorrido: Recorrido, puerta: Puerta): Aventura = if (puerta.esSalida) Exito(recorrido) else Pendiente(recorrido)

  def pasarLaSituacion(recorrido: Recorrido, puerta: Puerta): Aventura = {
    val grupoPostSituacion = puerta.habitacion.situacion.apply(recorrido.grupo)
    
    if(grupoPostSituacion.integrantes.forall(_.estaMuerto)){
      return TodosMuertos(recorrido)
    }
    Pendiente(recorrido.copy(grupo = grupoPostSituacion))
  }

  def desecharALosMuertos(recorrido: Recorrido): Recorrido = {
    val integrantes = recorrido.grupo.integrantes.filterNot(_.estaMuerto)
    recorrido.copy(grupo = recorrido.grupo.copy(integrantes = integrantes))
  }

  def pasarPor(recorrido: Recorrido, puerta: Puerta): Aventura = for {
    a1 <- abrirPuerta(recorrido, puerta)
    a2 <- intentarSalir(a1, puerta)
    a3 <- pasarLaSituacion(a2, puerta)
  } yield desecharALosMuertos(a3)
  

  def recorrerCalabozo(aventura: Aventura): Aventura = {
    aventura match {
      case PorEmpezar(recorrido) => recorrerCalabozo(pasarPor(recorrido, recorrido.calabozo.entrada))
      case Pendiente(recorrido) => recorrerProximaPuerta(recorrido)
      case _ => aventura
    }
  }

  def recorrerProximaPuerta(recorrido: Recorrido): Aventura = {
    proximaPuerta(recorrido) match {
      case Some(p) => recorrerCalabozo(pasarPor(recorrido, p))
      case None => Encerrados(recorrido)
    }
  }
  
  def grupoRecorreCalabozo(grupo: Grupo, calabozo: Calabozo): Aventura = {
    val aventuraInicial = Aventura(Recorrido(grupo, calabozo))
    recorrerCalabozo(aventuraInicial)
  }


  def recorrerSubiendoNiveles(grupo: Grupo, calabozo: Calabozo, niveles: Int): Option[Int] = {
    if (niveles == 20) return None
    grupoRecorreCalabozo(grupo, calabozo) match {
      case Exito(_) => Some(niveles)
      case _ => recorrerSubiendoNiveles(grupo.subirNivel(), calabozo, niveles + 1)
    }
  }

  def cuantosNivelesTieneQueSubir(grupo: Grupo, calabozo: Calabozo): Option[Int] = {
    recorrerSubiendoNiveles(grupo, calabozo, 0)
  }
}

object Aventura {
  def apply(recorrido: Recorrido) = PorEmpezar(recorrido)
}
sealed trait Aventura{
  def recorrido: Recorrido
  def map(f: Recorrido => Recorrido): Aventura
  def flatMap(f: Recorrido => Aventura): Aventura
}

case class Pendiente(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = Pendiente(f(recorrido))
  def flatMap(f: Recorrido => Aventura): Aventura = f(recorrido)
}

case class PorEmpezar(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = Pendiente(f(recorrido))
  def flatMap(f: Recorrido => Aventura): Aventura = f(recorrido)
}

abstract class Terminado extends Aventura {
  def map(f: Recorrido => Recorrido) = this
  def flatMap(f: Recorrido => Aventura) = this
}

case class Exito(recorrido: Recorrido) extends Terminado

case class Encerrados(recorrido: Recorrido) extends Terminado
case class TodosMuertos(recorrido: Recorrido) extends Terminado


