import scala.util.Try
import scala.util.Failure
import scala.util.Success
object HeroesFunciones {

  def seAgradan(unHeroe: Heroe, grupo: Grupo): Boolean = {
    val grupoConHeroe = grupo.agregarIntegrante(unHeroe)
    unHeroe.leAgradaGrupo(grupo) && grupo.lider.leAgradaGrupo(grupoConHeroe)
  }

  def pelear(unHeroe: Heroe, grupo: Grupo): Grupo = if (grupo.fuerzaTotal() > unHeroe.fuerza()) grupo.subirNivel() else grupo.recibirDanio(unHeroe.fuerza())


  def pasarPor(grupo: Grupo, puerta: Puerta): Try[Grupo] = {
    
    if(!grupo.sabeAbrirPuerta(puerta)){
      return Failure(new Exception(s"El grupo no logró abrir: ${puerta}"))
    }

    val grupoPostSituacion = puerta.habitacion.situacion.apply(grupo)
    
    if(grupoPostSituacion.integrantes.forall(_.estaMuerto)){
      return Failure(new Exception(s"El grupo no logró pasar por la habitación: ${puerta.habitacion.situacion}"))
    }

    val nuevasPuertasDescubiertas = grupo.puertasDescubiertas ++ puerta.habitacion.puertas
    val nuevasPuertasAbiertas     = grupo.puertasAbiertas :+ puerta
    val nuevosIntegrantes         = grupoPostSituacion.integrantes.filter(!_.estaMuerto)
    val nuevoGrupo                = grupoPostSituacion.copy(
                                      puertasDescubiertas = nuevasPuertasDescubiertas, 
                                      puertasAbiertas = nuevasPuertasAbiertas,
                                      integrantes = nuevosIntegrantes
                                    )
    return Success(nuevoGrupo)
  }


  def puntaje(grupoOriginal: Grupo, grupoFinal: Grupo): Int = {
    val muertos: Int = Math.max(grupoOriginal.cantidadIntegrantes - grupoFinal.cantidadIntegrantes, 0)
    val vivos: Int   = grupoFinal.cantidadIntegrantes - muertos
    val _puntaje: Int = (vivos * 10) - (muertos * 5) + (grupoFinal.cofre.size) + grupoFinal.integrantes.maxBy(_.nivel).nivel
    return _puntaje
  }
  
}
