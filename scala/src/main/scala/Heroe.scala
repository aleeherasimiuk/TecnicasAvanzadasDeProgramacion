abstract class Heroe(val _fuerza: Double, val _velocidad: Int, val _nivel: Int, val _salud: Int) {

  def estaMuerto: Boolean = _salud <= 0

  def sabeAbrirPuerta(puerta: Puerta): Boolean

  def fuerza(): Double = fuerza
  def nivel(): Int = nivel

}