import Habitaciones.Habitacion
import Situaciones.{MuchosMuchosDardos, NoPasaNada, TrampaDeLeones}
import Recorrido.grupoRecorreCalabozo
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import Situaciones.TesoroPerdido

class RecorridoSpec extends AnyFreeSpec{

  "Recorrido" - {
    val guerrero = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Loquito, criterio = Ordenado)
    val mago = Heroe(_fuerza = 5, velocidad = 8, nivel = 1, salud = 50, trabajo = Mago(List.empty), personalidad = Loquito, criterio = Vidente)
    val ladron = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(11), personalidad = Loquito, criterio = Ordenado)

    val ultimaPuerta = PuertaNormal(habitacion = Habitacion(situacion = TrampaDeLeones, puertas = List.empty))
    val puertaCerrada = PuertaCerrada(habitacion = Habitacion(situacion = NoPasaNada, puertas = List.empty))
    val puertaSalida = PuertaCerrada(habitacion = Habitacion(situacion = NoPasaNada, puertas = List.empty), esSalida = true)
    val primerPuerta = PuertaNormal(habitacion = Habitacion(situacion = MuchosMuchosDardos, puertas = List(puertaCerrada, puertaSalida)))

    
    "Un grupo y una aventura pasable con puerta de salida" - {
      val grupo = Grupo(integrantes = List(guerrero, mago, ladron))
      val calabozo = Calabozo(primerPuerta)
      "Se recorre de manera exitosa" in {
        val aventuraFinal = grupoRecorreCalabozo(grupo, calabozo)
        aventuraFinal shouldBe a [Exito]
      }
    }

    "Un grupo sin ladrón y la misma aventura se quedan encerrados" - {
      val grupo = Grupo(integrantes = List(guerrero, mago))
      val calabozo = Calabozo(primerPuerta)
      "Se quedan encerrados" in {
        val aventuraFinal = grupoRecorreCalabozo(grupo, calabozo)
        aventuraFinal shouldBe a [Encerrados]
      }
    }

    "Un grupo con solo un mago y una aventura difícil quedan todos muertos" - {
      val grupo = Grupo(integrantes = List(mago))
      val calabozo = Calabozo(ultimaPuerta)
      "Quedan todos muertos" in {
        val aventuraFinal = grupoRecorreCalabozo(grupo, calabozo)
        aventuraFinal shouldBe a [TodosMuertos]
      }
    }
  }

  "Una aventura imposible" - {

    val francoArmani = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Loquito, criterio = Ordenado)
    val julianAlvarez = Heroe(_fuerza = 5, velocidad = 9, nivel = 300, salud = 50, trabajo = Mago(List(Hechizo("escobazo", 250))), personalidad = Bigote, criterio = Vidente)
    val laChaqueña = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(11), personalidad = Loquito, criterio = Ordenado)
    val barrosSchelotto = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Introvertido, criterio = Ordenado)
    val pityMartinezYVaElTercero = Heroe(_fuerza = 5, velocidad = 8, nivel = 1, salud = 50, trabajo = Mago(List.empty), personalidad = Loquito, criterio = Vidente)
    val martinezQuarta = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(11), personalidad = Introvertido, criterio = Ordenado)
    val marceloGallardo = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Introvertido, criterio = Ordenado)
    val villa = Heroe(_fuerza = 5, velocidad = 8, nivel = 1, salud = 50, trabajo = Mago(List.empty), personalidad = Loquito, criterio = Vidente)
    val messi = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(11), personalidad = Introvertido, criterio = Ordenado)
    val ansufati = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Introvertido, criterio = Ordenado)
    val vinicius = Heroe(_fuerza = 5, velocidad = 8, nivel = 20, salud = 50, trabajo = Mago(List(Hechizo("Vislumbrar", 10))), personalidad = Loquito, criterio = Vidente)
    val kaka = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 20, trabajo = Ladron(11), personalidad = Introvertido, criterio = Ordenado)
    val gaspi = Heroe(_fuerza = 40, velocidad = 10, nivel = 1, salud = 10, trabajo = Guerrero, personalidad = Introvertido, criterio = Ordenado)
    val daniAlves = Heroe(_fuerza = 5, velocidad = 8, nivel = 10, salud = 50, trabajo = Mago(List(Hechizo("ElAnses",7))), personalidad = Loquito, criterio = Vidente)
    val kun = Heroe(_fuerza = 20, velocidad = 30, nivel = 1, salud = 1, trabajo = Ladron(11), personalidad = Introvertido, criterio = Ordenado)
    val kratos = Heroe(_fuerza = 80, velocidad = 30, nivel = 15, salud = 100, trabajo = Guerrero, personalidad = Loquito, criterio = Vidente)
    
    
    val jubilacion = PuertaEncantada(hechizo = "ElAnses", esSalida = true)
    
    val laTranqueraDePepe = PuertaNormal(habitacion = Habitacion(situacion = MuchosMuchosDardos, puertas = List.empty))
    val puertaHechizadaPorVoldemort = PuertaEscondida(habitacion = Habitacion(situacion = TesoroPerdido(Banana), puertas = List.empty))
    
    val soloAmigos = PuertaCerrada()
    val laSuegra = PuertaEncantada(hechizo = "escobazo")
    val laPuertaAlAmor = PuertaCompuesta(puertas = List(soloAmigos, laSuegra), habitacion = Habitacion(situacion = TrampaDeLeones, puertas = List(jubilacion, puertaHechizadaPorVoldemort)))
    
    val unaRejaAbierta = PuertaNormal(habitacion = Habitacion(situacion = NoPasaNada, puertas = List(laTranqueraDePepe, laPuertaAlAmor)))
    val puertaPentagonoMasDuraQueLaRealidad = PuertaCerrada(habitacion = Habitacion(situacion = TrampaDeLeones, puertas = List(unaRejaAbierta)))

    "Tienen que pasar porque son una banda" - {
      val grupo = Grupo(integrantes = List(francoArmani, julianAlvarez, laChaqueña, barrosSchelotto, pityMartinezYVaElTercero, martinezQuarta, marceloGallardo, villa, messi, ansufati, vinicius, kaka, gaspi, daniAlves, kun, kratos), cofre = List(Llave))
      val calabozo = Calabozo(puertaPentagonoMasDuraQueLaRealidad)
      "Salen re piola" in {
        val aventuraFinal = grupoRecorreCalabozo(grupo, calabozo)
        aventuraFinal shouldBe a [Exito]
      }
    }
    }
}
