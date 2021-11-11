import Heroe.{pelear, seAgradan}
import Situaciones._
import scala.util.Success
import scala.util.Failure

case class Recorrido(grupo: Grupo, calabozo: Calabozo, puertasDescubiertas: List[Puerta], puertasAbiertas: List[Puerta]) 
case class Habitacion(situacion: Situacion, puertas: List[Puerta])
case class Calabozo(entrada: Puerta)

object Recorrido{
  def proximaPuerta(recorrido: Recorrido): Option[Puerta] = {
    recorrido.grupo.lider.criterio.proximaPuerta(recorrido)
  }

  def abrirPuerta(recorrido: Recorrido, puerta: Puerta): Aventura = {

    if(!recorrido.grupo.sabeAbrirPuerta(puerta)){
      return Fracaso(recorrido, new Exception(s"El grupo no logró abrir: ${puerta}"))
    }

    val puertasDescubiertas: List[Puerta] = recorrido.puertasDescubiertas ++ puerta.habitacion.puertas
    val puertasAbiertas = recorrido.puertasAbiertas :+ puerta

    return Pendiente(recorrido.copy(puertasDescubiertas = puertasDescubiertas, puertasAbiertas = puertasAbiertas))
  }

  def pasarLaSituacion(recorrido: Recorrido, puerta: Puerta): Aventura = {
    val grupoPostSituacion = puerta.habitacion.situacion.apply(recorrido.grupo)
    
    if(grupoPostSituacion.integrantes.forall(_.estaMuerto)){
      return Fracaso(recorrido, new Exception(s"El grupo no logró pasar por la habitación: ${puerta.habitacion.situacion}"))
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
      a2 <- pasarLaSituacion(a1, puerta)
      a3 <- desecharALosMuertos(a2)
    } yield a3

    return aventura
  }
}


sealed trait Aventura{
  def recorrido: Recorrido
  def map(f: Recorrido => Recorrido): Aventura
  def flatMap(f: Recorrido => Aventura): Aventura
}

object Aventura{
  def apply(recorrido: => Recorrido): Aventura = try {
    Exito(recorrido)
  } catch {
    case e: Exception => Fracaso(recorrido, e)
  }
}

case class Exito(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = Exito(f(recorrido))
  def flatMap(f: Recorrido => Aventura): Aventura = f(recorrido)
}

case class Fracaso(recorrido: Recorrido, error: Exception) extends Aventura{
  def map(f: Recorrido => Recorrido) = this
  def flatMap(f: Recorrido => Aventura) = this
}

case class Pendiente(recorrido: Recorrido) extends Aventura{
  def map(f: Recorrido => Recorrido): Aventura = Pendiente(f(recorrido))
  def flatMap(f: Recorrido => Aventura): Aventura = f(recorrido)
}
