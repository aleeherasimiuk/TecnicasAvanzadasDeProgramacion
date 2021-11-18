import Recorrido.pasarPor
import Grupo.puntaje
sealed trait Criterio{
  def proximaPuerta(recorrido: Recorrido): Option[Puerta] = {
    val puertasPosibles = recorrido.puertasDescubiertas.filter(recorrido.grupo.sabeAbrirPuerta)  //Capaz una funcion en la que se cambie de estado
    puertasPosibles match {
      case Nil => None
      case _ => aplicarCriterio(puertasPosibles, recorrido)
    }
  } 

  def aplicarCriterio(puertas: List[Puerta], recorrido: Recorrido): Some[Puerta]
}

case object Heroico extends Criterio{

  override def aplicarCriterio(puertas: List[Puerta], r: Recorrido): Some[Puerta] =  Some(puertas.last)

}
case object Ordenado extends Criterio {

  override def aplicarCriterio(puertas: List[Puerta], r: Recorrido): Some[Puerta] = Some(puertas.head)

}
case object Vidente extends Criterio {

  override def aplicarCriterio(puertas: List[Puerta], recorrido: Recorrido): Some[Puerta] =
    Some(puertas.maxBy(puerta =>  puntaje(recorrido.grupo, pasarPor(recorrido, puerta).recorrido.grupo)))
}
