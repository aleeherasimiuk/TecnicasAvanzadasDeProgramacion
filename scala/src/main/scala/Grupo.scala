case class Grupo(integrantes: List[Heroe], cofre: List[Item]) {

  def lider: Heroe = integrantes.head

  def sabeAbrirPuerta(puerta: Puerta): Boolean = integrantes.exists(heroe => heroe.sabeAbrirPuerta(puerta, cofre))
  
}
