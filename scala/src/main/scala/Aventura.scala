import Heroe.{pelear, seAgradan}
import Situaciones._
import scala.util.Success
import scala.util.Failure
import Habitaciones._

case class Calabozo(entrada: Puerta)
case class Recorrido(grupo: Grupo, calabozo: Calabozo, puertasDescubiertas: List[Puerta], puertasAbiertas: List[Puerta])

object Recorrido{
  def proximaPuerta(recorrido: Recorrido): Option[Puerta] = {
    recorrido.grupo.lider.criterio.proximaPuerta(recorrido)
  }

  def abrirPuerta(recorrido: Recorrido, puerta: Puerta): Aventura = {

    if(!recorrido.grupo.sabeAbrirPuerta(puerta)){   /* Solo para tests */
      throw new RuntimeException(s"El grupo no sabe abrir: ${puerta}")
    }

    val puertasDescubiertas: List[Puerta] = recorrido.puertasDescubiertas ++ puerta.habitacion.puertas
    val puertasAbiertas = recorrido.puertasAbiertas :+ puerta

    return Pendiente(recorrido.copy(puertasDescubiertas = puertasDescubiertas, puertasAbiertas = puertasAbiertas))
  }

  def intentarSalir(recorrido: Recorrido, puerta: Puerta): Aventura = if (puerta.esSalida) Exito(recorrido) else Pendiente(recorrido)

  def pasarLaSituacion(recorrido: Recorrido, puerta: Puerta): Aventura = {
    val grupoPostSituacion = puerta.habitacion.situacion.apply(recorrido.grupo)
    
    if(grupoPostSituacion.integrantes.forall(_.estaMuerto)){
      return TodosMuertos(recorrido)
    }
    return Pendiente(recorrido.copy(grupo = grupoPostSituacion))
  }

  def desecharALosMuertos(recorrido: Recorrido): Aventura = {
    val integrantes = recorrido.grupo.integrantes.filterNot(_.estaMuerto)
    return Pendiente(recorrido.copy(grupo = recorrido.grupo.copy(integrantes = integrantes)))
  }

  def pasarPor(recorrido: Recorrido, puerta: Puerta): Aventura = {
    val aventura = for{
      a1 <- abrirPuerta(recorrido, puerta)
      a2 <- intentarSalir(a1, puerta)
      a3 <- pasarLaSituacion(a2, puerta)
      a4 <- desecharALosMuertos(a3)
    } yield a4

    return aventura
  }

  def recorrerCalabozo(aventura: Aventura): Aventura = {
    aventura match {
      case PorEmpezar(recorrido) => recorrerCalabozo(pasarPor(recorrido, recorrido.calabozo.entrada))
      case Pendiente(recorrido) => {
        val proxPuerta: Option[Puerta] = proximaPuerta(recorrido)
        proxPuerta match {
          case Some(p) => recorrerCalabozo(pasarPor(recorrido, p))
          case None => Encerrados(recorrido)
        }
      }
      case _ => aventura
    }
  }
}


sealed trait Aventura{
  def recorrido: Recorrido
  def map(f: Recorrido => Recorrido): Aventura
  def flatMap(f: Recorrido => Aventura): Aventura
}

case class Exito(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = this
  def flatMap(f: Recorrido => Aventura): Aventura = this
}

case class Encerrados(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido) = this
  def flatMap(f: Recorrido => Aventura) = this
}

case class TodosMuertos(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido) = this
  def flatMap(f: Recorrido => Aventura) = this
}

case class Pendiente(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = Pendiente(f(recorrido))
  def flatMap(f: Recorrido => Aventura): Aventura = f(recorrido)
}

case class PorEmpezar(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = Pendiente(f(recorrido))
  def flatMap(f: Recorrido => Aventura): Aventura = f(recorrido)
}

