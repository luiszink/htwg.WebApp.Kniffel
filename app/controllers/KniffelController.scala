package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.kniffel.aview.TUI
import de.htwg.se.kniffel.controller.ControllerInterface
import de.htwg.se.kniffel.KniffelApp
import models.GameState

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

@Singleton
class KniffelController @Inject()(cc: ControllerComponents, controller: ControllerInterface)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  val gameController = KniffelApp.controller
  val tui = new TUI(gameController)
  var players: ListBuffer[String] = ListBuffer.empty

  def startScene = Action {
    Ok(views.html.start())
  }

 def startGame = Action {
    if (players.nonEmpty) {
      players.foreach(controller.addPlayer)
      Redirect(routes.KniffelController.showGameField)
    } else {
      Redirect(routes.KniffelController.showAddPlayers).flashing("error" -> "Bitte füge mindestens einen Spieler hinzu.")
    }
  }

  def getGameState: GameState = {
    val diceOutput = tui.printDice()
    val scoreCardOutput = tui.printScoreCard()
    val rollPrompt = tui.printRoll() 

    val output = s"$diceOutput\n$scoreCardOutput\n$rollPrompt"
    val currentPlayers = gameController.getPlayers
    val categories = List("one", "two", "three", "four", "five", "six", "threeofakind", "fourofakind", "fullhouse", "smallstraight", "largestraight", "kniffel", "chance")
    GameState(output, currentPlayers, categories)
  }
  
  def showAddPlayers = Action { implicit request =>
    Ok(views.html.addPlayers(players.toList))
  }

  def about = Action {
    Ok(views.html.about())
  }

  def selectCategory = Action { implicit request: Request[AnyContent] =>
    val selectedCategory = request.body.asFormUrlEncoded.get("category").head
    gameController.updateScore(selectedCategory)
    Redirect(routes.KniffelController.showGameField)
  }

  def showGameField = Action { implicit request =>
    Ok(views.html.game(getGameState))
  }

  def rollDice = Action { implicit request =>
    gameController.rollDice()
    Ok(views.html.game(getGameState))
  }

  def addPlayer = Action { implicit request =>
    val playerName = request.body.asFormUrlEncoded.get("playerName").head
    gameController.addPlayer(playerName)
    players += playerName 
    Redirect(routes.KniffelController.showAddPlayers).flashing("success" -> s"$playerName wurde hinzugefügt!")
  }
}