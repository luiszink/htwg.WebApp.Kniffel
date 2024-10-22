package models

import de.htwg.se.kniffel.model.PlayerInterface

case class GameState(output: String, players: List[PlayerInterface], categories: List[String])